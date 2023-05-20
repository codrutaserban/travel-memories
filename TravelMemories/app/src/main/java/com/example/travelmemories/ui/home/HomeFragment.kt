package com.example.travelmemories.ui.home

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmemories.Memory
import com.example.travelmemories.MemoryListAdapter
import com.example.travelmemories.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    var data= MutableLiveData<List<Memory>>()
    var da:List<Memory> = ArrayList<Memory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        Log.d("Home fragment ", "bind recycler")
        recyclerView = binding.recyclerViewHome
        val adapter = MemoryListAdapter(listOf(Memory("aa","b","c","d")))
       recyclerView.adapter= adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        homeViewModel.getMemoryList().observe(viewLifecycleOwner, Observer<List<Memory>>{ memoryList ->
            // update UI
            data.value=memoryList
            da=memoryList!!
            Log.d("Home Fragment ","Data Send: "+data.value!!.size.toString())
            recyclerView.swapAdapter(MemoryListAdapter(da), false);
            adapter.updateList(da)


        })

        homeViewModel.getMemoryList().observe(viewLifecycleOwner, Observer<List<Memory>>{ memories ->
            adapter.updateList(memories)

        } )

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}