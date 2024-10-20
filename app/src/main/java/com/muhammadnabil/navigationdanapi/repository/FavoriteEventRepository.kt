package com.muhammadnabil.navigationdanapi.repository

import androidx.lifecycle.LiveData
import com.muhammadnabil.navigationdanapi.data.FavoriteEvent
import com.muhammadnabil.navigationdanapi.data.FavoriteEventDao

class FavoriteEventRepository ( private var favoriteEventDao: FavoriteEventDao) {
    suspend fun addFavoriteEvent( favoriteEvent: FavoriteEvent){
        favoriteEventDao.insertFavoriteEvent(favoriteEvent)
    }

    fun getFavoriteEvent(): LiveData<List<FavoriteEvent>>{
        return favoriteEventDao.getFavoriteEvent()
    }
}
