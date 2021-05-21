package com.example.newwriters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.adapter.IntroSliderAdapter
import com.example.newwriters.ui.home.HomeActivity
import com.example.newwriters.ui.model.IntroSlide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SliderActivity : AppCompatActivity() {
    private lateinit var introSliderViewPager: ViewPager2
    private lateinit var indicatorContainer: LinearLayout
    private lateinit var buttonNext: Button
    private lateinit var textSkipIntro: TextView
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide("New Writer",
                "Browse and read books and enhance you knowledge.",
                R.drawable.book),

            IntroSlide("New Writer",
                "Enjoy Our Books",
                R.drawable.booksa)
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        //calling the id from splash activity
        introSliderViewPager = findViewById(R.id.introSliderViewPager)
        indicatorContainer = findViewById(R.id.indicatorContainer)
        buttonNext = findViewById(R.id.buttonNext)
        textSkipIntro = findViewById(R.id.textSkipIntro)

        introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener{
            if(introSliderViewPager.currentItem+1< introSliderAdapter.itemCount){
                introSliderViewPager.currentItem+=1
            }
            else{
                checkSlider()
            }
        }

        textSkipIntro.setOnClickListener {
            checkSlider()
        }

    }
    private fun setupIndicators(){
        val indicators= arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i]= ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive
                )
                )
                this?.layoutParams=layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    //for buttons
    private fun setCurrentIndicator(index: Int){
        val childCount = indicatorContainer.childCount
        for(i in 0 until childCount){
            val imageView= indicatorContainer[i] as ImageView
            if(i==index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )

            }
        }
    }

    private fun checkSlider(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository = UserRepository()
                val response = repository.sliderCheck(ServiceBuilder.id!!)
                if (response.success == true) {
                    withContext(Main){
                        Intent(applicationContext, HomeActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@SliderActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}