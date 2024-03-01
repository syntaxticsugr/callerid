package com.syntaxticsugr.callerid.truecaller

import android.content.Context
import com.syntaxticsugr.callerid.truecaller.datamodel.RequestResponse
import com.syntaxticsugr.callerid.truecaller.datamodel.VerifyResponse
import com.syntaxticsugr.callerid.truecaller.datamodel.Version
import com.syntaxticsugr.callerid.truecaller.postbody.postBodyRequestOtp
import com.syntaxticsugr.callerid.truecaller.postbody.postBodyVerifyOtp
import com.syntaxticsugr.callerid.truecaller.utils.getAndroidVersion
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

class TrueCallerApiClient {

    private val trueCallerAppVersion = Version(
        buildVersion = 8,
        majorVersion = 13,
        minorVersion = 56
    )

    private val clientsecret = "lvc22mp3l1sfv6ujg83rd17btt"

    val userAgent =
        "Truecaller/${trueCallerAppVersion.majorVersion}.${trueCallerAppVersion.minorVersion}.${trueCallerAppVersion.buildVersion} (Android;${getAndroidVersion()})"


    @OptIn(ExperimentalSerializationApi::class)
    private fun createClient() = HttpClient(OkHttp) {
        expectSuccess = true

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

    private fun HttpRequestBuilder.truecallerClient() {
        contentType(ContentType.Application.Json)
        headers {
            append("accept-encoding", "gzip")
            append("user-agent", userAgent)
            append("clientsecret", clientsecret)
        }
    }

    private var httpClient = createClient()

    suspend fun requestOtp(phoneNumber: String, context: Context): RequestResponse {
        val postBodyRequestOtp = postBodyRequestOtp(context, phoneNumber, trueCallerAppVersion)

        val response = httpClient.post {
            truecallerClient()
            url("https://account-asia-south1.truecaller.com/v3/sendOnboardingOtp")
            setBody(
                Json.encodeToString(postBodyRequestOtp)
            )
        }

        return response.body<RequestResponse>()
    }

    suspend fun verifyOtp(phoneNumber: String, requestId: String, token: String): VerifyResponse {
        val postBodyVerifyOtp = postBodyVerifyOtp(phoneNumber, requestId, token)

        val response = httpClient.post {
            truecallerClient()
            url("https://account-asia-south1.truecaller.com/v1/verifyOnboardingOtp")
            setBody(
                Json.encodeToString(postBodyVerifyOtp)
            )
        }

        return response.body<VerifyResponse>()
    }

}
