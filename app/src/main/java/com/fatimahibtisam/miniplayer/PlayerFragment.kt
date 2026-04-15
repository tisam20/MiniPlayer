package com.fatimahibtisam.miniplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekBar = view.findViewById(R.id.seekBar)

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.lofimusic)

        view.findViewById<Button>(R.id.btnPlay).setOnClickListener {
            mediaPlayer?.start()
            updateSeekBar()
        }

        view.findViewById<Button>(R.id.btnPause).setOnClickListener {
            mediaPlayer?.pause()
        }

        view.findViewById<Button>(R.id.btnStop).setOnClickListener {
            mediaPlayer?.seekTo(0)
            mediaPlayer?.pause()
        }
    }

    private fun updateSeekBar() {
        handler.postDelayed({
            mediaPlayer?.let {
                if (it.isPlaying) {
                    seekBar.progress = it.currentPosition
                    updateSeekBar()
                }
            }
        }, 500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
    }
}