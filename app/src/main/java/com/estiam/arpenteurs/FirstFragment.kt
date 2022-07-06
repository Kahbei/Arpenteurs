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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlinx.coroutines.delay as delay2

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

        val scope = CoroutineScope(Dispatchers.Main)
        val firstCallTime = kotlin.math.ceil(System.currentTimeMillis() / 60_000.0).toLong() * 60_000


        val parentJob = scope.launch {
            // suspend till first minute comes after some seconds
            delay2(timeMillis = firstCallTime - System.currentTimeMillis())
            while (true) {
                val bil = LocalDateTime.now();
                val coorN = "${ (0..100).random() }N"
                val coorS = "${ (0..100).random() }S"
                val coorW = "${ (0..100).random() }W"
                val coorE = "${ (0..100).random() }E"
                val test = " $bil : $coorN, $coorS, $coorW $coorE"
                launch {
                    FragmentViewModel.save(test)
                    populate()
                }
                delay2(60_000)  // 1 minute delay (suspending)
            }
        }

//        binding.textView.text = FragmentViewModel.getSaved()

        binding.testBtn.setOnClickListener {
            parentJob.cancel()
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    fun populate(){
        binding.textView.text = FragmentViewModel.getSaved()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}