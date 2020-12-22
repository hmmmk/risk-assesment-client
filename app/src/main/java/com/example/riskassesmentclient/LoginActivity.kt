package com.example.riskassesmentclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.riskassesmentclient.api.RiskAutomationApiService
import com.example.riskassesmentclient.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var riskAutomationApiService: RiskAutomationApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        riskAutomationApiService = RetrofitUtils.getInstance(applicationContext)
            .createService(RiskAutomationApiService::class.java)

        initViews()
        initListeners()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        signInBtn.setOnClickListener {
            signIn()
        }

        signUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signIn() {
        val login = loginEt.text.toString()
        val password = passwordEt.text.toString()

        launchUI {
            asyncR {
                riskAutomationApiService.login(login, password).bodyOrError()
            }.awaitFold({
                applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putString("token", it.token)
                    .commit()

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }, {
                Toast.makeText(this@LoginActivity, "User is not found", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun signUp() {
        val login = loginEt.text.toString()
        val password = passwordEt.text.toString()

        launchUI {
            asyncR {
                riskAutomationApiService.register(login, password).bodyOrError()
            }.awaitFold({
                applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putString("token", it.token)
                    .commit()

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }, {
                Toast.makeText(this@LoginActivity, "User is not found", Toast.LENGTH_SHORT).show()
            })
        }
    }
}