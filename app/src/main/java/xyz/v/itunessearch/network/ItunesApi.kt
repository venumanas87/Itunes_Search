package xyz.v.itunessearch.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.v.itunessearch.objects.Search

interface ItunesApi {
    @GET("search")
    fun getResults(@Query("term") term:String):Call<Search>
}