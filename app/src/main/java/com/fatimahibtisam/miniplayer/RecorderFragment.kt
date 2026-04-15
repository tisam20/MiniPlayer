package com.fatimahibtisam.miniplayer

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class RecorderFragment : Fragment(R.layout.fragment_recorder) {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var filePath: String = ""
    private var isRecording = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filePath = "${requireContext().externalCacheDir?.absolutePath}/audio.mp4"

        val btnRecord = view.findViewById<Button>(R.id.btnRecord)
        val btnPlay = view.findViewById<Button>(R.id.btnPlay)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                101
            )
        }

        btnRecord.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(requireContext(), "Izin mic belum diberikan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isRecording) {
                startRecording()
                btnRecord.text = "Stop Rekam"
            } else {
                stopRecording()
                btnRecord.text = "Mulai Rekam"
            }
        }

        btnPlay.setOnClickListener {
            playAudio()
        }
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
            start()
        }
        isRecording = true
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        isRecording = false
    }

    private fun playAudio() {
        player?.release()
        player = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recorder?.release()
        player?.release()
    }
}