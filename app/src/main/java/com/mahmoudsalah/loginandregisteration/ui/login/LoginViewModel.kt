package com.mahmoudsalah.loginandregisteration.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.loginandregisteration.data.Repository

import com.mahmoudsalah.loginandregisteration.R
import com.mahmoudsalah.loginandregisteration.data.model.FormState
import com.mahmoudsalah.loginandregisteration.data.model.ResponseModel
import com.mahmoudsalah.loginandregisteration.data.model.UserModel
import com.mahmoudsalah.loginandregisteration.util.LOGIN_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repo: Repository) : ViewModel() {

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val _loginForm = MutableLiveData<FormState>()
    val formState: LiveData<FormState> = _loginForm

     private val mutableLoginResult = MutableLiveData<ResponseModel>()
     val loginResultLive: LiveData<ResponseModel> = mutableLoginResult


    fun loginWithAPI(loginModel: UserModel, url:String){
        viewModelScope.launch {
            val result = repo.loginWithAPI(loginModel, url)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    mutableLoginResult.postValue(result.body())
                }else{
                    Log.i("ViewModel","Mah Error")
                }
            }
        }
    }

    fun loginDataChanged(loginModel: UserModel) {
        if (!isUserNameValid(loginModel.username)) {
            _loginForm.value = FormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(loginModel.password)) {
            _loginForm.value = FormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = FormState(isDataValid = true)
            loginWithAPI(loginModel, LOGIN_URL)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.matches(emailPattern.toRegex())
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}