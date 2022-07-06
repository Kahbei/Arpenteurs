package com.estiam.arpenteurs

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estiam.arpenteurs.data.TemporaryData

class FirstFragmentViewModel(private val temporaryData: TemporaryData): ViewModel() {
    private val locationList: MutableList<String> = mutableListOf()

    fun save(str: String){
        locationList.add(str)

        val newStr = locationList.joinToString(";")
        temporaryData.setCurrItinerary(newStr).let {
            Log.d("OUI","INSCRIPTION OK !!")
        }
    }

    fun getSaved(): String? {
        return temporaryData.getCurrItinerary()
    }
}

class FirstFragmentViewModelFactory(private val temporaryData: TemporaryData): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FirstFragmentViewModel(temporaryData) as T
    }
}