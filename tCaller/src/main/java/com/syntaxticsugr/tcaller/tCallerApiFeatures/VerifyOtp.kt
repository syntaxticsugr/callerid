package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.VerifyResult
import com.syntaxticsugr.tcaller.postbody.postBodyVerifyOtp
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.stringToJson
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

suspend fun TcallerApiClient.verifyOtp(
    context: Context,
    phoneNumber: String,
    requestId: String,
    token: String
): Map<VerifyResult, JSONObject> {
    val postBodyVerifyOtp = postBodyVerifyOtp(phoneNumber, requestId, token)

    val response =
        httpClient.post("https://account-asia-south1.truecaller.com/v1/verifyOnboardingOtp") {
            tCallerClient()
            setBody(Json.encodeToString(postBodyVerifyOtp))
        }

    val responseJson = stringToJson(response.body<String>())

    val result = if (responseJson.getInt("status") == 2) {
        AuthKeyManager.saveAuthKey(context, responseJson)
        VerifyResult.VERIFICATION_SUCCESSFUL
    } else if (responseJson.getInt("status") == 11) {
        VerifyResult.INVALID_OTP
    } else if (responseJson.getInt("status") == 7) {
        VerifyResult.RETRIES_LIMIT_EXCEEDED
    } else if (responseJson.getBoolean("suspended")) {
        VerifyResult.ACCOUNT_SUSPENDED
    } else {
        VerifyResult.ERROR
    }

    return mapOf(result to responseJson)
}
