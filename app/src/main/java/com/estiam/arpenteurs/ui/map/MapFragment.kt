package com.estiam.arpenteurs.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.estiam.arpenteurs.R
import com.estiam.arpenteurs.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Pour appliquer des modifications une fois la map chargée
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Récupère le fragment grâce à son ID
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        Log.d("debug_me", "Début des logs")
        Log.d("debug_me", mapFragment.toString())
        Log.d("debug_me", R.id.map_fragment.toString())
        Log.d("debug_me", "Fin des logs")

        //Lance la fonction addMarkers
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }
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