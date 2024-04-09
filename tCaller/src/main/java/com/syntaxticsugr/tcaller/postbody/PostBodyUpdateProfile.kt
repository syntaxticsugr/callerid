package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.AddressDataModel
import com.syntaxticsugr.tcaller.datamodel.OnlineIdsDataModel
import com.syntaxticsugr.tcaller.datamodel.PersonalDataDataModel
import com.syntaxticsugr.tcaller.datamodel.UserInfoDataModel
import com.syntaxticsugr.tcaller.enums.Gender
import com.syntaxticsugr.tcaller.utils.parsePhoneNumber

fun postBodyUpdateProfile(
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
    twitterId: String = "",
    url: String = "",
): UserInfoDataModel {
    val userPhoneNumber = parsePhoneNumber(phoneNumber)

    return UserInfoDataModel(
        firstName = firstName,
        lastName = lastName,
        personalData = PersonalDataDataModel(
            about = about,
            address = AddressDataModel(
                city = city,
                country = userPhoneNumber.countryCode,
                street = street,
                zipCode = zipCode
            ),
            avatarUrl = "",
            companyName = companyName,
            gender = gender.value,
            isCredUser = false,
            jobTitle = jobTitle,
            onlineIds = OnlineIdsDataModel(
                email = email,
                facebookId = facebookId,
                googleIdToken = "",
                twitterId = twitterId,
                url = url
            ),
            phoneNumbers = listOf(userPhoneNumber.phoneNumberWithoutU002B.toLong()),
            privacy = "Private",
            tags = emptyList()
        )
    )
}
