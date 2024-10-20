package com.muhammadnabil.navigationdanapi.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadnabil.navigationdanapi.data.FavoriteEvent
import com.muhammadnabil.navigationdanapi.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventViewModel (private var repository: FavoriteEventRepository): ViewModel(){
    fun getFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        return repository.getFavoriteEvent()
    }

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            repository.addFavoriteEvent(favoriteEvent)
        }

    }
}

