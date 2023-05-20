package com.example.travelmemories.ui.home

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelmemories.Memory

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fgiiiii"
    }
    val text: LiveData<String> = _text


    // private lateinit var memoryList: MutableLiveData<List<Memory>>
    private var memoryList: MutableLiveData<List<Memory>>? = null
    internal fun getMemoryList(): MutableLiveData<List<Memory>> {
        if (memoryList == null) {
            memoryList = MutableLiveData()
            loadMemories()
        }
        return memoryList as MutableLiveData<List<Memory>>
    }

    private fun loadMemories() {
        val fruitsStringList = ArrayList<Memory>()
        fruitsStringList.add(Memory(1,"m1","l1","img1","12.05.2020"))
        fruitsStringList.add(Memory(2,"m2","l2","img2","12.05.2019"))
        fruitsStringList.add(Memory(3,"m3","l3","img3","12.05.2018"))
        memoryList!!.postValue(fruitsStringList)
    }

}