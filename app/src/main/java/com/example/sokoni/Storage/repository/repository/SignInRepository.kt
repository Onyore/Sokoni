package com.example.sokoni.storage.respository.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData


import android.telecom.Call
import androidx.lifecycle.LiveData
import com.example.sokoni.R
import com.example.sokoni.Storage.repository.PrefrenceManager
import com.example.sokoni.Storage.repository.SokoniDatabase
import android.os.Message
import com.example.sokoni.Storage.repository.daos.CartDao
import com.example.sokoni.Storage.repository.daos.ProfileDao
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.network.NetworkUtils
import com.example.sokoni.network.RequestService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback
import retrofit2.Response



class SignInRepository(application: Application) {

    private val profileDao: ProfileDao
    private val cartDao: CartDao
    private val db: SokoniDatabase
    private  val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
   public final val signInObservable: MutableLiveData<Resource<Oauth>>()

    val signInObservable = MutableLiveData<Resource<Oauth>>()
    val profileObservable = MutableLiveData<Resource<Oauth>>()

    private val context= Context
    init {
        db = SokoniDatabase.getDatabase(application)!!
        profileDao = db.profileDao()
        cartDao = db.cartDao()
        context = application.applicationContext

    }
    fun signIn(parameters: Oauth) {
        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            excuteSignIn(parameters)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }

    private fun excuteSignIn(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").signIn(parameters.profile?.email, parameters.profile?.password)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    setIsError(t.toString())
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1 && !response.body()?.error!!) {
                                setIsSuccesful(response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }
                        } else {
                            setIsError(response.toString())
                        }
                    } else {
                        setIsError("Error Logging In")
                    }
                }
            })
        }
    }

    private fun setIsLoading() {
        signInObservable.postValue(Resource.loading(null))
    }

    private fun setIsSuccesful(parameters: Oauth) {
        signInObservable.postValue(Resource.success(parameters))
    }

    private fun setIsError(message: String) {
        signInObservable.postValue(Resource.error(message, null))
    }

    fun saveProfile(data: Oauth) {
        profileDao.delete()
        data.profile?.let { profileDao.insertProfile(it) }
        data.cart?.let { cartDao.insert(it) }
        prefrenceManager.saveProfile(data)
    }

    fun getProfile(): LiveData<Profile> {
        return profileDao.getProfile()
    }

    fun fetchProfile(): Profile {
        return profileDao.fetch()
    }
}

