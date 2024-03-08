package com.syntaxticsugr.tcaller

import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object TcallerApiClient {

    val tCallerAppVersion = VersionDataModel(
        buildVersion = 8,
        majorVersion = 13,
        minorVersion = 56
    )

    private const val CLIENTSECRET = "lvc22mp3l1sfv6ujg83rd17btt"

    private val majorVersion = tCallerAppVersion.majorVersion
    private val minorVersion = tCallerAppVersion.minorVersion
    private val buildVersion = tCallerAppVersion.buildVersion
    private val androidVersion = getAndroidVersion()

    private val userAgent =
        "Truecaller/${majorVersion}.${minorVersion}.${buildVersion} (Android;${androidVersion})"


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

    fun HttpRequestBuilder.tCallerClient() {
        contentType(ContentType.Application.Json)
        headers {
            append("accept-encoding", "gzip")
            append("user-agent", userAgent)
            append("clientsecret", CLIENTSECRET)
        }
    }

    var httpClient = createClient()

}
