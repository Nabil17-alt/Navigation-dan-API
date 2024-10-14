package com.muhammadnabil.navigationdanapi.data.retrofit

import com.muhammadnabil.navigationdanapi.data.response.BaseResponse
import com.muhammadnabil.navigationdanapi.data.response.DetailEventResponse
import com.muhammadnabil.navigationdanapi.data.response.ListEventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<ListEventsResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String): Call<BaseResponse>

    @GET("events")
    fun getEventLimits(
        @Query("active") active: Int = -1,
        @Query("limit") limit: Int = 1
    ): Call<ListEventsResponse>
}
