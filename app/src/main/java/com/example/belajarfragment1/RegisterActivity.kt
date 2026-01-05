package com.example.belajarfragment1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.belajarfragment1.api.ApiHelper
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var input_name: TextInputEditText
    private lateinit var input_email: TextInputEditText
    private lateinit var input_password: TextInputEditText
    private lateinit var input_password_confirmation: TextInputEditText

    private lateinit var input_phone_number: TextInputEditText
    private lateinit var btn_register: Button
    private lateinit var txt_click: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        input_name = findViewById<TextInputEditText>(R.id.input_name)
        input_email = findViewById<TextInputEditText>(R.id.input_email)
        input_password = findViewById<TextInputEditText>(R.id.input_password)
        input_password_confirmation = findViewById<TextInputEditText>(R.id.input_password_confirmation)
        input_phone_number = findViewById<TextInputEditText>(R.id.input_phone_number)
        btn_register = findViewById<Button>(R.id.btn_register)
        txt_click = findViewById<TextView>(R.id.text_click_login)

        btn_register.setOnClickListener {

            val name = input_name.text.toString().trim()
            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()
            val passwordConf = input_password_confirmation.text.toString().trim()
            val phoneNumber = input_phone_number.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConf.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != passwordConf){
                Toast.makeText(this, "password confirmation harus sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = JSONObject()
            body.put("name",name)
            body.put("email",email)
            body.put("password",password)
            body.put("no_telp",phoneNumber)

            ApiHelper.post("register",body){status,response ->
                runOnUiThread {
                    if (status && response != null){
                        setRegisterSuccess(response)
                    }else{
                        Toast.makeText(this, "Register gaga;l", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun setRegisterSuccess(res: String){

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