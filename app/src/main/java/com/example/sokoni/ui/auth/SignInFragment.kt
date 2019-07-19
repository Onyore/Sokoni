package com.example.sokoni.ui.auth

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.auth_fragment.signin
import kotlinx.android.synthetic.main.fragment_forget_password.avi
import kotlinx.android.synthetic.main.fragment_forget_password.backtoauth
import kotlinx.android.synthetic.main.fragment_forget_password.signinback
import kotlinx.android.synthetic.main.fragment_sign_in.*
import com.example.sokoni.utils.Validator


class SignInFragment() : Fragment(), Parcelable {
    companion object{
        fun nextInstance() = SignInFragment()

    }
    private lateinit var viewModel: AuthViewModel

    constructor(parcel: Parcel) : this() {

    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignInFragment> {
        override fun createFromParcel(parcel: Parcel): SignInFragment {
            return SignInFragment(parcel)
        }

        override fun newArray(size: Int): Array<SignInFragment?> {
            return arrayOfNulls(size)
        }
    }

private fun Any.setOnclickListener(any: Any) {

}

            )}


