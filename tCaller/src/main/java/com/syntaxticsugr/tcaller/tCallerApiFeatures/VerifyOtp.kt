package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.VerifyResult
import com.syntaxticsugr.tcaller.postbody.postBodyVerifyOtp
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
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
): Pair<VerifyResult, JSONObject> {
    val postBodyVerifyOtp = postBodyVerifyOtp(phoneNumber, requestId, token)

    val response =
        httpClient.post("https://account-asia-south1.truecaller.com/v1/verifyOnboardingOtp") {
            tCallerClient()
            setBody(Json.encodeToString(postBodyVerifyOtp))
        }

    val resultJson = response.body<String>().toJson()

    val result = if (resultJson.getInt("status") == 2) {
        AuthKeyManager.saveAuthKey(context, resultJson)
        VerifyResult.VERIFICATION_SUCCESSFUL
    } else if (resultJson.getInt("status") == 11) {
        VerifyResult.INVALID_OTP
    } else if (resultJson.getInt("status") == 7) {
        VerifyResult.RETRIES_LIMIT_EXCEEDED
    } else if (resultJson.getBoolean("suspended")) {
        VerifyResult.ACCOUNT_SUSPENDED
    } else {
        VerifyResult.ERROR
    }

    return Pair(result, resultJson)
}
