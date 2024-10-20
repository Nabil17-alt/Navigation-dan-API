package com.muhammadnabil.navigationdanapi.ui.detailevent

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.muhammadnabil.navigationdanapi.data.FavoriteEvent
import com.muhammadnabil.navigationdanapi.data.FavoriteEventDao
import com.muhammadnabil.navigationdanapi.data.FavoriteEventDatabase
import com.muhammadnabil.navigationdanapi.data.response.BaseResponse
import com.muhammadnabil.navigationdanapi.data.response.DetailEventResponse
import com.muhammadnabil.navigationdanapi.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel (application: Application) : AndroidViewModel(application) {

    private val favoriteEventDao: FavoriteEventDao =
        FavoriteEventDatabase.getDatabase(application).favoriteEventDao()

    private val _event = MutableLiveData<DetailEventResponse?>()
    val event: LiveData<DetailEventResponse?> = _event

    fun isFavorite(eventId: String): LiveData<FavoriteEvent?> {
        return favoriteEventDao.getFavoriteEventById(eventId)
    }

    fun toggleFavorite(event: FavoriteEvent) {
        viewModelScope.launch {

            val existingEvent = favoriteEventDao.getFavoriteEventByIdSync(event.id)

            if (existingEvent == null) {

                favoriteEventDao.insertFavoriteEvent(event)
            } else {

                favoriteEventDao.deleteFavoriteEventById(event.id)
            }
        }
    }


    fun getDetailEvent(id: Int) {
        Log.d("DetailEventViewModel", "Fetching details for ID: $id")

        val idString = id.toString()
        val client = ApiConfig.getApiService().getDetailEvent(idString)

        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.isSuccessful) {
                    _event.value = response.body()?.event
                    Log.d("DetailEventViewModel", "Data received: ${response.body()}")
                } else {
                    _event.value = null
                    Log.e("DetailEventViewModel", "Error response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _event.value = null
                Log.e("DetailEventViewModel", "Failure: ${t.message}")
            }
        })
    }
}
