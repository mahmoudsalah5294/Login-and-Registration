package com.mahmoudsalah.loginandregisteration.data.model

/**
 * Data validation state of the login form.
 */
data class FormState(val usernameError: Int? = null,
                     val passwordError: Int? = null,
                     val fieldEmpty: Int? = null,
                     val isDataValid: Boolean = false)