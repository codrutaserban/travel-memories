package com.example.travelmemories.ui.contactUs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactUsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is contactUs Fragment"
    }
    val text: LiveData<String> = _text
}