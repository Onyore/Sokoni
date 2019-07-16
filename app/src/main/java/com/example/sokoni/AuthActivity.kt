package com.example.sokoni

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.sokoni.ui.auth.AuthFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthFragment.newInstance())
                    .commitNow()
        }


    }

    fun Window.getSoftInputMode() : Int {
        return attributes.softInputMode
    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }


}
