package com.muhammadnabil.navigationdanapi.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadnabil.navigationdanapi.repository.FavoriteEventRepository

class FavoriteEventViewModelFactory(private val repository: FavoriteEventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteEventViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                FavoriteEventViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
