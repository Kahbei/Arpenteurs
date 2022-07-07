package com.estiam.arpenteurs.ui.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.estiam.arpenteurs.BuildConfig.GOOGLE_MAPS_API_KEY
import com.estiam.arpenteurs.R
import com.estiam.arpenteurs.databinding.FragmentMapBinding
import com.estiam.arpenteurs.model.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode

/**
 * [Fragment] pour afficher la map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    private var mMap: GoogleMap? = null

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
            onMapReady(googleMap)
            showMapPosition(googleMap)
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

    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Define list to get all latlng for the route
        val path: MutableList<LatLng> = ArrayList()


        //Execute Directions API request
        val context = GeoApiContext.Builder()
            .apiKey("${GOOGLE_MAPS_API_KEY}")
            .build()
        /*
        val req = DirectionsApi.getDirections(context, "45.73641982012202,4.836886690686885",
            "45.757769333993515, 4.775306140604183")
            */

        val req = DirectionsApi.newRequest(context)
            .mode(TravelMode.WALKING)
            .optimizeWaypoints(true)
            .avoid(DirectionsApi.RouteRestriction.FERRIES)
            .avoid(DirectionsApi.RouteRestriction.HIGHWAYS)
            .origin("45.73641982012202,4.836886690686885")
            .destination("45.757769333993515, 4.775306140604183")

        try {
            val res = req.await()

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.size > 0) {
                val route = res.routes[0]
                if (route.legs != null) {
                    for (i in route.legs.indices) {
                        val leg = route.legs[i]
                        if (leg.steps != null) {
                            for (j in leg.steps.indices) {
                                val step = leg.steps[j]
                                if (step.steps != null && step.steps.size > 0) {
                                    for (k in step.steps.indices) {
                                        val step1 = step.steps[k]
                                        val points1 = step1.polyline
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            val coords1 = points1.decodePath()
                                            for (coord1 in coords1) {
                                                path.add(LatLng(coord1.lat, coord1.lng))
                                            }
                                        }
                                    }
                                } else {
                                    val points = step.polyline
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        val coords = points.decodePath()
                                        for (coord in coords) {
                                            path.add(LatLng(coord.lat, coord.lng))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            Log.d("debug_me", ex.localizedMessage)
        }

        //Draw the polyline
        if (path.size > 0) {
            val opts = PolylineOptions().addAll(path).color(Color.BLUE).width(6f)
            mMap!!.addPolyline(opts)
        }
    }

    //Voir la position actuel de la personne
    //Pour le moment elle utilise les coordonnées de Lyon
    //Le but serait qu'elle utilise les coordonnées GPS du téléphone
    //Ou bien le point de départ
    fun showMapPosition(googleMap: GoogleMap){
        mMap = googleMap
        val Lyon = LatLng(45.821810560685634, 4.823063163164788)
        //Proposer le zoom
        mMap!!.getUiSettings().setZoomControlsEnabled(true)
        //Mettre en évidence Lyon
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(Lyon, 11f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}