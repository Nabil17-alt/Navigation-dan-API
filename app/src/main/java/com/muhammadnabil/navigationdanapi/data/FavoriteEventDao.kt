package com.muhammadnabil.navigationdanapi.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(favoriteEvent: FavoriteEvent)

    @Query("SELECT * FROM favorite_event")
    fun getFavoriteEvent(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM favorite_event WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent?>

    @Query("SELECT * FROM favorite_event WHERE id = :id LIMIT 1")
    suspend fun getFavoriteEventByIdSync(id: String): FavoriteEvent?

    @Query("DELETE FROM favorite_event WHERE id = :id")
    suspend fun deleteFavoriteEventById(id: String)

}