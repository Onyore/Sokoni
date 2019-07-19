package com.example.sokoni.models.oauth

import androidx.room.Entity
import androidx.room.Index


@Entity(
        indices = [(Index("id"))],
        primaryKeys = ["id"]
)
class Profile(trim: String) {
    @field:SerializedName("id")
    var id: Int? = 0
    @field:SerializedName("code")
    var code: String? = null
    @field:SerializedName("first_name")
    var firstName: String? = null
    @field:SerializedName("last_name")
    var lastName: String? = null
    @field:SerializedName("email")
    var email: String? = null
    @field:SerializedName("mobile")
    var mobile: String? = null
    @field:SerializedName("firebase_token")
    var firebaseToken: String? = null
    @field:SerializedName("password")
    var password: String? = null
    @field:SerializedName("token")
    var token: String? = null
    @field:SerializedName("created_at")
    var createdAt: String? = null
    @field:SerializedName("updated_at")
    var updatedAt: String? = null

annotation class SerializedName(val value: String)
}
