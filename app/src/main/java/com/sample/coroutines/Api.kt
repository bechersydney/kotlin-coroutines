package com.sample.coroutines

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("/comments")
    suspend  fun getComments(): Response<List<Comment>>

}