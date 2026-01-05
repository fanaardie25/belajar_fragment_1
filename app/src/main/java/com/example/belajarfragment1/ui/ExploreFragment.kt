package com.example.belajarfragment1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belajarfragment1.DetailJobActivity
import com.example.belajarfragment1.R
import com.example.belajarfragment1.adapter.JobAdapter
import com.example.belajarfragment1.api.ApiHelper
import com.example.belajarfragment1.data.JobData
import org.json.JSONObject


class ExploreFragment : Fragment() {
    private lateinit var tabAll: TextView
    private lateinit var tabOnSite: TextView
    private lateinit var tabRemote: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private val jobList = mutableListOf<JobData>()
    private val alljobs = mutableListOf<JobData>()

    private fun filter(category: String){
        val result = when(category){
            "onsite" -> alljobs.filter { it.category == "onSite" }
            "remote" -> alljobs.filter { it.category == "remote" }
            else -> alljobs
        }

        jobAdapter.UpdateData(result)
    }

    private fun parseJobs(jsonString: String){
        val json = JSONObject(jsonString)
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            val job = data.getJSONObject(i)
            alljobs.add(
                JobData(
                category = job.getString("category"),
                job = job.getString("job_name"),
                namePT = job.getString("company_name"),
                location = job.getString("location"),
                experience = job.getString("required")
            ))
            jobAdapter.UpdateData(alljobs)
            Log.d("JOB", job.getString("job_name"))
        }
    }


    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        recyclerView = view.findViewById(R.id.view_card)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        jobAdapter = JobAdapter(jobList){job ->
            Toast.makeText(requireContext(), job.job, Toast.LENGTH_SHORT).show()

            // pindah ke detail
            val intent = Intent(requireContext(), DetailJobActivity::class.java)
            intent.putExtra("JOB_ID", job.job)
            startActivity(intent)
        }
        recyclerView.adapter = jobAdapter

        ApiHelper.get("jobs",null){success,response ->
            if (!success || response == null) return@get

            requireActivity().runOnUiThread {
                parseJobs(response)
            }
        }


        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabAll = view.findViewById<TextView>(R.id.tabAll)
        tabOnSite = view.findViewById<TextView>(R.id.tabOnSIte)
        tabRemote = view.findViewById<TextView>(R.id.tabRemote)

        setupTabs()
    }

    private fun setupTabs() {

        fun selectTab(selected: TextView) {
            tabAll.isSelected = false
            tabOnSite.isSelected = false
            tabRemote.isSelected = false

            selected.isSelected = true
        }

        tabAll.setOnClickListener {
            selectTab(tabAll)
            filter("all")
        }

        tabOnSite.setOnClickListener {
            selectTab(tabOnSite)
            filter("onsite")
        }

        tabRemote.setOnClickListener {
            selectTab(tabRemote)
            filter("remote")
        }

        selectTab(tabAll)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}