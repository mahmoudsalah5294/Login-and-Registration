package com.mahmoudsalah.loginandregisteration.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.loginandregisteration.R
import com.mahmoudsalah.loginandregisteration.data.Repository
import com.mahmoudsalah.loginandregisteration.data.model.RegisterModel
import com.mahmoudsalah.loginandregisteration.data.model.ResponseModel
import com.mahmoudsalah.loginandregisteration.data.model.FormState
import com.mahmoudsalah.loginandregisteration.util.Registration_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(private val repo: Repository):ViewModel() {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private val mutableRegisterData = MutableLiveData<ResponseModel>()
    val registerData : LiveData<ResponseModel> = mutableRegisterData


    private val _registerForm = MutableLiveData<FormState>()
    val registerFormState: LiveData<FormState> = _registerForm

    fun signUp(model:RegisterModel,url:String){
        viewModelScope.launch {
            val result = repo.signUpWithAPI(model,url)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    mutableRegisterData.postValue(result.body())
                }else{
                    Log.i("ViewModel","Mah Error")
                }
            }
        }
    }

    fun registrationDataChanged(registerModel: RegisterModel,cPassword:String) {
        if (isNotEmpty(registerModel)) {
            val message = isPasswordValid(registerModel.password, cPassword)
            if (!isUserNameValid(registerModel.username)) {
                _registerForm.value = FormState(usernameError = R.string.invalid_username)
            } else if (message == "less than 5") {
                _registerForm.value = FormState(passwordError = R.string.invalid_password)
            } else if (message == "password must be same") {
                _registerForm.value = FormState(passwordError = R.string.password_not_same)
            } else {
                _registerForm.value = FormState(isDataValid = true)
                signUp(registerModel, Registration_URL)
            }
        }else{
            _registerForm.value = FormState(fieldEmpty = R.string.field_empty)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.matches(emailPattern.toRegex())
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String, cPassword:String): String {
        if (password == cPassword){
            if (password.length > 5) return "good"
            else return "less than 5"
        }else return "password must be same"

    }

    private fun isNotEmpty(registerModel: RegisterModel):Boolean{
        return !(registerModel.first_name.isNullOrEmpty() && registerModel.last_name.isNullOrEmpty()
                && registerModel.phone_number.isNullOrEmpty())
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}