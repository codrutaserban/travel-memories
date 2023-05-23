package com.example.travelmemories.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelmemories.Memory

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fgiiiii"
    }
    val text: LiveData<String> = _text


    private var memoryList: MutableLiveData<List<Memory>>? = null
    internal fun getMemoryList(context: Context): MutableLiveData<List<Memory>> {
        if (memoryList == null) {
            memoryList = MutableLiveData()
        }
        return memoryList as MutableLiveData<List<Memory>>
    }

    fun updateMemoryList(newMemoryList: List<Memory>){
        Log.d("HomeViewModel", "In updateMemoryList")

        memoryList!!.postValue(newMemoryList)
    }
}