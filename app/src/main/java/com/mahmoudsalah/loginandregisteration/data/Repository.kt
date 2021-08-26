package com.mahmoudsalah.loginandregisteration.data

import com.mahmoudsalah.loginandregisteration.data.model.RegisterModel
import com.mahmoudsalah.loginandregisteration.data.model.UserModel
import com.mahmoudsalah.loginandregisteration.data.model.ResponseModel
import retrofit2.Response



class Repository(val dataSource: DataSource) {


    suspend fun loginWithAPI(loginModel: UserModel, url:String): Response<ResponseModel> {
        return dataSource.loginWithAPI(loginModel,url)
    }

    suspend fun signUpWithAPI(model: RegisterModel, url:String): Response<ResponseModel> {
        return dataSource.signUpWithAPI(model,url)
    }

}