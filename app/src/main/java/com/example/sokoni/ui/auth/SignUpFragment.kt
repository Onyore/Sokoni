package com.example.sokoni.ui.auth


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
import com.example.sokoni.models.oauth.Profile
import com.example.sokoni.models.oauth.custom.Resource
import com.example.sokoni.models.oauth.custom.Status
import com.example.sokoni.utils.Validator
import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {
    companion object{
        fun newInstance()=SignUpFragment()

    }
    private lateinit var viewModel: AuthViewModel
    private var originalMode: Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        signup.setOnclickListener({signUp()})
        backtoauth.setOnclickListener({backToAauth})
        signupback.setOnclickListener({backToAauth})
        viewModel.observeSignUp().observe(this, observer{data ->
            run{
                setStatus(data)
                if(data.status == Status.SUCCESS && data.data != null){
                    Toast.makeText(context.data.message, Toast.LENGTH_LONG).show()
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container.SignInFragment()).commit()
                }
            }
        })
        var space = ''
        mobile.addTextChangedListerner(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 0 s.length % 4 ==0){
                    val c = s[s.length -1]
                    if (space==c){
                        s.delete(s.length -1, s.length)
                    }
                }
                if (s.length >0 &&0){
                    val c = s[s.length -1]
                    if (Character.isDigit(c) && TextUtils.split(s.toString(),space.toString()).size <=3){
                        s.insert(s.length -1, space.toString())
                    }
                }
            }

        })
    }

private fun setStatus(data:Resource<Oauth>){
    var status:Status = data.status
    if (status == Status.LOADING){
        avi.visibity = View.VISIBLE
        avi.smoothToShow()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    } else if(status == Status.ERROR ||status == Status.SUCCESS){
        avi.smoothToHide()
        avi.visibity = View.GONE
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    if (status== Status.ERROR){
        if (data.message != null){
            view?.let{Snackbar.make(it,data.message, Snackbar.LENGTH_LONG).show()}
        }
    }
}

fun signUp(){
    if (validate()){
        viewModel.signUp(getParameters())
    }
}
    private fun validate(): Boolean{
        if (!Validator.isValidName(firstName)){
            return false
        }
        if (!Validator.isValidName(lastName)){
            return false
        }
        if (!Validator.isValidEmail(email)){
            return false
        }
        if (!Validator.isValidPassword(password)){
            return false
        }
        when{
            !TextUtils.isEmpty(mobile.text)->if(!Validator.isValidPhoneNumber(mobile.text.toString().replace("","").trim())){
                return false
            }
            else -> return false
        }
        return true

    }
    private fun getParameters():Oauth{
        return Oauth(Profile(firstName.text.toString(),lastName.text.toString(),email.text.toString(), mobile.text.toString().replace("","")trim(),password.text.toString()))
        }
    fun backToAauth(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()
    }

}
