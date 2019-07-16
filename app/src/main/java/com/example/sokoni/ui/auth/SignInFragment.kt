package com.example.sokoni.ui.auth

//import android.content.Context
//import android.net.Uri
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View

import com.example.sokoni.R


import android.view.ViewGroup
import android.view.WindowManager
import com.example.sokoni.MainActivity
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.models.oauth.custom.Status
import com.google.android.material.snackbar.Snackbar
import javax.xml.validation.Validator


class SignInFragment : android.support.v4.app.Fragment(){
    companion object{
        fun nextInstance() = SignInFragment()

    }
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        backtoauth.setOnClickListener({goBackToAuth})
        signin.setOnclickListener({signIn})
        signinback.setOnclickListener({goBackToAuth})
        forgotPassword.setOnclickListener({forgotPassword})
        viewModel.observeSignIn().observe(this, Observer { data ->
            run {
                setStatus(data)
                if (data.status == Status.SUCCESS && data.data != null) {

                    viewModel.saveProfile(data.data)
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()

                }
                )


    }

    fun signIn(){
        if (validate()){
            viewModel.signin(getParameter())
        }
    }
    fun goBackToAuth(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container.AuthFragment()).commit()
    }
    fun forgotPassword(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container,ForgetPassword()).addToBackStack("forgotPassword").commit()
    }

    private fun getParameter(): Oauth{
        return Oauth(Profile(email.text.toString(), password.text.toString()))
    }
    private fun validate(): Boolean{
        if (!Validator.isValidEmail(email)){
            return false
        }
        if (!Valiadator.isValidPassword(password)){
            return true
        }
    }
    private fun setStatus(data: Resource<Ouath>){
        val status: Status = data.status
        if(status== Status.LOADING){
            avi.visibilty= View.VISIBLE
            avi.smoothToShow()
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else if (status == Status.ERROR|| status== Status.SUCCESS){
            avi.smoothToHide()
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            if (status == Status.ERROR) {
                if (data.message != null) {
                    view?.let { Snackbar.make(it, data.message, Snackbar.LENGTH_LONG).show() }
                }


            }

    }
}




