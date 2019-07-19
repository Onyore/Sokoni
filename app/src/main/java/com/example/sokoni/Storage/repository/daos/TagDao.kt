@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.sokoni.Storage.repository.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sokoni.models.oauth.Main.Tag
import androidx.room.Delete as AndroidxRoomDelete
import java.nio.charset.CodingErrorAction.REPLACE as REPLACE

@Dao
interface TagDao {
    @Query("SELECT * from Tag ")
    fun fetch(): LiveData<List<Tag>>

    @Query("SELECT * from Tag  ")
    fun get(): List<Tag>

    @Insert(onConflict = REPLACE)
    fun insert(model: List<Tag>)

    @Update
    fun update(model: Tag)

    @AndroidxRoomDelete
    fun delete(model: Tag)

    @Query("DELETE FROM Tag")
    fun delete()
}
