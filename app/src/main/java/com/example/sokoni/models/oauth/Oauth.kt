package com.example.sokoni.models.oauth

import android.provider.ContactsContract
import com.example.sokoni.models.oauth.Cart.Cart



class Oauth {
    @SerializedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("profile")
    @Expose
    var profile: ContactsContract.Profile? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("cart")
    @Expose
    var cart: Cart? = null


    @Ignore constructor(profile: ContactsContract.Profile?) {
        this.profile = profile
    }

    constructor()


}