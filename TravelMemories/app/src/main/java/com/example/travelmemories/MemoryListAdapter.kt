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
import com.example.travelmemories.databinding.MemoryRecyclerViewItemBinding

class MemoryListAdapter(private var memories: List<Memory>): ListAdapter<Memory,MemoryListAdapter.MemoryViewHolder>(RowItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate (R.layout.memory_recycler_view_item,parent,false)
        val binding = MemoryRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = MemoryViewHolder.from(parent)
        holder.itemView.setOnClickListener {
            Toast.makeText(parent.context, "item in recycler view clicked ${holder.binding.memoryItemName.text}", Toast.LENGTH_SHORT).show()
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

    class MemoryViewHolder private constructor(val binding: MemoryRecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root) {
//        var textViewName: TextView = itemView.findViewById<TextView>(R.id.memory_item_name)
//        private var textViewLocation: TextView =itemView.findViewById<TextView>(R.id.memory_item_location)
//        private var textViewDate: TextView =itemView.findViewById<TextView>(R.id.memory_item_date)
        companion object {
            fun from(parent: ViewGroup): MemoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemoryRecyclerViewItemBinding .inflate(layoutInflater, parent, false)
                return MemoryViewHolder(binding)
            }
        }

        fun bind(item:Memory){//, position:Int){
            Log.d(" Home Adapter ", "bind date $item")

            with(binding) {
                binding.memoryItemName.text = item.name
                binding.memoryItemLocation.text = item.location
                binding.memoryItemDate.text = item.date
            }
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