package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realtimedatabasekotlin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()
            when (userName + " " + password) {
                "user password1" -> {
                    val intent = Intent(this, UserViewCategory::class.java)
                    val value: String = userName + " " + password
                    intent.putExtra("access", value)
                    startActivity(intent)
                    finish()
                }
                "admin password2" -> {
                    val intent = Intent(this, AdminViewCategory::class.java)
                    val value: String = userName + " " + password
                    intent.putExtra("access", value)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                    binding.userName.text = null
                    binding.password.text = null
                }
            }
        }
    }
}