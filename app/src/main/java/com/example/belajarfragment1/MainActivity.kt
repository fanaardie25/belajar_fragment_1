package com.example.belajarfragment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.belajarfragment1.ui.ExploreFragment
import com.example.belajarfragment1.ui.JobFragment
import com.example.belajarfragment1.ui.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val pref = getSharedPreferences("auth",MODE_PRIVATE)
        val token = pref.getString("token","not-found")

        if (token == "not-found"){
            val intentDestination = Intent(this, LoginActivity::class.java)
            startActivity(intentDestination)
            finish()
        }

        if (savedInstanceState == null) {
            addFragment(ExploreFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.explore_menu -> {
                    addFragment(ExploreFragment())
                    true
                }
                R.id.myjob_menu -> {
                    addFragment(JobFragment())
                    true
                }
                R.id.Profile_menu -> {
                    addFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

    }



    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content,fragment,fragment.javaClass.simpleName)
            .commit()
    }
}