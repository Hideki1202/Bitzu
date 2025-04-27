package com.example.bitzu.services
import com.example.bitzu.models.Tag
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface TagService {
    @GET("/tag")
    fun getTags(@Header("Authorization") token: String): Call<List<Tag>>

}