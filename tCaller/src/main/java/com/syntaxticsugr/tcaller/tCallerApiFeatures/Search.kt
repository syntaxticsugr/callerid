package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.SearchResult
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber
import com.syntaxticsugr.tcaller.utils.toJson
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import org.json.JSONObject

suspend fun TcallerApiClient.search(context: Context, q: String): Pair<SearchResult, JSONObject> {
    //    {
    //        "data": [
    //            {
    //                "id": String,
    //                "name": String,
    //                "imId": String,
    //                "gender": String,
    //                "image": String (image url),
    //                "score": Float,
    //                "access": String,
    //                "enhanced": Boolean,
    //                "phones": [
    //                    {
    //                        "e164Format": String,
    //                        "numberType": String,
    //                        "nationalFormat": String,
    //                        "dialingCode": Int,
    //                        "countryCode": String,
    //                        "carrier": String,
    //                        "type": String
    //                    }
    //                ],
    //                "addresses": [
    //                    {
    //                        "address": String,
    //                        "city": String,
    //                        "countryCode": String,
    //                        "timeZone": String,
    //                        "type": String
    //                    }
    //                ],
    //                "internetAddresses": [
    //                    {
    //                        "id": String,
    //                        "service": String,
    //                        "caption": String,
    //                        "type": String
    //                    }
    //                ],
    //                "badges": [
    //                    String
    //                ],
    //                "tags": [],
    //                "cacheTtl": Int,
    //                "sources": [],
    //                "searchWarnings": [],
    //                "surveys": [
    //                    {
    //                        "id": String,
    //                        "frequency": Int,
    //                        "passthroughData": String,
    //                        "perNumberCooldown": Int,
    //                        "dynamicContentAccessKey": String
    //                    },
    //                    {
    //                        "id": String,
    //                        "frequency": Int,
    //                        "passthroughData": String,
    //                        "perNumberCooldown": Int,
    //                        "dynamicContentAccessKey": String
    //                    }
    //                ],
    //                "commentsStats": {
    //                    "showComments": Boolean
    //                },
    //                "manualCallerIdPrompt": Boolean,
    //                "ns": Int
    //            }
    //        ],
    //        "provider": String,
    //        "stats": {
    //            "sourceStats": []
    //        }
    //    }
    val response = httpClient.get("https://search5-noneu.truecaller.com/v2/search") {
        tCallerClient()
        header(HttpHeaders.Authorization, "Bearer ${AuthKeyManager.getAuthKey(context)}")
        parameter("q", q)
        parameter("countryCode", getDialingCodeFromPhoneNumber(q))
        parameter("type", 4)
        parameter("locAddr", null)
    }

    val result = when (response.status.value) {
        200 -> SearchResult.SUCCESS
        429 -> SearchResult.REQUEST_QUOTA_EXCEEDED
        else -> SearchResult.UNEXPECTED_ERROR
    }

    val resultJson = when (result) {
        SearchResult.SUCCESS -> response.body<String>().toJson().getJSONArray("data")
            .getJSONObject(0)

        else -> response.body<String>().toJson()
    }

    return Pair(result, resultJson)
}
