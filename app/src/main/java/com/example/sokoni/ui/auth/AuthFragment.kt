package com.example.sokoni.ui.auth

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sokoni.R

class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)


        signin.setOnClickListener({ signIn() })
        signup.setOnClickListener({ signUp() })
    }

    fun signIn(){
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, SignInFragment()).addToBackStack("signIn").commit()
    }

    fun signUp(){
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, SignUpFragment()).addToBackStack("signUp").commit()

    }

}
