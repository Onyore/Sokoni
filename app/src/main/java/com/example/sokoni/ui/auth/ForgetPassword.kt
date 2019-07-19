package com.example.sokoni.ui.auth


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast

import com.example.sokoni.R
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.PasswordRecoveryModel
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.models.oauth.custom.Status
import com.example.sokoni.utils.Validator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_confirm_password_code.avi
import kotlinx.android.synthetic.main.fragment_confirm_password_code.backtoauth
import kotlinx.android.synthetic.main.fragment_confirm_password_code.recoverPassword
import kotlinx.android.synthetic.main.fragment_confirm_password_code.signinback
import kotlinx.android.synthetic.main.fragment_forget_password.*

class ForgotPassword: Fragment() {
    companion object {
        fun newInstance() = ForgotPassword()
    }

    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        backtoauth.setOnClickListener({ goBackToAuth() })
        recoverPassword.setOnClickListener({recoverPassword()})
        signinback.setOnClickListener({ goBackToAuth() })

        viewModel.observeRecoverPassword().observe(this, Observer { data ->
            run {
                setStatus(data)
                if (data.status == Status.SUCCESS && data.data != null) {
                    Toast.makeText(context, data.data.message, Toast.LENGTH_LONG).show()
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container,
                            ConfirmPasswordCode.newInstance(data.data.code,
                                    getParameters().profile?.mobile!!,
                                    data.data.profile?.token!!)).commit()
                }
            }
        })
        val space = ' '

        phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 0 && s.length % 4 == 0) {
                    val c = s[s.length - 1]
                    if (space == c) {
                        s.delete(s.length - 1, s.length)
                    }
                }
                if (s.length > 0 && s.length % 4 == 0) {
                    val c = s[s.length - 1]
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3) {
                        s.insert(s.length - 1, space.toString())
                    }
                }
            }
        })
    }

    private fun setStatus(data: Resource<PasswordRecoveryModel>) {
        val status: Status = data.status

        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.smoothToHide()
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (data.message != null) {
                view?.let { Snackbar.make(it, data.message, Snackbar.LENGTH_LONG).show() }
            }
        }
        if (status == Status.SUCCESS) {
            if (data.message != null) {
                view?.let { data.data?.message?.let { it1 -> Snackbar.make(it, it1, Snackbar.LENGTH_LONG).show() } }
            }
        }
    }

    fun recoverPassword() {
        if (validate()) {
            viewModel.recoverPassword(getParameters())
        }
    }

    private fun validate(): Boolean {
        if (!Validator.isValidPhoneNumber(phone)) {
            return false
        }


        return true
    }

    private fun getParameters(): Oauth {
        return Oauth(Profile(phone.text.toString().replace(" ", "").trim()))
    }

    fun goBackToAuth() {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()
    }
}
