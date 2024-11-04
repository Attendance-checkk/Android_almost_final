//package com.example.attendancecheckandroidtest.data.models;
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//class SharedViewModel : ViewModel() {
//    var isButtonEnabled = true
//    private set
//
//    fun disableButtonTemporarily() {
//        if (isButtonEnabled) {
//            isButtonEnabled = false
//            viewModelScope.launch {
//                delay(500)
//                isButtonEnabled = true
//            }
//        }
//    }
//}
