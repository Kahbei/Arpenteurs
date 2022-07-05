package com.estiam.arpenteurs

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estiam.arpenteurs.data.TemporaryData

class FirstFragmentViewModel(private val temporaryData: TemporaryData): ViewModel() {
    //private _state: Mutable
    fun save(str: String){
        temporaryData.setSavingPreference(str).let {
            Log.d("OUI","INSCRIPTION OK !!")
        }
    }
}

class FirstFragmentViewModelFactory(private val temporaryData: TemporaryData): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FirstFragmentViewModel(temporaryData) as T
    }
}