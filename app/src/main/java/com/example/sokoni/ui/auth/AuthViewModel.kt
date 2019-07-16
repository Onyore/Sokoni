package com.example.sokoni.ui.auth

import android.arch.lifecycle.LiveData
import android.app.Application
import android.arch.lifecycle.MediatorLiveData
import androidx.lifecycle.AndroidViewModel
import com.example.sokoni.Storage.repository.SignInRepository
import com.example.sokoni.Storage.repository.repository.RecoverPasswordUpRepository
import com.example.sokoni.Storage.repository.repository.SignUpRepository
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.PasswordRecoveryModel
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.storage.repository.SignInRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    internal var signInRepository: SignInRepository
    internal var signUpRepository: SignUpRepository
    internal var recoverPasswordUpRepository: RecoverPasswordUpRepository
    private val signInObservable = MediatorLiveData<Resource<Oauth>>()
    private val signUpObservable = MediatorLiveData<Resource<Oauth>>()
    private val recoverPasswordObservable = MediatorLiveData<Resource<PasswordRecoveryModel>>()
    private val updatePasswordObservable = MediatorLiveData<Resource<PasswordRecoveryModel>>()
    init {
        signInRepository = SignInRepository(application)
        signUpRepository = SignUpRepository(application)
        recoverPasswordUpRepository = RecoverPasswordUpRepository(application)
        signInObservable.addSource(signInRepository.signInObservable) { data -> signInObservable.setValue(data) }
        signUpObservable.addSource(signUpRepository.signUpObservable) { data -> signUpObservable.setValue(data) }
        recoverPasswordObservable.addSource(recoverPasswordUpRepository.recoverPasswordObservable) { data -> recoverPasswordObservable.setValue(data) }
        updatePasswordObservable.addSource(recoverPasswordUpRepository.updatePasswordObservable) { data -> updatePasswordObservable.setValue(data) }
    }
    fun signIn(parameters: Oauth) {
        signInRepository.signIn(parameters)
    }
    fun observeSignIn(): LiveData<Resource<Oauth>> {
        return signInObservable
    }
    fun signUp(parameters: Oauth) {
        signUpRepository.signUp(parameters)
    }
    fun observeSignUp(): LiveData<Resource<Oauth>> {
        return signUpObservable
    }
    fun recoverPassword(parameters: Oauth) {
        recoverPasswordUpRepository.recoverPassword(parameters)
    }
    fun observeRecoverPassword(): LiveData<Resource<PasswordRecoveryModel>> {
        return recoverPasswordObservable
    }
    fun saveProfile(data: Oauth) {
        signInRepository.saveProfile(data)
    }
    fun observeUpdatePassword(): LiveData<Resource<PasswordRecoveryModel>> {
        return updatePasswordObservable
    }
    fun updatePassword(data: Oauth) {
        recoverPasswordUpRepository.update(data)
    }
}
