package com.example.sokoni.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.sokoni.R
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.utils.Validator
import kotlinx.android.synthetic.main.fragment_confirm_password_code.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfirmPasswordCode.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfirmPasswordCode.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class ConfirmPasswordCode : Fragment() {
    companion object {
        fun newInstance(code: String?, phone: String, token: String): ConfirmPasswordCode {
            val args = Bundle()
            args.putSerializable("code", code)
            args.putSerializable("phone", phone)
            args.putSerializable("token", token)
            val fragment = ConfirmPasswordCode()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_confirm_password_code, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        backtoauth.setOnClickListener({ goBackToAuth() })
        recoverPassword.setOnClickListener({ recoverPassword() })
        signinback.setOnClickListener({ goBackToAuth() })
    }

    fun recoverPassword() {
        if (validate()) {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, UpdatePasswordFragment.newInstance(Profile("", arguments?.getString("phone"), arguments?.getString("token")))).commit()
        }
    }

    private fun validate(): Boolean {
        if (!Validator.isValidCode(code, arguments?.getString("code"))) {
            return false
        }


        return true
    }

    private fun getParameters(): Oauth {
        return Oauth(Profile("", code.text.toString()))
    }

    fun goBackToAuth() {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()
    }
}
