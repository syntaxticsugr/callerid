package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.RequestResult
import com.syntaxticsugr.tcaller.postbody.postBodyRequestOtp
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

suspend fun TcallerApiClient.requestOtp(
    context: Context,
    phoneNumber: String
): Pair<RequestResult, JSONObject> {
    val postBodyRequestOtp = postBodyRequestOtp(context, phoneNumber, tCallerAppVersion)

    val response =
        httpClient.post("https://account-asia-south1.truecaller.com/v3/sendOnboardingOtp") {
            tCallerClient()
            setBody(Json.encodeToString(postBodyRequestOtp))
        }

    val resultJson = response.body<String>().toJson()

    val result = if ((resultJson.getInt("status") == 1) || (resultJson.getInt("status") == 9)) {
        RequestResult.OTP_SENT
    } else if (resultJson.getInt("status") == 3) {
        AuthKeyManager.saveAuthKey(context, resultJson)
        RequestResult.ALREADY_VERIFIED
    } else {
        RequestResult.ERROR
    }

    return Pair(result, resultJson)
}
