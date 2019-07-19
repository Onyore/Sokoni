package com.example.sokoni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.sokoni.Storage.repository.PrefrenceManager

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(PrefrenceManager(this).getLoginStatus()==0){
            startActivity(Intent(this@SplashScreenActivity,AuthActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
            finish()
        }
    }

    }


