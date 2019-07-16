package com.example.sokoni.Storage.repository.daos

import android.arch.lifecycle.LiveData
import com.example.sokoni.models.oauth.Cart.Cart
import java.nio.charset.CodingErrorAction.REPLACE





@Dao
interface CartDao {
    @Query("SELECT * from Cart LIMIT 1")
    fun fetch(): LiveData<Cart>

    @Query("SELECT * from Cart LIMIT 1")
    fun get(): Cart

    @Insert(onConflict = REPLACE)
    fun insert(model: Cart)

    @Update
    fun update(model: Cart)

    @Delete
    fun delete(model: Cart)

    @Query("DELETE FROM Cart")
    fun delete()
}
