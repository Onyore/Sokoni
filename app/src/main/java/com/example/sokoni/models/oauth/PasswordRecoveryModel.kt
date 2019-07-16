package com.example.sokoni.models.oauth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PasswordRecoveryModel {
    @SerializedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("profile")
    @Expose
    var profile: Profile? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null



}