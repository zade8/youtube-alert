package com.example.you_tube_alerting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes.YOUTUBE_READONLY
import com.google.api.services.youtubeAnalytics.YouTubeAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStreamReader


class YouTubeApi(
    private val context: Context,
    private val activity: Activity,

    ) {
    private val secretLocation = "secrets/client_secret.json"
    private val youtubeCred = context.resources.assets.open(secretLocation)



    suspend fun authorize() = withContext(Dispatchers.IO) { authorizeInGoogle() }

    private fun authorizeInGoogle(): Credential {
        val clientSecrets =
            GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(),
                InputStreamReader(youtubeCred)
            )

        val flow = GoogleAuthorizationCodeFlow.Builder(
            NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            clientSecrets,
            listOf(YOUTUBE_READONLY, "https://www.googleapis.com/auth/yt-analytics.readonly"),
        )
            .setAccessType("offline")
            .setApprovalPrompt("force")
            .build()

        val ab: AuthorizationCodeInstalledApp =
            object : AuthorizationCodeInstalledApp(flow, LocalServerReceiver()) {
                @Throws(IOException::class)
                override fun onAuthorization(authorizationUrl: AuthorizationCodeRequestUrl) {
                    val url = authorizationUrl.build()
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    activity.startActivity(browserIntent)
                }
            }

        return ab.authorize("user").setAccessToken("user")
    }

    suspend fun dooo() = withContext(Dispatchers.IO) { doo() }

    fun doo() {
        val httpTransport = NetHttpTransport()
        val credential = authorizeInGoogle()
        val youtubeService = YouTube.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential)
            .setApplicationName(context.appName)
            .build()

        val request = youtubeService.subscriptions()
            .list("snippet")
        val response = request.setMyRecentSubscribers(true).execute()//.items[0].snippet.channelId
        println(response)

        val analytics = YouTubeAnalytics.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential)
            .setApplicationName(context.appName)
            .build()

        val e = analytics.reports().query()

    }

//    fun authorizeWithin(activity: Activity) =
}

val Context.appName
    get() = applicationInfo.loadLabel(packageManager).toString()

