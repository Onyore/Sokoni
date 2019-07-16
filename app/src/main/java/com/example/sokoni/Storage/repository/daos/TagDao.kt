package com.example.sokoni.Storage.repository.daos

import android.arch.lifecycle.LiveData
import com.example.sokoni.models.oauth.Main.Tag
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface TagDao {
@Query ("SELECT * from Tag")
fun fetch(): LiveData<List<Tag>>

    @Query("SELECT * from Tag")
    fun get():
            List<Tag>

    @Insert(onConflict =REPLACE )
    fun insert(model: List<Tag>)

    @update
    fun update(model:Tag)

    @Delete
    fun delete (model:Tag)

    @Query("DELETE FROM TAG")
    fun delete
}