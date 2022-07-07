package com.estiam.arpenteurs.ui.map

import com.estiam.arpenteurs.R
import com.estiam.arpenteurs.model.Place
import com.estiam.arpenteurs.model.PlaceResponse
import com.estiam.arpenteurs.model.toPlace
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Reads a list of place JSON objects from the file places.json
 */
class PlacesReader(private val context: MapFragment) {

    // GSON object responsible for converting from JSON to a Place object
    private val gson = Gson()

    // InputStream representing places.json
    private val inputStream: InputStream
        get() = context.resources.openRawResource(R.raw.randos)

    /**
     * Reads the list of place JSON objects in the file places.json
     * and returns a list of Place objects
     */
    fun read(): List<Place> {
        val itemType = object : TypeToken<List<PlaceResponse>>() {}.type
        val reader = InputStreamReader(inputStream)
        return gson.fromJson<List<PlaceResponse>>(reader, itemType).map {
            it.toPlace()
        }
    }
}