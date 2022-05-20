package com.example.catapps.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import androidx.viewpager2.widget.ViewPager2
import com.example.catapps.model.ImageResultData

interface ApiService {
    @GET("images/search")
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("size") format: String
    ): Call<List<ImageResultData>>
}