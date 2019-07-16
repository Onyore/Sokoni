package com.example.sokoni.models.oauth.Main

class Tag {
    @SerializedName("id")
    @Expose
    @PrimaryKey()
    var id:Int? = null

    @SerializedName("tag")
    @Expose
    var tag:String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null


}