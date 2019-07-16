package com.example.sokoni.network

import android.provider.ContactsContract
import android.telecom.Call
import com.example.sokoni.models.oauth.Oauth
import com.example.sokoni.models.oauth.PasswordRecoveryModel
import com.example.sokoni.models.oauth.UpdateProfileResponse
import com.example.sokoni.models.Profile
import com.example.sokoni.models.repository
import com.example.sokoni.models.Cart

import retrofit2.Call
import retrofit2.http.*





interface EndPoints {
    @FormUrlEncoded
    @POST("oauth/signin.php")
    fun signIn(@Field("email") email: String?, @Field("password") password: String?): Call<Oauth>

    @FormUrlEncoded
    @POST("aouth/sendPasswordCode.php")
    fun recoverPassword(@Field("phone") phone: String?): Call<PasswordRecoveryModel>

    @FormUrlEncoded
    @POST("aouth/updatePassword.php")
    fun updatePassword(@Field("password") password: String?): Call<PasswordRecoveryModel>



    @FormUrlEncoded
    @POST("aouth/signu1p.php")
    fun signUp(@Field("email") email: String?, @Field("password") password: String?, @Field("firstName") firstName: String?, @Field("lastName") lastName: String?, @Field("mobile") mobile: String?): Call<Oauth>

    @POST("aouth/update.php")
    fun updateProfile(@Body item: ContactsContract.Profile): Call<UpdateProfileResponse>

    @GET("base/baseApi.php")
    fun baseApi(): Call<BaseData>

    // http://calista.co.ke/sokoni/api/v1/favorites/addFavourite.php
    @FormUrlEncoded
    @POST("favorites/addFavourite.php")
    fun addFavourite(@Field("productId") productId: String?): Call<ProductData>

    @FormUrlEncoded
    @POST("favorites/unFavourite.php")
    fun unFavourite(@Field("productId") productId: String?): Call<ProductData>

    @POST("favorites/favourites.php")
    fun favourites(): Call<ProductsData>

    @FormUrlEncoded
    @POST("products/rate.php")
    fun rateProduct(@Field("productId") productId: String?, @Field("rating") rate: String?): Call<ProductData>

    @FormUrlEncoded
    @POST("products/product.php")
    fun product(@Field("productId") productId: String?): Call<ProductData>

    @FormUrlEncoded
    @POST("products/productDetails.php")
    fun productDetails(@Field("productId") productId: String?): Call<ProductDetailData>

    @FormUrlEncoded
    @POST("products/productReviews.php")
    fun productReviews(@Field("productId") productId: String?): Call<ProductReviewData>

    @FormUrlEncoded
    @POST("products/search.php")
    fun searchProduct(@Field("categoryId") categoryId: String?, @Field("searchTag") tag: String?, @Field("minPrice") minPrice: String?, @Field("maxPrice") maxPrice: String?, @Field("priceOrder") priceOrder: String?): Call<ProductsData>

    @POST("cart/addCartItem.php")
    fun addCartItem(@Body item: AddItem): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("cart/cartItems.php")
    fun cartItems(@Field("cartId") cartId: Int?): Call<CartItemsData>

    @FormUrlEncoded
    @POST("cart/deleteCartItem.php")
    fun deleteCartItem(@Field("cartId") cartId: Int?, @Field("cartItemId") cartItemId: Int?): Call<CartItemsData>

    @FormUrlEncoded
    @POST("cart/addItem.php")
    fun addCartItem(@Field("cartId") cartId: Int?, @Field("cartItemId") cartItemId: Int?): Call<CartItemsData>

    @FormUrlEncoded
    @POST("cart/removeItem.php")
    fun removeCartItem(@Field("cartId") cartId: Int?, @Field("cartItemId") cartItemId: Int?): Call<CartItemsData>

    @POST("address/address.php")
    fun addresses(): Call<AddressData>

    @POST("address/defaultAddress.php")
    fun defaultAddress(): Call<DefaultAddressData>

    @FormUrlEncoded
    @POST("address/delete.php")
    fun deleteAddress(@Field("addressId") addressId: Int?): Call<AddressData>

    @FormUrlEncoded
    @POST("address/add.php")
    fun addAddress(@Field("name") name: String?, @Field("phone") phone: String?
                   , @Field("address") address: String?, @Field("lat") lat: String?, @Field("lon") lon: String?
                   , @Field("desc") desc: String?, @Field("countryId") countryId: String?, @Field("countyId") countyId: String?
                   , @Field("townId") townId: String?, @Field("street") street: String?, @Field("isDefault") isDefault: Boolean?
    ): Call<AddressData>

    @FormUrlEncoded
    @POST("address/default.php")
    fun defaultAddress(@Field("addressId") addressId: Int?): Call<AddressData>

    @POST("locations/countries.php")
    fun getCountries(): Call<CountriesData>

    @FormUrlEncoded
    @POST("locations/counties.php")
    fun getCounties(@Field("countryId") countryId: Int?): Call<CountiesData>

    @FormUrlEncoded
    @POST("locations/towns.php")
    fun getTown(@Field("countyId") countyId: Int?): Call<TownsData>

    @POST("categories/categories.php")
    fun getCategories(): Call<CategoryData>

    @FormUrlEncoded
    @POST("categories/subCategories.php")
    fun getSubCategories(@Field("categoryId") categoryId: Int?): Call<SubCategoryData>

    @POST("billing/billings.php")
    fun billings(): Call<BillingsData>

    @POST("billing/defaultBilling.php")
    fun defaultBilling(): Call<DefaultBillingData>

    @FormUrlEncoded
    @POST("billing/delete.php")
    fun deleteBilling(@Field("billingId") addressId: Int?): Call<BillingsData>

    @FormUrlEncoded
    @POST("billing/add.php")
    fun addBilling(@Field("billingId") billingId: Int?, @Field("detail") detail: String?): Call<BillingsData>

    @FormUrlEncoded
    @POST("billing/default.php")
    fun defaultBilling(@Field("billingId") billingId: Int?): Call<BillingsData>

    @POST("billing/methods.php")
    fun billingMethods(): Call<BillingMethodsData>

    @POST("payment/pay.php")
    fun pay(@Body item: CartItemsData): Call<PayOrder>

    @FormUrlEncoded
    @POST("payment/payPending.php")
    fun payPending(@Field("orderId") orderId: String, @Field("phone") Phone: String, @Field("firebaseToken") firebaseToken: String, @Field("amount") amount: String): Call<PayOrder>

    @POST("orders/pending.php")
    fun pendingOrders(): Call<OrderData>

    @POST("orders/completed.php")
    fun completedOrders(): Call<OrderData>

    @FormUrlEncoded
    @POST("products/searchProducts.php")
    fun search(@Field("categoryId") categoryId: String, @Field("tag") tag: String, @Field("name") name: String, @Field("orderBy") orderBy: Int): Call<ProductsData>


}
