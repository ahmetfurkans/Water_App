package com.patika.week3

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.patika.week3.databinding.FragmentHomeBinding
import kotlin.math.roundToInt


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var targetWater = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // Create on click listeners for events
        binding.apply {
            smallBottle.setOnClickListener {
                drinkWater(0.2)
            }
            mediumBottle.setOnClickListener {
                drinkWater(0.5)
            }
            bigBottle.setOnClickListener {
                drinkWater(1.0)
            }
            clearAll.setOnClickListener {
                targetWater = 0.0
                drinkWater(0.0)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // set data from saveInstanceState
        outState.putDouble("targetWater", targetWater)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // get data from saveInstanceState
        targetWater = savedInstanceState?.getDouble("targetWater", 0.0) ?: 0.0
        drinkWater(0.0)
    }

    // Change background height according to water level
    private fun backgroundColor() {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val ratio = targetWater / 3.0
        val height = (screenHeight * ratio).toInt()

        val params = binding.background.layoutParams
        params.height = height
        binding.background.layoutParams = params
    }

    // Add target water and apply changes to ui
    private fun drinkWater(bottle: Double) {
        targetWater += bottle
        backgroundColor()
        textChange()
    }

    // Change text acording to waterlevel
    private fun textChange() {
        binding.apply {
            val water = ((3.0 - targetWater) * 100.0).roundToInt() / 100.0
            when (targetWater) {
                0.0 -> {
                    header1.text = "Merhaba! \nBugün su içtin mi?"
                    header2.text =
                        "Bugünkü su ihtiyacını karşılamak için \n3 litre daha su içmelisin."
                }
                in 0.2..2.9 -> {
                    header1.text = "Tebrikler! \nDoğru yoldasın."
                    header2.text =
                        "Bugünkü su ihtiyacını karşılamak için \n$water litre daha su içmelisin."
                }
                in 3.0..5.0 -> {
                    header1.text = "Muhteşem! \nKendine iyi baktın."
                    header2.text = "Bugün vücüdun için gereken su miktarının tamamını karşıladın."
                }
            }
        }
    }

}