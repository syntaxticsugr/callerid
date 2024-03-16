package com.syntaxticsugr.tcaller

import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.ktor.http.withCharset
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object TcallerApiClient {

    val tCallerAppVersion = VersionDataModel(
        buildVersion = 8,
        majorVersion = 13,
        minorVersion = 56
    )

    private val majorVersion = tCallerAppVersion.majorVersion
    private val minorVersion = tCallerAppVersion.minorVersion
    private val buildVersion = tCallerAppVersion.buildVersion
    private val androidVersion = getAndroidVersion()

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

    private const val CLIENTSECRET = "lvc22mp3l1sfv6ujg83rd17btt"
    private val userAgent =
        "Truecaller/${majorVersion}.${minorVersion}.${buildVersion} (Android;${androidVersion})"

    fun HttpRequestBuilder.tCallerClient() {
        contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        accept(ContentType.Application.GZip)
        userAgent(userAgent)
        header("clientsecret", CLIENTSECRET)
    }

    var httpClient = createClient()

}
