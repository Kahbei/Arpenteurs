package com.estiam.arpenteurs.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.estiam.arpenteurs.R
import com.estiam.arpenteurs.databinding.FragmentMapBinding

/**
 * [Fragment] pour afficher la map
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        binding.buttonMap.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_mapFragment)
        }
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}