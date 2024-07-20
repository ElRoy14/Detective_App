package com.appsmoviles.a911_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import com.appsmoviles.a911_app.EventActivity

class EventsAdapter(private var events: List<Event>, private var eContext: Context) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTV)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTV)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTV)
        val imageIv: ImageView = itemView.findViewById(R.id.imageIV)
        val viewButton: ImageView = itemView.findViewById(R.id.viewButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.dateTextView.text = event.date
        holder.imageIv.setImageURI(ImageController.getImageUri(eContext, position.toLong()))

        holder.viewButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, EventActivity::class.java).apply{
                putExtra("event_id", event.id)
            }
            holder.itemView.context.startActivity(intent)
        }

    }

    fun refreshData(newEvents: List<Event>){
        events = newEvents
        notifyDataSetChanged()
    }

}