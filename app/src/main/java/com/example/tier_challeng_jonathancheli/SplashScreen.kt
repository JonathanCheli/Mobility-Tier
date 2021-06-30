package com.example.tier_challeng_jonathancheli

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


class SplashScreen : AppCompatActivity() {

    private val SPLASH_SCREEN_DELAY = 17000


    private var animationScooter: Animation?=null
    private var scooterLottie : LottieAnimationView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        animationScooter = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top)

        scooterLottie =  findViewById(R.id.id_scooter)
        Handler().postDelayed(Runnable {
            // Executed after timer is finished (Opens MainActivity)
            scooterLottie!!.animation  = animationScooter

            val intent = Intent(this@SplashScreen, MapsActivity::class.java)
            startActivity(intent)


            // Kills this Activity
            finish()
        }, SPLASH_SCREEN_DELAY.toLong())
    }
}