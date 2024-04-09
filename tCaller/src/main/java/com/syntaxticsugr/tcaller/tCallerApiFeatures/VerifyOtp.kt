package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.OnboardingResult
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
    val url = "https://account-noneu.truecaller.com/v1/verifyOnboardingOtp"

    val postBodyVerifyOtp = postBodyVerifyOtp(
        phoneNumber = phoneNumber,
        requestId = requestId,
        token = token
    )

    val response = httpClient.post(url) {
        tCallerClient()
        setBody(Json.encodeToString(postBodyVerifyOtp))
    }

    var resultJson = response.body<String>().toJson()

    val status = resultJson.getInt("status")

    var result = when (status) {
        17 -> VerifyResult.BACKUP_FOUND
        2 -> {
            val installationId = resultJson.getString("installationId")
            AuthKeyManager.saveAuthKey(context = context, installationId = installationId)
            VerifyResult.VERIFICATION_SUCCESSFUL
        }
        11 -> VerifyResult.INVALID_OTP
        7 -> VerifyResult.RETRIES_LIMIT_EXCEEDED
        else -> if (resultJson.optBoolean("suspended")) VerifyResult.ACCOUNT_SUSPENDED else VerifyResult.ERROR
    }

    if (result == VerifyResult.BACKUP_FOUND) {
        val (onBoardingResult, onBoardingResultJson) = TcallerApiClient.completeOnboarding(
            context = context,
            phoneNumber = phoneNumber,
            requestId = requestId
        )

        result = if (onBoardingResult == OnboardingResult.SUCCESSFUL) {
            VerifyResult.VERIFICATION_SUCCESSFUL
        } else {
            VerifyResult.ERROR
        }

        resultJson = onBoardingResultJson
    }

    return Pair(result, resultJson)
}
