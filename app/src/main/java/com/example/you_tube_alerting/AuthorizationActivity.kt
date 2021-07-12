package com.example.you_tube_alerting

import android.app.Activity
import android.os.Bundle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthorizationActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking { launch {
            val r = YouTubeApi(baseContext, this@AuthorizationActivity).dooo()
            val e = ""
        } }

        finish()
    }
}