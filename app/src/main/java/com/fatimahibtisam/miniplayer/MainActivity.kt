package com.fatimahibtisam.miniplayer

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        var volumeLevel: Float = 0.5f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val volumeBar = findViewById<SeekBar>(R.id.volumeBar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, PlayerFragment())
            .commit()

        volumeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                volumeLevel = progress / 100f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

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