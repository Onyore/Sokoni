package com.example.sokoni

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


class SplashScreenActivity() : AppCompatActivity(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashScreenActivity,AuthActivity::class.java))

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<SplashScreenActivity> {
        override fun createFromParcel(parcel: Parcel): SplashScreenActivity {
            return SplashScreenActivity(parcel)
        }

        override fun newArray(size: Int): Array<SplashScreenActivity?> {
            return arrayOfNulls(size)
        }
    }
}

open class AppCompatActivity {

}
