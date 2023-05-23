package com.example.travelmemories.networkconnection

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWheatherServiceApi {

    companion object{
        const val API_KEY = "a807412a55b0f24bdec6c94c5f5cc4da"
    }

    //https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid={API key}
    @GET("onecall")
    suspend fun getCurrentWeather(@Query("lat") lat:Double, @Query("lon") lon:Double, @Query("appid") appid:String ): Weather
    //suspend fun getCurrentWeather(@Query("lat") lat:Double, @Query("lon") lon:Double, @Query("appid") appid:String ): Call<Weather>
}