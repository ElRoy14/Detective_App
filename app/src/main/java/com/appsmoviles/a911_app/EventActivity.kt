package com.appsmoviles.a911_app

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appsmoviles.a911_app.databinding.ActivityEventBinding


class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    private lateinit var db: EventsDatabaseHelper
    private var eventId: Int = -1
    private lateinit var recordController: RecorderController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EventsDatabaseHelper(this)

        eventId = intent.getIntExtra("event_id", -1)
        if(eventId == -1){
            finish()
            return
        }

        val event = db.getEventById(eventId)
        binding.eventViewTitle.text = event.title
        binding.eventViewDescription.text = event.description
        binding.eventViewDate.text = event.date
        binding.eventViewImage.setImageURI(ImageController.getImageUri(this, event.id.toLong()))
        binding.playButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@EventActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1000)
            }

            recordController = RecorderController()
            val title = binding.eventViewTitle.text.toString()
            val date = binding.eventViewDate.text.toString()
            recordController.AudioPlayer(this@EventActivity, title, date)
        }
        binding.returnButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply{
            }
            this.startActivity(intent)
        }

    }
}