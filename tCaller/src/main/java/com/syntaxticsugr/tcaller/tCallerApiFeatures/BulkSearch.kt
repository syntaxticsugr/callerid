package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import com.syntaxticsugr.tcaller.utils.toList
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import org.json.JSONObject

suspend fun TcallerApiClient.bulkSearch(context: Context, q: List<String>): List<JSONObject> {
    //    {
    //        "data": [
    //            {
    //                "key": String
    //                "value": {
    //                    "id": String,
    //                    "name": String,
    //                    "imId": String,
    //                    "gender": String,
    //                    "image": String (image url),
    //                    "score": Float,
    //                    "access": String,
    //                    "enhanced": Boolean,
    //                    "phones": [
    //                        {
    //                            "e164Format": String,
    //                            "numberType": String,
    //                            "nationalFormat": String,
    //                            "dialingCode": Int,
    //                            "countryCode": String,
    //                            "carrier": String,
    //                            "type": String
    //                        }
    //                    ],
    //                    "addresses": [
    //                        {
    //                            "address": String,
    //                            "city": String,
    //                            "countryCode": String,
    //                            "timeZone": String,
    //                            "type": String
    //                        }
    //                    ],
    //                    "internetAddresses": [
    //                        {
    //                            "id": String,
    //                            "service": String,
    //                            "caption": String,
    //                            "type": String
    //                        }
    //                    ],
    //                    "badges": [
    //                        String
    //                    ],
    //                    "tags": [],
    //                    "cacheTtl": Int,
    //                    "sources": [],
    //                    "searchWarnings": [],
    //                    "surveys": [
    //                        {
    //                            "id": String,
    //                            "frequency": Int,
    //                            "passthroughData": String,
    //                            "perNumberCooldown": Int,
    //                            "dynamicContentAccessKey": String
    //                        },
    //                        {
    //                            "id": String,
    //                            "frequency": Int,
    //                            "passthroughData": String,
    //                            "perNumberCooldown": Int,
    //                            "dynamicContentAccessKey": String
    //                        }
    //                    ],
    //                    "commentsStats": {
    //                        "showComments": Boolean
    //                    },
    //                    "manualCallerIdPrompt": Boolean,
    //                    "ns": Int
    //                }
    //            },
    //            {
    //                ...
    //            }
    //        ],
    //        "provider": String,
    //        "stats": {
    //            "sourceStats": []
    //        }
    //    }
    val response = httpClient.get("https://search5-noneu.truecaller.com/v2/bulk") {
        tCallerClient()
        header(HttpHeaders.Authorization, "Bearer ${AuthKeyManager.getAuthKey(context)}")
        parameter("q", q.joinToString(separator = ","))
        parameter("countryCode", null)
        parameter("type", 14)
        parameter("locAddr", null)
    }

    val resultJson = response.body<String>().toJson()

    return resultJson.getJSONArray("data").toList()
}
