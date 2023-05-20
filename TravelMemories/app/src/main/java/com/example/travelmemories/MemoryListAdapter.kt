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

class MemoryListAdapter(private var memories: List<Memory>): ListAdapter<Memory,MemoryListAdapter.MemoryViewHolder>(RowItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate (R.layout.memory_recycler_view_item,parent,false)
        val holder = MemoryViewHolder(layout)
        holder.itemView.setOnClickListener {
            Toast.makeText(parent.context, "item in recycler view clicked ${holder.textViewName.text}", Toast.LENGTH_LONG).show()
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
        //aici pot face si holder.textViewName daca le declar ca public
    }

    fun updateList(data: List<Memory>) {
        Log.d("Home Adapter ", "update list")
        memories = data
        notifyDataSetChanged()
    }

    class MemoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById<TextView>(R.id.memory_item_name)
        private var textViewLocation: TextView =itemView.findViewById<TextView>(R.id.memory_item_location)
        private var textViewDate: TextView =itemView.findViewById<TextView>(R.id.memory_item_date)

        fun bind(item:Memory){//, position:Int){
            Log.d(" Home Adapter ", "bind date $item")

            textViewName.text = item.name
            textViewLocation.text = item.location
            textViewDate.text = item.date
        }
    }

    class RowItemDiffCallback : DiffUtil.ItemCallback<Memory> () {
        // prima data apeleaza asta
        override fun areItemsTheSame (oldItem : Memory, newItem : Memory) : Boolean {
            return (oldItem.id == newItem.id) || oldItem == newItem
        }

        // daca areItemsTheSame da false, apeleaza areContentsTheSame
        override fun areContentsTheSame (oldItem : Memory, newItem : Memory) : Boolean {
            return oldItem.name == newItem.name && oldItem.location == newItem.location && oldItem.date == newItem.date
        }
    }
}