package com.estiam.arpenteurs.ui.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estiam.arpenteurs.R
import com.estiam.arpenteurs.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.estiam.arpenteurs.MainActivity
import com.google.android.gms.maps.MapFragment

/**
 * [Fragment] pour afficher la map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        Log.d("debug_me", "Début des logs")
        Log.d("debug_me", mapFragment.toString())
        Log.d("debug_me", R.id.map_fragment.toString())
        Log.d("debug_me", "Fin des logs")

        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    /**
     * Adds marker representations of the places list on the provided GoogleMap object
     */
    private fun addMarkers(googleMap: GoogleMap) {
        Log.d("debug_me", "Lancement de addMarkers")
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.latLng)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}