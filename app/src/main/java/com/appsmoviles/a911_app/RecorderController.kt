package com.appsmoviles.a911_app

import android.app.Activity
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class RecorderController {

    private var grabacion: MediaRecorder?= null
    private var archivoSalida: String?= null


    @RequiresApi(Build.VERSION_CODES.S)
    fun AudioRercorder(context: Context, title: String, date: String) {

        if(grabacion == null){
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    0
                )
                //return
            }
            var sanitizedTitle = title.replace(" ".toRegex(), "_")
            var sanitizedDate = date.replace(" ".toRegex(), "_")
            sanitizedTitle = sanitizedTitle.replace("/".toRegex(), "_")
            sanitizedDate = sanitizedDate.replace("/".toRegex(), "_")

            archivoSalida = "${context.filesDir.absolutePath}/${sanitizedTitle}$sanitizedDate.mp3"
            Log.d("RecorderController", "Archivo de salida: $archivoSalida")
            Log.d("RecorderController", "Archivo de salida: $archivoSalida")
            Log.d("RecorderController", "Title: $title, Date: $date")
            grabacion = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(archivoSalida)
            }

            Toast.makeText(context, "entró al if 2", Toast.LENGTH_SHORT).show()
            try {
                grabacion!!.prepare()
                Toast.makeText(context, "Preparando para grabar", Toast.LENGTH_SHORT).show()
                grabacion!!.start()
                Toast.makeText(context, "Grabando...", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Log.e("RecorderController", "IO Exception: ${e.message}", e)
                Toast.makeText(context, "Ha ocurrido una excepción: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }else if(grabacion != null)
        {
            grabacion!!.stop()
            grabacion!!.release()
            grabacion!!.reset()
            Toast.makeText(context, "Grabación Finalizada", Toast.LENGTH_SHORT).show()
        }
    }

    fun AudioPlayer(context: Context, title: String, date: String){
        var mediaPlayer = MediaPlayer()
        try{
            var sanitizedTitle = title.replace(" ".toRegex(), "_")
            var sanitizedDate = date.replace(" ".toRegex(), "_")
            sanitizedTitle = sanitizedTitle.replace("/".toRegex(), "_")
            sanitizedDate = sanitizedDate.replace("/".toRegex(), "_")

            var audioFile = "${context.filesDir.absolutePath}/${sanitizedTitle}$sanitizedDate.mp3"
            Log.d("RecorderController", "Archivo de salida: $audioFile")
            Log.d("RecorderController", "Title: $title, Date: $date")
            mediaPlayer.setDataSource(audioFile)
            mediaPlayer.prepare()
        }catch(e: IOException){
            Log.e("RecorderController", "IO Exception: ${e.message}", e)
        }
        mediaPlayer.start()
        Toast.makeText(context, "Reproduciendo Audio", Toast.LENGTH_SHORT).show()
    }

}