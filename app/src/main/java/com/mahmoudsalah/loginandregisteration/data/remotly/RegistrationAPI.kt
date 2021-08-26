package com.mahmoudsalah.loginandregisteration.data.remotly


import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegistrationAPI {

    private const val URL = "https://store.zeew.eu/v1/"

    fun login():RegistrationInterface{
        return Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistrationInterface::class.java)
    }

    fun signUp():RegistrationInterface{
        return Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistrationInterface::class.java)
    }
}