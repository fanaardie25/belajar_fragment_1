package com.example.belajarfragment1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.belajarfragment1.api.ApiHelper
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var input_email: TextInputEditText
    private lateinit var input_password: TextInputEditText
    private lateinit var btn_login: Button
    private lateinit var txt_click: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        input_email = findViewById<TextInputEditText>(R.id.input_email)
        btn_login = findViewById<Button>(R.id.btn_login)
        input_password = findViewById<TextInputEditText>(R.id.input_password)
        txt_click = findViewById<TextView>(R.id.text_click_register)

        txt_click.setOnClickListener {
            val intentDestination = Intent(this, RegisterActivity::class.java)
            startActivity(intentDestination)
            finish()
        }

        btn_login.setOnClickListener {
            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email & password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = JSONObject()
            body.put("email",email)
            body.put("password",password)

            ApiHelper.post("login",body){success,response ->
                runOnUiThread {
                    if (success && response != null){
                        setloginSuccess(response)
                    }else{
                        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



    }
    fun setloginSuccess(res: String){

        val dataJson = JSONObject(res)

        val token = dataJson.getString("token")
        Log.d("token","token = $token")
        getSharedPreferences("auth",MODE_PRIVATE)
            .edit()
            .putString("token",token)
            .apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}