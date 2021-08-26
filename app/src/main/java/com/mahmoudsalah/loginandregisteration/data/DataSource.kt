package com.mahmoudsalah.loginandregisteration.data


import com.mahmoudsalah.loginandregisteration.data.model.RegisterModel
import com.mahmoudsalah.loginandregisteration.data.model.UserModel
import com.mahmoudsalah.loginandregisteration.data.remotly.RegistrationAPI
import com.mahmoudsalah.loginandregisteration.data.model.ResponseModel
import retrofit2.Response

class DataSource {


    suspend fun loginWithAPI(loginModel:UserModel,url:String): Response<ResponseModel> {
       return RegistrationAPI.login().login(url,loginModel)
    }

    suspend fun signUpWithAPI(model:RegisterModel,url:String): Response<ResponseModel> {
        return RegistrationAPI.signUp().registration(url,model)
    }

}