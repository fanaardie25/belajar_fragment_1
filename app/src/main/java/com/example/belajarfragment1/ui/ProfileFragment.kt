package com.example.belajarfragment1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.belajarfragment1.LoginActivity
import com.example.belajarfragment1.R
import com.example.belajarfragment1.api.ApiHelper
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var btn_logout: Button
    private lateinit var txt_name: TextView
    private lateinit var txt_email: TextView
    private lateinit var txt_telp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
                // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txt_name = view.findViewById<TextView>(R.id.txt_name)
        txt_email = view.findViewById<TextView>(R.id.txt_email)
        txt_telp = view.findViewById<TextView>(R.id.txt_telp)

        val pref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = pref.getString("token","no-token")

        ApiHelper.get("user",token){success , response ->
            requireActivity().runOnUiThread {
                if (success && response != null){
                    val json = JSONObject(response)
                    txt_name.text = json.getString("name")
                    txt_email.text = json.getString("email")
                    txt_telp.text = json.getString("no_telp")
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout = view.findViewById<Button>(R.id.btn_logout)

        btn_logout.setOnClickListener {
            val pref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)

            pref.edit().remove("token").apply()
            requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}