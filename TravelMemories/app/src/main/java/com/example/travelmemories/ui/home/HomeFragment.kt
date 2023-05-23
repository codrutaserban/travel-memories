package com.example.travelmemories.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmemories.MainActivity
import com.example.travelmemories.Memory
import com.example.travelmemories.MemoryListAdapter
import com.example.travelmemories.TravelMemoriesDb
import com.example.travelmemories.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    var data= MutableLiveData<List<Memory>>()
    var da:List<Memory> = ArrayList<Memory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        root = binding.root


        Log.d("Home fragment ", "bind recycler")
        recyclerView = binding.recyclerViewHome
        val adapter = MemoryListAdapter(listOf(Memory(1, "aa", "b", "c", "d")))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        homeViewModel.getMemoryList(root.context)
            .observe(viewLifecycleOwner, Observer<List<Memory>> { memoryList ->
                // update UI
                data.value = memoryList
                da = memoryList!!
                Log.d("Home Fragment ", "Data Send: " + data.value!!.size.toString())
                Log.d("HomeFragment", "In observer: da:$da ")
                Log.d("HomeFragment", "In observer: data:$data ")

                adapter.updateList(memoryList!!)
            })

//        homeViewModel.getMemoryList(root.context).observe(viewLifecycleOwner, Observer<List<Memory>>{
//                memoryList -> adapter.updateList(memoryList)
//        })

//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                memoriesDAO.deleteAll()
//            }
//        }

//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                homeViewModel.getDummyMemories().forEach {
//                    memoriesDAO.insert(it)
//                }
//            }
//        }

        return root
    }

    override fun onStart() {
        super.onStart()
        val memoriesDAO =  TravelMemoriesDb.getInstances(root.context).memoriesDAO()
        lifecycleScope.launch(Dispatchers.IO) {
            val aux =ArrayList<Memory>()
            memoriesDAO.getAll().forEach{
                val m = Memory.fromEntity(it)
                aux.add(m)
            }
            homeViewModel.updateMemoryList(aux)
        }
        (activity as MainActivity?)!!.getFloatingActionButton()?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}