package com.example.sokoni.Storage.repository.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.telecom.Call
import android.util.Log
import com.example.sokoni.R
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.PasswordRecoveryModel
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.network.NetworkUtils
import com.example.sokoni.network.RequestService
import javax.security.auth.callback.Callback


class RecoverPasswordUpRepository(application: Application) {
     val recoverPasswordObservable = MutableLiveData<Resource<PasswordRecoveryModel>>()
     val updatePasswordObservable = MutableLiveData<Resource<PasswordRecoveryModel>>()
    private val context: Context

    init {
        context = application.applicationContext
    }

    fun recoverPassword(parameters: Oauth) {
        setIsLoading(Observable.RECOVERY)

        if (NetworkUtils.isConnected(context)) {
            excuteRecoverPassword(parameters)
        } else {
            setIsError(Observable.RECOVERY, context.getString(R.string.no_connection))
        }
    }

    fun update(parameters: Oauth) {
        setIsLoading(Observable.UPDATE)

        if (NetworkUtils.isConnected(context)) {
            excuteUpdatePassword(parameters)
        } else {
            setIsError(Observable.UPDATE, context.getString(R.string.no_connection))
        }
    }

    private fun excuteRecoverPassword(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").recoverPassword(parameters.profile?.mobile)
            call.enqueue(object : Callback<PasswordRecoveryModel> {
                override fun onFailure(call: Call<PasswordRecoveryModel>?, t: Throwable?) {
                    Log.d("recoverpasssdedwer", t.toString())
                    setIsError(Observable.RECOVERY, t.toString())
                }

                override fun onResponse(call: Call<PasswordRecoveryModel>?, response: Response<PasswordRecoveryModel>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1 && !response.body()?.error!!) {
                                setIsSuccesful(Observable.RECOVERY, response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(Observable.RECOVERY, it) }
                            }
                        } else {
                            setIsError(Observable.RECOVERY, "Error In Connection")
                        }
                    } else {
                        setIsError(Observable.RECOVERY, "Error In Connection")
                    }
                }
            })
        }
    }

    private fun excuteUpdatePassword(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {
            Log.d("tokensda", parameters.profile?.token)
            val call = RequestService.getService(parameters.profile?.token!!).updatePassword(parameters.profile?.password)
            call.enqueue(object : Callback<PasswordRecoveryModel> {
                override fun onFailure(call: Call<PasswordRecoveryModel>?, t: Throwable?) {
                    setIsError(Observable.UPDATE, t.toString())
                }

                override fun onResponse(call: Call<PasswordRecoveryModel>?, response: Response<PasswordRecoveryModel>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1 && !response.body()?.error!!) {
                                setIsSuccesful(Observable.UPDATE, response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(Observable.UPDATE, it) }
                            }
                        } else {
                            setIsError(Observable.UPDATE, "Error In Connection")
                        }
                    } else {
                        setIsError(Observable.UPDATE, "Error In Connection")
                    }
                }
            })
        }
    }

    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.RECOVERY -> recoverPasswordObservable.postValue(Resource.loading(null))
            Observable.UPDATE -> updatePasswordObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.RECOVERY -> recoverPasswordObservable.postValue(Resource.success(data as PasswordRecoveryModel))
            Observable.UPDATE -> updatePasswordObservable.postValue(Resource.success(data as PasswordRecoveryModel))
        }
    }

    private fun setIsError(observable: Observable, message: String) {
        when (observable) {
            Observable.UPDATE -> updatePasswordObservable.postValue(Resource.error(message, null))
            Observable.RECOVERY -> recoverPasswordObservable.postValue(Resource.error(message, null))
        }
    }

    enum class Observable {
        RECOVERY,
        UPDATE
    }
}


