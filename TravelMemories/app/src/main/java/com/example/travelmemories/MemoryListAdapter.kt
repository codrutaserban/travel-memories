package com.example.travelmemories

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmemories.databinding.MemoryRecyclerViewItemBinding


class MemoryListAdapter(private var memories: List<Memory>): ListAdapter<Memory,MemoryListAdapter.MemoryViewHolder>(RowItemDiffCallback()) {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
       context=parent.context
        val holder = MemoryViewHolder.from(parent)
        return holder
    }

    override fun getItemCount(): Int {
        return memories.size
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val currentItem = memories[position]
        Log.d(" Home Adapter ", "on bind view holder")
        holder.bind(currentItem)
        holder.binding.cardMemory.setOnClickListener{
            val intent = Intent( context, MemoryActivity::class.java)
            intent.putExtra("action", "edit")
            intent.putExtra("memory_id", currentItem.id)
            context.startActivity(intent)
        }
    }

    fun updateList(data: List<Memory>) {
        Log.d("Home Adapter ", "update list")
        memories = data
        notifyDataSetChanged()
    }

    class MemoryViewHolder private constructor(val binding: MemoryRecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): MemoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemoryRecyclerViewItemBinding .inflate(layoutInflater, parent, false)
                return MemoryViewHolder(binding)
            }
        }

        fun bind(item:Memory){

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