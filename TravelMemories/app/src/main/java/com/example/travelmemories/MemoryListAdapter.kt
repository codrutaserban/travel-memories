package com.example.travelmemories

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MemoryListAdapter(private var memories: List<Memory>): RecyclerView.Adapter<MemoryListAdapter.MemoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate (R.layout.memory_recycler_view_item,parent,false)
        val holder = MemoryViewHolder(layout)
        holder.itemView.setOnClickListener {
            Toast.makeText(parent.context, "item in recycler view clicked", Toast.LENGTH_LONG).show()
        }
        Log.d(" Home Adapter ", "on create view holder")
        return holder
    }

    override fun getItemCount(): Int {
        return memories.size
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val currentItem = memories[position]
        Log.d(" Home Adapter ", "on bind view holder")
        holder.bind(currentItem)
    }

    fun updateList(data: List<Memory>) {
        Log.d("Home Adapter ", "update list")
        memories = data
        notifyDataSetChanged()
    }

    class MemoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var textViewName: TextView
        private lateinit var textViewLocation: TextView
        private lateinit var textViewDate: TextView

        fun bind(item:Memory){//, position:Int){
            Log.d(" Home Adapter ", "bind date $item")

            textViewName = itemView.findViewById<TextView>(R.id.memory_item_name)
            textViewName.text = item.name

            textViewLocation = itemView.findViewById<TextView>(R.id.memory_item_location)
            textViewLocation.text = item.location

            textViewDate = itemView.findViewById<TextView>(R.id.memory_item_date)
            textViewDate.text = item.date
        }
    }

//    class RowItemDiffCallback : DiffUtil.ItemCallback<Memory> () {
//        override fun areItemsTheSame (oldItem : Memory, newItem : Memory) : Boolean {
//            return oldItem.name == newItem.name
//        }
//
//        override fun areContentsTheSame (oldItem : Memory, newItem : Memory) : Boolean {
//            return oldItem.location == newItem.location
//        }
//    }
}