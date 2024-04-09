package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.SearchResult
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import org.json.JSONObject

suspend fun TcallerApiClient.search(
    context: Context,
    phoneNumber: String
): Pair<SearchResult, JSONObject> {
    val url = "https://search5-noneu.truecaller.com/v2/search"

    val response = httpClient.get(url) {
        tCallerClient()
        header(HttpHeaders.Authorization, "Bearer ${AuthKeyManager.getAuthKey(context)}")
        parameter("q", phoneNumber)
        parameter("type", 4)
    }

    var resultJson = response.body<String>().toJson()

    val result = when (response.status.value) {
        200 -> {
            resultJson = resultJson.getJSONArray("data").getJSONObject(0)
            SearchResult.SUCCESS
        }
        429 -> SearchResult.REQUEST_QUOTA_EXCEEDED
        else -> SearchResult.UNEXPECTED_ERROR
    }

    return Pair(result, resultJson)
}
