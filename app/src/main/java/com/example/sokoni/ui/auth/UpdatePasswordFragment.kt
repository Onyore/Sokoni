package com.example.sokoni.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.sokoni.R
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.PasswordRecoveryModel
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.models.oauth.custom.Status
import com.example.sokoni.utils.Validator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_update_password.*
//import java.util.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UpdatePasswordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UpdatePasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UpdatePasswordFragment : Fragment() {
    companion object {
        fun newInstance(profile: Profile): UpdatePasswordFragment {
            val args = Bundle()
            args.putSerializable("phone", profile.mobile)
            args.putSerializable("token", profile.token)
            val fragment = UpdatePasswordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: AuthViewModel
    private var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        backtoauth.setOnClickListener { goBackToAuth() }
        update.setOnClickListener { update() }

        viewModel.observeUpdatePassword().observe(this, Observer { data ->
            run {
                setStatus(data)
                if (data.status == Status.SUCCESS && data.data != null) {
                    Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, SignInFragment()).commit()
                }
            }
        })
    }

    fun update() {
        if (validate()) {
            viewModel.updatePassword(getParameters())
        }
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
    }

    private fun validate(): Boolean {
        if (!Validator.isValidPassword(password)) {
            return false
        }

        return true
    }

    private fun getParameters(): Oauth =
        Oauth(Profile(password.text.toString(), arguments?.getString("phone"), arguments?.getString("token")))

    fun goBackToAuth() {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()
    }

    fun forgotPassword() {
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, ForgotPassword()).addToBackStack("forgotPassword").commit()
    }

    fun initGoogleSignIn() {
        login_in_button.setOnClickListener {
            Log.d("GoogleSignIn", "Clicked=")

            mGoogleSignInClient?.signOut()
            val signInIntent = mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, 2001)
        }



        try {
            mGoogleSignInClient?.signOut()
        } catch (nm: Exception) {
            nm.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("GoogleSignIn", "OnActivity:result =" + data.toString())
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        handleSignInResult(task, requestCode)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, requestCode: Int) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            signIn(account)
        } catch (e: ApiException) {
            view?.let { Snackbar.make(it, "Couldn't Sign You In Now..\nPlease try again later", Snackbar.LENGTH_LONG).show() }
        }
    }

    private fun signIn(account: GoogleSignInAccount?) {
        if (account != null) {
            val email = account.email
            val names = account.givenName
            val lastname = account.familyName
            val d = account.photoUrl
            val pass = account.id
            viewModel.signIn(Oauth(Profile(email, pass)))
        }
    }
}
