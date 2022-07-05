package com.estiam.arpenteurs.data

import android.content.Context
import android.content.SharedPreferences

class TemporaryData (
    private val context: Context,
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("tempData", Context.MODE_PRIVATE)
){

    fun setSavingPreference(prefValue: String) =
        sharedPreferences.edit().putString("Arpenteur-pref", prefValue).apply()

    fun areSavedPreference(dataPref: Int): String? =
        sharedPreferences.getString("Arpenteur-pref-$dataPref", "")

    fun setLastLocalization(itineraire: Int, lastLoca: String) =
        sharedPreferences.edit().putString("Arpenteur-lastLocalization-$itineraire", lastLoca).apply()

    fun getLastLocalization(itineraire: Int): String? =
        sharedPreferences.getString("Arpenteur-lastLocalization-$itineraire", "")
}