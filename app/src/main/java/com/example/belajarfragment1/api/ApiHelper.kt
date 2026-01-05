package com.example.belajarfragment1.api

import android.util.JsonToken
import android.util.Log
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiHelper {

    private const val BASE_URL = "http://192.168.1.4:8000/api/"

    fun post(
        endpoint: String,
        body: JSONObject,
        callback: (Boolean, String?) -> Unit
    ) {
        Thread {
            try {
                val url = URL(BASE_URL + endpoint)
                val conn = url.openConnection() as HttpURLConnection

                conn.requestMethod = "POST"
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val jsonBody = body.toString()
                Log.d("API", "POST $endpoint")
                Log.d("API", "BODY: $jsonBody")

                conn.outputStream.use {
                    it.write(jsonBody.toByteArray())
                }

                val code = conn.responseCode
                val stream = if (code in 200..299)
                    conn.inputStream else conn.errorStream

                val response = stream.bufferedReader().readText()
                Log.d("API", "CODE: $code")
                Log.d("API", "RESPONSE: $response")

                callback(code in 200..299, response)

            } catch (e: Exception) {
                Log.e("API", e.message ?: "unknown error")
                callback(false, null)
            }
        }.start()
    }

    fun get(endpoint: String,token: String?,callback: (Boolean, String?) -> Unit){
        Thread {
            try {
                val url = URL(BASE_URL + endpoint)
                val conn = url.openConnection() as HttpURLConnection

                conn.requestMethod = "GET"
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("Content-Type", "application/json")
                if (!token.isNullOrBlank()) {
                    conn.setRequestProperty("Authorization", "Bearer $token")
                }
                conn.doOutput = false
                conn.connectTimeout = 15000
                conn.readTimeout = 15000


                val code = conn.responseCode
                val stream = if (code in 200..299)
                    conn.inputStream else conn.errorStream

                val response = stream.bufferedReader().readText()
                Log.d("API", "CODE: $code")
                Log.d("API", "RESPONSE: $response")

                callback(code in 200..299, response)

            } catch (e: Exception) {
                Log.e("API", e.message ?: "unknown error")
                callback(false, null)
            }
        }.start()
    }
}

