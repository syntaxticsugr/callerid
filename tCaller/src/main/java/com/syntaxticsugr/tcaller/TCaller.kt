package com.syntaxticsugr.tcaller

import android.content.Context
import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.postbody.postBodyRequestOtp
import com.syntaxticsugr.tcaller.postbody.postBodyVerifyOtp
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import com.syntaxticsugr.tcaller.utils.stringToJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class TCallerApiClient {

    private val tCallerAppVersion = VersionDataModel(
        buildVersion = 8,
        majorVersion = 13,
        minorVersion = 56
    )

    private val clientsecret = "lvc22mp3l1sfv6ujg83rd17btt"

    val userAgent = "Truecaller/${tCallerAppVersion.majorVersion}.${tCallerAppVersion.minorVersion}.${tCallerAppVersion.buildVersion} (Android;${getAndroidVersion()})"


    @OptIn(ExperimentalSerializationApi::class)
    private fun createClient() = HttpClient(OkHttp) {
//        expectSuccess = true

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                encodeDefaults = true
            })
        }

        install(ContentEncoding) {
            gzip(1.0F)
        }
    }

    private fun HttpRequestBuilder.tCallerClient() {
        contentType(ContentType.Application.Json)
        headers {
            append("accept-encoding", "gzip")
            append("user-agent", userAgent)
            append("clientsecret", clientsecret)
        }
    }

    private var httpClient = createClient()

    suspend fun requestOtp(context: Context, phoneNumber: String): JSONObject {
        val postBodyRequestOtp = postBodyRequestOtp(context, phoneNumber, tCallerAppVersion)

        val response = httpClient.post {
            tCallerClient()
            url("https://account-asia-south1.truecaller.com/v3/sendOnboardingOtp")
            setBody(
                Json.encodeToString(postBodyRequestOtp)
            )
        }

        return stringToJson(response.body<String>())
    }

    suspend fun verifyOtp(phoneNumber: String, requestId: String, token: String): JSONObject {
        val postBodyVerifyOtp = postBodyVerifyOtp(phoneNumber, requestId, token)

        val response = httpClient.post {
            tCallerClient()
            url("https://account-asia-south1.truecaller.com/v1/verifyOnboardingOtp")
            setBody(
                Json.encodeToString(postBodyVerifyOtp)
            )
        }

        return stringToJson(response.body<String>())
    }

}