package com.appsmoviles.a911_app

import android.app.Activity
import android.content.Intent
import android.media.AudioRecord
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appsmoviles.a911_app.databinding.ActivityAddEventBinding
import com.appsmoviles.a911_app.databinding.ActivityMainBinding

class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
    private lateinit var db: EventsDatabaseHelper

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null
    private lateinit var recordController: RecorderController

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EventsDatabaseHelper(this)

        binding.recordButton.setOnClickListener {
            recordController = RecorderController()
            val title = binding.eventTitle.text.toString()
            val date = binding.eventDate.text.toString()
            recordController.AudioRercorder(this@AddEventActivity, title, date)
        }

        binding.saveButton.setOnClickListener{
            val title = binding.eventTitle.text.toString()
            val description = binding.eventDescription.text.toString()
            val date = binding.eventDate.text.toString()
            val event = Event(0, title, description, date)
            val id = db.insertEvent(event)
            imageUri?.let{
                ImageController.saveImage(this@AddEventActivity, id, it)
            }
            finish()
            Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show()
        }


        binding.eventImage.setOnClickListener{
            ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                binding.eventImage.setImageURI(imageUri)
            }
        }
    }

}