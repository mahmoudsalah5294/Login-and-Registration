package com.mahmoudsalah.loginandregisteration.data.model

import com.mahmoudsalah.loginandregisteration.data.model.Result

data class ResponseModel(
    val result: Result,
    val status: String
)