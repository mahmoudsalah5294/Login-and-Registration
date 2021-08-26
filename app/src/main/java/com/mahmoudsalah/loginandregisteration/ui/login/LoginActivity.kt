package com.mahmoudsalah.loginandregisteration.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

import com.mahmoudsalah.loginandregisteration.R
import com.mahmoudsalah.loginandregisteration.data.model.UserModel
import com.mahmoudsalah.loginandregisteration.ui.register.RegistrationActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userModel: UserModel
    private lateinit var deviceID:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val facebook = findViewById<Button>(R.id.facebook)
        val google = findViewById<Button>(R.id.google)
        val forgetPassword = findViewById<TextView>(R.id.forgetText)
        val register = findViewById<TextView>(R.id.signUpTxt)


        username.setText(intent?.getStringExtra("email"))
        password.setText(intent?.getStringExtra("password"))

        deviceID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)


        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.formState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


        register.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }

        forgetPassword.setOnClickListener {
            Toast.makeText(this,"Password reset",Toast.LENGTH_SHORT).show()
        }

        facebook.setOnClickListener {
            Toast.makeText(this,"facebook sign",Toast.LENGTH_SHORT).show()
        }

        google.setOnClickListener {
            Toast.makeText(this,"google sign",Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loginResultLive.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

//            loading.visibility = View.GONE
            if (loginResult.result.success == 0){
                Toast.makeText(this,"Login Failed ${loginResult.result.message}",Toast.LENGTH_LONG).show()
            }
            if (loginResult.result.success != 0){
                Toast.makeText(this,"${loginResult.result.message}",Toast.LENGTH_LONG).show()
            }
        })



            login.setOnClickListener {
//                loading.visibility = View.VISIBLE
                userModel = UserModel(username.text.toString(),password.text.toString(),deviceID,"ANDROID")

                loginViewModel.loginDataChanged(
                    userModel)
            }
        }
    }
