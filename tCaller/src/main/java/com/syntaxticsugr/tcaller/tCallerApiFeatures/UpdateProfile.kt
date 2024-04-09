package com.syntaxticsugr.tcaller.tCallerApiFeatures

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.Gender
import com.syntaxticsugr.tcaller.enums.UpdateResult
import com.syntaxticsugr.tcaller.postbody.postBodyUpdateProfile
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.utils.toJson
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

suspend fun TcallerApiClient.updateProfile(
    context: Context,
    phoneNumber: String,
    firstName: String,
    lastName: String,
    about: String = "",
    city: String = "",
    street: String = "",
    zipCode: String = "",
    companyName: String = "",
    gender: Gender,
    jobTitle: String = "",
    email: String = "",
    facebookId: String = "",
    twitterId: String = ""
): Pair<UpdateResult, JSONObject> {
    val url = "https://profile4-noneu.truecaller.com/v4/profile?encoding=json"

    val postBodyUpdateProfile = postBodyUpdateProfile(
        phoneNumber = phoneNumber,
        firstName = firstName,
        lastName = lastName,
        about = about,
        city = city,
        street = street,
        zipCode = zipCode,
        companyName = companyName,
        gender = gender,
        jobTitle = jobTitle,
        email = email,
        facebookId = facebookId,
        twitterId = twitterId
    )

    val response = httpClient.post(url) {
        tCallerClient()
        header(HttpHeaders.Authorization, "Bearer ${AuthKeyManager.getAuthKey(context)}")
        setBody(Json.encodeToString(postBodyUpdateProfile))
    }

    val resultJson = response.body<String>().toJson()

    val status = resultJson.getInt("status")

    val result = when (status) {
        204 -> UpdateResult.SUCCESS
        else -> UpdateResult.ERROR
    }

    return Pair(result, resultJson)
}
