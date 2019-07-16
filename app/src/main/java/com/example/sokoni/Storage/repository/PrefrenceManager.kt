package com.example.sokoni.Storage.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.sokoni.models.oauth.Oauth

class PrefrenceManager(internal var _context: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor
    internal var PRIVATE_MODE = 0

    companion object {
        private val LOGIN_STATUS = "LOGIN_STATUS"
        private val PROFILE = "sokoni_user_profile"
        private val FIREBASE_TOKEN = "firebasetoken"
        private val PREF_NAME = "sokoni_prefrences"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setFirebase(token: String) {
        editor.putString(FIREBASE_TOKEN, token)
        editor.commit()
    }

    fun getFirebase(): String {
        Log.d("firebaseToken", pref.getString(FIREBASE_TOKEN, ""))

        return pref.getString(FIREBASE_TOKEN, "")!!
    }

    fun setLoginStatus(status: Int) {
        editor.putInt(LOGIN_STATUS, status)
        editor.commit()
    }

    fun logOut() {
        clearUser()
    }

    fun getLoginStatus(): Int {
        return pref.getInt(LOGIN_STATUS, 0)
    }

    fun saveProfile(data: Oauth) {
        editor.putString(Gson().toJson(data), PROFILE)

        editor.commit()
    }
}