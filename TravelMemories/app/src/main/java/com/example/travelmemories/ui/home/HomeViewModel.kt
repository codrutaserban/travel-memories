package com.example.travelmemories.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelmemories.Memory
import com.example.travelmemories.MemoryEntity

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fgiiiii"
    }
    val text: LiveData<String> = _text


    // private lateinit var memoryList: MutableLiveData<List<Memory>>
    private var memoryList: MutableLiveData<List<Memory>>? = null
    internal fun getMemoryList(context: Context): MutableLiveData<List<Memory>> {
        if (memoryList == null) {
            memoryList = MutableLiveData()
           //loadMemories()



        //val memoriesDAO =  TravelMemoriesDb.getInstances(context).memoriesDAO()

        }
        return memoryList as MutableLiveData<List<Memory>>
    }

    fun getDummyMemories(): MutableList<MemoryEntity> {
        val list = mutableListOf<MemoryEntity>()

        list.add(MemoryEntity(0,"m1","l1",0.0,0.0,"12.05.2020","type",29,"notes1","img1"))
        return list
    }

    private fun loadMemories() {
        val memoriesList = ArrayList<Memory>()
        memoriesList.add(Memory(1,"m1","l1","img1","12.05.2020"))
        memoriesList.add(Memory(2,"m2","l2","img2","12.05.2019"))
        memoriesList.add(Memory(3,"m3","l3","img3","12.05.2018"))
        memoryList!!.postValue(memoriesList)
    }

    fun updateMemoryList(newMemoryList: List<Memory>){
        Log.d("HomeViewModel", "In updateMemoryList")

        memoryList!!.postValue(newMemoryList)
    }
}