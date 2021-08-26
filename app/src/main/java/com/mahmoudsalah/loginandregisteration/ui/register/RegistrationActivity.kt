package com.mahmoudsalah.loginandregisteration.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.loginandregisteration.data.model.RegisterModel
import com.mahmoudsalah.loginandregisteration.databinding.ActivityRegistrationBinding
import com.mahmoudsalah.loginandregisteration.ui.login.LoginActivity

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var model: RegisterModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registrationViewModel = ViewModelProvider(
            this,
            RegistrationViewModelFactory()
        ).get(RegistrationViewModel::class.java)

        binding.joinBtn.setOnClickListener {
        val phone = binding.ccp.fullNumberWithPlus+binding.phone
            model = RegisterModel(
                "CustomerSignUp",
                binding.fName.text.toString(),
                binding.lName.text.toString(),
                binding.emailAddress.text.toString(),
                phone,
                binding.passwordTxt.text.toString(),
                binding.referralCode.text.toString()
            )


            registrationViewModel.registrationDataChanged(model,binding.cPassword.text.toString())
        }

        registrationViewModel.registerFormState.observe(this@RegistrationActivity, Observer {
            val registrationState = it ?: return@Observer
            if (registrationState.usernameError != null) {
                binding.emailAddress.error = getString(registrationState.usernameError)
            }
            if (registrationState.passwordError != null) {
                binding.passwordTxt.error = getString(registrationState.passwordError)
            }
            if (registrationState.fieldEmpty != null){
                Toast.makeText(this, getString(registrationState.fieldEmpty), Toast.LENGTH_LONG).show()

            }
        })

        registrationViewModel.registerData.observe(this@RegistrationActivity, Observer {
            val registerResult = it ?: return@Observer

            if (registerResult.result.success == 0){
                Toast.makeText(this, "${registerResult.result.message}", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this@RegistrationActivity,LoginActivity::class.java)
                intent.putExtra("email",model.username)
                intent.putExtra("password",model.password)
                startActivity(intent)
                finish()
            }

        })


    }
}