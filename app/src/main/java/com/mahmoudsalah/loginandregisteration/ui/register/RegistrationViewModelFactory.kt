package com.mahmoudsalah.loginandregisteration.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.loginandregisteration.data.DataSource
import com.mahmoudsalah.loginandregisteration.data.Repository

class RegistrationViewModelFactory:ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(
                repo = Repository(
                    dataSource = DataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}