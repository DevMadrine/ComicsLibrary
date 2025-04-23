package com.example.comicslibrary.model.api

import retrofit2.Retrofit

object ApiService {
    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"
    private fun getRetrofit():Retrofit{
        val ts = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_KEY
        val apiKey = BuildConfig.MARVEL_SECRET
        val hash = getHash(ts, apiSecret,apiKey)

    }
}