package com.estiam.arpenteurs.data

import android.content.Context
import android.content.SharedPreferences

class TemporaryData (
    private val context: Context,
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("tempData", Context.MODE_PRIVATE)
){
    private val currentItinerary: String = "Current-itinerary"
    private val chosenItinerary: String = "Chosen-itinerary"

    fun setCurrItinerary(prefValue: String) =
        sharedPreferences.edit().putString(currentItinerary, prefValue).apply()

    fun getCurrItinerary(): String? =
        sharedPreferences.getString(currentItinerary, "")

    fun setChosenItinerary(chosenItinerary: String) =
        sharedPreferences.edit().putString(chosenItinerary, chosenItinerary).apply()

    fun getChosenItinerary(): String? =
        sharedPreferences.getString(chosenItinerary, "")

}