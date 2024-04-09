package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.SearchResult
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import com.syntaxticsugr.tcaller.utils.toList
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import org.json.JSONObject

suspend fun TcallerApiClient.bulkSearch(
    context: Context,
    phoneNumbers: List<String>
): Pair<SearchResult, List<JSONObject>> {
    val url = "https://search5-noneu.truecaller.com/v2/bulk"

    val response = httpClient.get(url) {
        tCallerClient()
        header(HttpHeaders.Authorization, "Bearer ${AuthKeyManager.getAuthKey(context)}")
        parameter("q", phoneNumbers.joinToString(separator = ","))
        parameter("type", 14)
    }

    val resultJson = response.body<String>().toJson()

    var resultList = listOf(resultJson)

    val result = when (response.status.value) {
        200 -> {
            resultList = resultJson.getJSONArray("data").toList()
            SearchResult.SUCCESS
        }
        429 -> SearchResult.REQUEST_QUOTA_EXCEEDED
        else -> SearchResult.ERROR
    }

    return Pair(result, resultList)
}
