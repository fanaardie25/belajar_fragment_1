package com.example.belajarfragment1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belajarfragment1.R
import com.example.belajarfragment1.adapter.JobAdapter
import com.example.belajarfragment1.data.JobData


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
            "onsite" -> alljobs.filter { it.category == "onsite" }
            "remote" -> alljobs.filter { it.category == "remote" }
            else -> alljobs
        }

        jobAdapter.UpdateData(result)
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


        jobAdapter = JobAdapter(jobList)
        recyclerView.adapter = jobAdapter

        loadDummyData()

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


    @SuppressLint("NotifyDataSetChanged")
    private fun loadDummyData() {
        alljobs.add(
            JobData(
                category = "onsite",
                job = "Android Developer",
                namePT = "PT Maju Mundur",
                location = "On site (Jakarta)",
                experience = "Min 1 Tahun"
            )
        )

        alljobs.add(
            JobData(
                category = "remote",
                job = "UI/UX Designer",
                namePT = "PT Kreatif",
                location = "Remote (Bandung)",
                experience = "Fresh Graduate"
            )
        )

        alljobs.add(
            JobData(
                category = "remote",
                job = "UI/UX Designer",
                namePT = "PT Kreatif",
                location = "Remote (Bandung)",
                experience = "Fresh Graduate"
            )
        )

        alljobs.add(
            JobData(
                category = "remote",
                job = "UI/UX Designer",
                namePT = "PT Kreatif",
                location = "Remote (Bandung)",
                experience = "Fresh Graduate"
            )
        )

        jobAdapter.UpdateData(alljobs)

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