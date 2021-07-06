package com.example.you_tube_alerting

import android.accounts.Account
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.youtube.YouTubeScopes.YOUTUBE_READONLY
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(applicationContext, "view is created", Toast.LENGTH_SHORT).show();
        getNumberOfSubs()

        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener {
            Toast.makeText(applicationContext, "start is clicked", Toast.LENGTH_SHORT).show();
            changeSubNumberAs("666")
        }

        val stopButton = findViewById<Button>(R.id.stop)
        stopButton.setOnClickListener {
            Toast.makeText(applicationContext, "stop is clicked", Toast.LENGTH_SHORT).show();
        }


        val textView = findViewById<TextView>(R.id.sub_number)
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.youtube.com/channel/UC4bAHicwNwBKFwyZju8cPug"
        //https://www.youtube.com/c/ZADEx/featured

        val stringRequest = StringRequest(
            GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                textView.text = "Response is: ${response.substring(0, 10)}"
                println(response.toString())
            },
            { textView.text = "That didn't work!" })

        queue.add(stringRequest)
    }

    private fun changeSubNumberAs(newNumber: String) {
        findViewById<TextView>(R.id.sub_number).text = newNumber.toString()
    }

    private fun getNumberOfSubs(): String {
        val youTubeCreds = GoogleAccountCredential.usingOAuth2(
            applicationContext,
            listOf(YOUTUBE_READONLY),
        )

        val e = forChannelId()

        val zadeUrl = "https://www.youtube.com/channel/UC4bAHicwNwBKFwyZju8cPug"
        val rowContent = URL(zadeUrl).readText()

        return rowContent
    }
}
