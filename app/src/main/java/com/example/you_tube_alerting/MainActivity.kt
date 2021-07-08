package com.example.you_tube_alerting

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.youtube.YouTubeScopes.YOUTUBE_READONLY
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URL

//AIzaSyClBPKCe-0wlCEYB8J5giIB7FlNfLwIWII

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val r = applicationContext
//            .resources
//            .assets
//            .open("my_secret.json")
//            .bufferedReader()
//            .use { it.readText() }
        runBlocking {
            launch {
                val t = YouTubeApi(applicationContext)
                val cred = t.getSubs()
                val r =""
            }
        }
        getPreferences()
//        val t = YouTubeApi(applicationContext)
//        val cred = t.authorize()
//        val r =""

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


        val zadeUrl = "https://www.youtube.com/channel/UC4bAHicwNwBKFwyZju8cPug"
        val rowContent = URL(zadeUrl).readText()

        return rowContent
    }
}
