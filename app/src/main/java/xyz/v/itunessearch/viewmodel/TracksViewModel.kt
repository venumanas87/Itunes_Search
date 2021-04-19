package xyz.v.itunessearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.v.itunessearch.network.ItunesApi
import xyz.v.itunessearch.objects.Search

class TracksViewModel:ViewModel() {
    val str:MutableLiveData<Search> = MutableLiveData()

    fun fetchResult(search:String):MutableLiveData<Search>{
        val retrofit =  Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ItunesApi::class.java)
        val call = service.getResults(search)
        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful){
                   // val mainObj = JSONObject(Gson().toJson(response.body()))
                    println("Executing It...")
                    str.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })

   return str }

}