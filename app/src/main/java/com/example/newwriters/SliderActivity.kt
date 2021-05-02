package com.example.newwriters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.newwriters.ui.adapter.IntroSliderAdapter
import com.example.newwriters.ui.model.IntroSlide
import com.google.android.material.button.MaterialButton

class SliderActivity : AppCompatActivity() {
    private lateinit var introSliderViewPager: ViewPager2
    private lateinit var indicatorContainer: LinearLayout
    private lateinit var buttonNext: MaterialButton
    private lateinit var textSkipIntro: TextView
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide("Restaurant Management System",
                "Restaurant Management System",
                R.drawable.logo_2),

            IntroSlide("Restaurant Management System",
                "Restaurant Management System",
                R.drawable.logo_2)
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
    }
}