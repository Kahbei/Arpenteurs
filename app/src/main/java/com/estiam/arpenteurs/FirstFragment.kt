package com.estiam.arpenteurs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.estiam.arpenteurs.data.TemporaryData
import com.estiam.arpenteurs.databinding.FragmentFirstBinding
import java.time.LocalDateTime
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment() : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val FragmentViewModel: FirstFragmentViewModel by viewModels {
        FirstFragmentViewModelFactory(TemporaryData(requireContext()))
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testBtn.setOnClickListener {
            var test = ""
            var bil = LocalDateTime.now();
            var coorN = "${ (0..100).random() }N"
            var coorS = "${ (0..100).random() }S"
            var coorW = "${ (0..100).random() }W"
            var coorE = "${ (0..100).random() }E"
            test = "|| $bil : $coorN, $coorS, $coorW, $coorE ||"

            for(i in 0..10){
                FragmentViewModel.save(test)
                bil = LocalDateTime.now();
                coorN = "${ (0..100).random() }N"
                coorS = "${ (0..100).random() }S"
                coorW = "${ (0..100).random() }W"
                coorE = "${ (0..100).random() }E"
                test += " $bil : $coorN, $coorS, $coorW $coorE ||"
            }
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}