package com.syntaxticsugr.tcaller.postbody

import android.content.Context
import com.syntaxticsugr.tcaller.datamodel.AppDataModel
import com.syntaxticsugr.tcaller.datamodel.AppVersionDataModel
import com.syntaxticsugr.tcaller.datamodel.DeviceDataModel
import com.syntaxticsugr.tcaller.datamodel.InstallationDetailsDataModel
import com.syntaxticsugr.tcaller.datamodel.RequestOtpDataModel
import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import com.syntaxticsugr.tcaller.utils.getDeviceId
import com.syntaxticsugr.tcaller.utils.getDeviceLanguage
import com.syntaxticsugr.tcaller.utils.getDeviceManufacturer
import com.syntaxticsugr.tcaller.utils.getDeviceModel
import com.syntaxticsugr.tcaller.utils.getMobileServices
import com.syntaxticsugr.tcaller.utils.parsePhoneNumber

fun postBodyRequestOtp(
    context: Context,
    phoneNumber: String,
    tCallerAppVersion: AppVersionDataModel
): RequestOtpDataModel {
    val userPhoneNumber = parsePhoneNumber(phoneNumber)

    val buildVersion = tCallerAppVersion.buildVersion
    val majorVersion = tCallerAppVersion.majorVersion
    val minorVersion = tCallerAppVersion.minorVersion

    return RequestOtpDataModel(
        countryCode = userPhoneNumber.countryCode,
        dialingCode = userPhoneNumber.dialingCode,
        installationDetails = InstallationDetailsDataModel(
            app = AppDataModel(
                buildVersion = buildVersion,
                majorVersion = majorVersion,
                minorVersion = minorVersion,
                store = "GOOGLE_PLAY"
            ),
            device = DeviceDataModel(
                deviceId = getDeviceId(context),
                language = getDeviceLanguage(),
                manufacturer = getDeviceManufacturer(),
                mobileServices = getMobileServices(),
                model = getDeviceModel(),
                osName = "Android",
                osVersion = getAndroidVersion(),
                simSerials = listOf("")
            ),
            language = "en",
            sims = null,
            storeVersion = VersionDataModel(
                buildVersion = buildVersion,
                majorVersion = majorVersion,
                minorVersion = minorVersion
            )
        ),
        phoneNumber = userPhoneNumber.phoneNumberWithoutU002B,
        region = "region-2",
        sequenceNo = 2
    )
}
