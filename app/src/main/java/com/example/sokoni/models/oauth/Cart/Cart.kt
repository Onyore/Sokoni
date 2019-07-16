package com.example.sokoni.models.oauth.Cart
@Entity
class Cart {
    @SerializedNmae("id")
    @Expose
    @PrimaryKey()
    var id: Int? = null
    @SerializedName("cust_id")
    @Expose
    var custId: Int? = null
    @SerializedName("item_quality")
    @Expose
    var items_quality: Int? = null
    @SerializedNme("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedNmae("updated_at")
    @Expose
    var updatedAt: String? = null

    constructor(items_quality: Int?){
        this.items_quality=items_quality
    }
    constructor(id:Int?, custId:Int?, items_quality: Int?, createdAt:String?, updatedAt:Int? ){
        this.id=id
        this.custId=custId
        this.items_quality=items_quality
        this.createdAt= createdAt
        this.updatedAt=updatedAt
    }

}