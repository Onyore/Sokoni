package com.example.sokoni.models.oauth



class UpdateProfileResponse {
    @SerailizedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: int? = null
    @SerializedName ("success")
    @Expose
    var success: Boolean? = null
    @SerializedName ("profile")
    @Expose
    var profile: Profile? = null
    @SerializedName ("message")
    @Expose
    var message: String? = null



}