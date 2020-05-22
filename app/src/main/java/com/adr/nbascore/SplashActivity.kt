package com.adr.nbascore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

        iv_logo_splash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation))
    }
}
