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
    val url = "https://account-noneu.truecaller.com/v3/sendOnboardingOtp"

    val postBodyRequestOtp = postBodyRequestOtp(
        context = context,
        phoneNumber = phoneNumber,
        tCallerAppVersion = tCallerAppVersion
    )

    val response = httpClient.post(url) {
        tCallerClient()
        setBody(Json.encodeToString(postBodyRequestOtp))
    }

    val resultJson = response.body<String>().toJson()

    val status = resultJson.getInt("status")

    val result = when (status) {
        1, 9 -> RequestResult.OTP_SENT
        3 -> {
            val installationId = resultJson.getString("installationId")
            AuthKeyManager.saveAuthKey(context = context, installationId = installationId)
            RequestResult.ALREADY_VERIFIED
        }
        else -> RequestResult.ERROR
    }

    return Pair(result, resultJson)
}
