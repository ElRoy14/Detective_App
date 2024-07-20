package com.appsmoviles.a911_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.a911_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: EventsDatabaseHelper
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EventsDatabaseHelper(this)
        eventsAdapter = EventsAdapter(db.getAllEvents(), this)

        binding.eventRv.layoutManager = LinearLayoutManager(this)
        binding.eventRv.adapter = eventsAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        eventsAdapter.refreshData(db.getAllEvents())
    }
}