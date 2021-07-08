package com.example.you_tube_alerting

import android.content.Context
import android.content.SharedPreferences
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.YouTubeScopes.YOUTUBE_READONLY
import com.google.api.services.youtube.model.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class YouTubeApi(
    private val context: Context,
    privite val preferences: SharedPreferences,
) {
    private val secretLocation = "secrets/my_secret.json"
    private val youtubeCred = context.resources.assets.open(secretLocation)
    private val key = "AIzaSyDFTv7j6h2U5Xns53dFek8vSLaeGzUJz3Y"

    var youTubeDataAPIEndpoint = YouTube
        .Builder(AndroidHttp.newCompatibleTransport(), AndroidJsonFactory(), null)
        .build()

    suspend fun getSubs() = withContext(Dispatchers.IO) { getSubsBlocking() }

    fun getSubsBlocking(): List<Subscription> = youTubeDataAPIEndpoint
        .subscriptions()
        .list("snippet")
        .setForChannelId("UC4bAHicwNwBKFwyZju8cPug")
        .setMyRecentSubscribers(true)
        .setKey(key)
        .execute()
        .items

    suspend fun authorize() = withContext(Dispatchers.IO) { authorizeInGoogle() }

    private fun authorizeInGoogle(): Credential {
        val credential =
            GoogleAccountCredential.usingOAuth2(context, listOf(YOUTUBE_READONLY))
        //val settings: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        credential.setSelectedAccountName(preferences.getString(PREF_ACCOUNT_NAME, null))
// YouTube client `
// YouTube client
        service = YouTube.Builder(transport, jsonFactory, credential)
            .setApplicationName("Google-YouTubeAndroidSample/1.0").build()


//        val clientSecrets =
//            GoogleClientSecrets.load(
//                JacksonFactory.getDefaultInstance(),
//                InputStreamReader(youtubeCred)
//            )
//
//        val flow = GoogleAuthorizationCodeFlow.Builder(
//            NetHttpTransport(),
//            JacksonFactory.getDefaultInstance(),
//            clientSecrets,
//            listOf(YOUTUBE_READONLY),
//        )
//            .setAccessType("offline")
//            .setApprovalPrompt("force")
//            .build()
//
//
//        val ab: AuthorizationCodeInstalledApp =
//            object : AuthorizationCodeInstalledApp(flow, LocalServerReceiver()) {
//                @Throws(IOException::class)
//                override fun onAuthorization(authorizationUrl: AuthorizationCodeRequestUrl) {
//                    val url = authorizationUrl.build()
//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    context.startActivity(browserIntent)
//                }
//            }
//        val r = Credential.Builder(BearerToken.formEncodedBodyAccessMethod()).setClientAuthentication()

//        val e = ab.authorize("user").setAccessToken("user")
//
//        return e


//        val e =""
//        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver.Builder()
//            .setPort(8080)
//            .build())
//            .authorize("user")


//        val scopes: MutableCollection<String> = ArrayList()
//        scopes.add(Scopes.PROFILE)
//        val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
//
//        credential.setSelectedAccountName(mFirebaseAuth.getCurrentUser().getEmail())
//
//        val userProfile = null
//
//        val service: PeopleService = Builder(http_transport, JSON_FACTORY, credential)
//            .setApplicationName(mainActivity.getResources().getString(R.string.app_name)).build()
//        val userProfile: Person = service.people().get("people/100889827937171513174")
//            .setRequestMaskIncludeField("person.biographies,person.birthdays,person.genders,person.phone_numbers")
//            .execute()
//
    }
}