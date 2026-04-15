package com.fatimahibtisam.miniplayer

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class SoundFragment : Fragment(R.layout.fragment_sound) {

    private lateinit var soundPool: SoundPool
    private var shoot = 0
    private var boom = 0
    private var coin = 0
    private var isLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val audioAttr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttr)
            .build()

        // pakai file audio kamu
        shoot = soundPool.load(requireContext(), R.raw.chillsound, 1)
        boom = soundPool.load(requireContext(), R.raw.lofimusic, 1)
        coin = soundPool.load(requireContext(), R.raw.relaxingguitar, 1)

        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) isLoaded = true
        }

        view.findViewById<Button>(R.id.btnShoot).setOnClickListener {
            if (isLoaded) soundPool.play(shoot, 1f, 1f, 1, 0, 1f)
        }

        view.findViewById<Button>(R.id.btnBoom).setOnClickListener {
            if (isLoaded) soundPool.play(boom, 1f, 1f, 1, 0, 1f)
        }

        view.findViewById<Button>(R.id.btnCoin).setOnClickListener {
            if (isLoaded) soundPool.play(coin, 1f, 1f, 1, 0, 1f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        soundPool.release()
    }
}