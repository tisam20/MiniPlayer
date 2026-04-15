package com.fatimahibtisam.miniplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, PlayerFragment())
            .commit()

        bottomNav.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.menu_player -> PlayerFragment()
                R.id.menu_sound -> SoundFragment()
                R.id.menu_recorder -> RecorderFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, it)
                    .commit()
            }
            true
        }
    }
}