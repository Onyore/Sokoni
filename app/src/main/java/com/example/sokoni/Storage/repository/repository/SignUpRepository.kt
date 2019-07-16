package com.example.sokoni.Storage.repository.repository

import android.app.Application
import android.content.Context
import android.telecom.Call
import androidx.lifecycle.MutableLiveData
import com.example.sokoni.R
import com.example.sokoni.Storage.repository.PrefrenceManager
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.network.NetworkUtils
import com.example.sokoni.network.RequestService
import javax.security.auth.callback.Callback

class SignUpRepository(application: Application) {
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val signUpObservable = MutableLiveData<Resource<Oauth>>()
    private val context: Context

    init {
        context = application.applicationContext
    }

    fun signUp(parameters: Oauth) {
        setIsLoading()

        if (NetworkUtils.isConnected(context)) {
            excuteSignUp(parameters)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }

    private fun excuteSignUp(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").signUp(parameters.profile?.email, parameters.profile?.password, parameters.profile?.firstName, parameters.profile?.lastName, parameters.profile?.mobile)
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
                            setIsError("Error Registering You In")
                        }
                    } else {
                        setIsError("Error Registering You In")
                    }
                }
            })
        }
    }

    private fun setIsLoading() {
        signUpObservable.postValue(Resource.loading(null))
    }

    private fun setIsSuccesful(parameters: Oauth) {
        signUpObservable.postValue(Resource.success(parameters))
    }

    private fun setIsError(message: String) {
        signUpObservable.postValue(Resource.error(message, null))
    }
}


}