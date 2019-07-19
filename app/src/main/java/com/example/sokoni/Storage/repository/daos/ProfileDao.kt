package com.example.sokoni.Storage.repository.daos

import androidx.lifecycle.LiveData
import com.example.sokoni.models.oauth.Profile
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile LIMIT 1 ")
    fun getProfile(): LiveData<Profile>

    @Query("SELECT * FROM Profile LIMIT 1")
    fun fetch(): Profile

    @Insert(onConflict = REPLACE)
    fun insertProfile(model: Profile)

    @Update
    fun updateProfile(model: Profile)

    @Delete
    fun deleteProfile(model: Profile)

    @Query("DELETE FROM Profile")
    fun delete()
}
