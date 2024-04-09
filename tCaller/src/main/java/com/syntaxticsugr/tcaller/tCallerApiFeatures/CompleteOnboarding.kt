package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.OnboardingResult
import com.syntaxticsugr.tcaller.postbody.postBodyCompleteOnboarding
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

suspend fun TcallerApiClient.completeOnboarding(
    context: Context,
    phoneNumber: String,
    requestId: String
): Pair<OnboardingResult, JSONObject> {
    val postBodyCompleteOnboarding = postBodyCompleteOnboarding(phoneNumber, requestId)

    val response =
        httpClient.post("https://account-noneu.truecaller.com/v1/completeOnboarding") {
            tCallerClient()
            setBody(Json.encodeToString(postBodyCompleteOnboarding))
        }

    val resultJson = response.body<String>().toJson()

    val status = resultJson.getInt("status")

    val result = when (status) {
        19 -> {
            AuthKeyManager.saveAuthKey(context, resultJson)
            OnboardingResult.SUCCESSFUL
        }

        else -> OnboardingResult.ERROR
    }

    return Pair(result, resultJson)
}
