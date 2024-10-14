package com.muhammadnabil.navigationdanapi.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.muhammadnabil.navigationdanapi.databinding.ActivityDetailEventBinding
import com.google.android.material.snackbar.Snackbar
import com.muhammadnabil.navigationdanapi.R
import android.os.Handler
import android.os.Looper

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailEventViewModel by viewModels()

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)

        if (eventId == -1) {
            Log.e("DetailEventActivity", "Invalid Event ID")
            return
        }
        Log.d("DetailEventActivity", "Event ID: $eventId")

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getDetailEvent(eventId)

        viewModel.event.observe(this) { event ->
            Handler(Looper.getMainLooper()).postDelayed({
                binding.progressBar.visibility = View.GONE
                event?.let { eventData ->
                    binding.tvEventName.text = eventData.name
                    binding.tvOwnerName.text = eventData.ownerName
                    binding.tvEventDate.text = eventData.date
                    binding.tvEventLocation.text = eventData.location
                    binding.tvDescription.text = HtmlCompat.fromHtml(
                        eventData.description ?: "",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    Glide.with(this)
                        .load(eventData.mediaCover)
                        .into(binding.imgCover)

                    binding.btnLink.setOnClickListener {
                        val url = eventData.link ?: ""
                        if (url.isNotEmpty()) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        } else {
                            Log.e("DetailEventActivity", "Link is empty")
                        }
                    }
                } ?: run {
                    Log.e("DetailEventActivity", "Event data is null")
                    showError("Event details not found")
                }
            }, 2000) // Simulasi loading selama 2 detik
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
