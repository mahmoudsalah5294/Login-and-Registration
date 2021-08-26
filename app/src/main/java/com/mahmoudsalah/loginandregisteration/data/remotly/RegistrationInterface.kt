package com.mahmoudsalah.loginandregisteration.data.remotly

import com.mahmoudsalah.loginandregisteration.data.model.RegisterModel
import com.mahmoudsalah.loginandregisteration.data.model.UserModel
import com.mahmoudsalah.loginandregisteration.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface RegistrationInterface {

@POST
suspend fun login(@Url url: String,@Body user:UserModel): Response<ResponseModel>

@POST
suspend fun registration(@Url url: String,@Body register:RegisterModel): Response<ResponseModel>
}