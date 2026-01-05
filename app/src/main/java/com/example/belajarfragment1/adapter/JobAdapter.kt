package com.example.belajarfragment1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belajarfragment1.R
import com.example.belajarfragment1.data.JobData
import org.w3c.dom.Text
import java.util.zip.Inflater

class JobAdapter(private var dataList: MutableList<JobData>,private val onItemClick: (JobData) -> Unit): RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvJobName: TextView = itemView.findViewById<TextView>(R.id.job_name)
        val rvPtName: TextView = itemView.findViewById<TextView>(R.id.pt_name)
        val rvJobLocation: TextView = itemView.findViewById<TextView>(R.id.job_location)
        val rvJobExperience: TextView = itemView.findViewById<TextView>(R.id.job_experience_require)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout,parent,false)
        return JobViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
       val currentItem = dataList[position]

        holder.rvJobName.text = currentItem.job
        holder.rvPtName.text = currentItem.namePT
        holder.rvJobLocation.text = currentItem.location
        holder.rvJobExperience.text = currentItem.experience

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
     fun UpdateData(newList: List<JobData>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}