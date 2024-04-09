package com.syntaxticsugr.tcaller.postbody

import android.content.Context
import com.syntaxticsugr.tcaller.datamodel.AppDataModel
import com.syntaxticsugr.tcaller.datamodel.DeviceDataModel
import com.syntaxticsugr.tcaller.datamodel.InstallationDetailsDataModel
import com.syntaxticsugr.tcaller.datamodel.RequestOtpDataModel
import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import com.syntaxticsugr.tcaller.utils.getCountryCodeFromPhoneNumber
import com.syntaxticsugr.tcaller.utils.getDeviceId
import com.syntaxticsugr.tcaller.utils.getDeviceLanguage
import com.syntaxticsugr.tcaller.utils.getDeviceManufacturer
import com.syntaxticsugr.tcaller.utils.getDeviceModel
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber
import com.syntaxticsugr.tcaller.utils.getMobileServices

fun postBodyRequestOtp(
    context: Context,
    phoneNumber: String,
    tCallerAppVersion: VersionDataModel
): RequestOtpDataModel {
    return RequestOtpDataModel(
        countryCode = getCountryCodeFromPhoneNumber(phoneNumber),
        dialingCode = getDialingCodeFromPhoneNumber(phoneNumber),
        installationDetails = InstallationDetailsDataModel(
            app = AppDataModel(
                buildVersion = tCallerAppVersion.buildVersion,
                majorVersion = tCallerAppVersion.majorVersion,
                minorVersion = tCallerAppVersion.minorVersion,
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
                simSerials = null
            ),
            language = "en",
            sims = null,
            storeVersion = VersionDataModel(
                buildVersion = tCallerAppVersion.buildVersion,
                majorVersion = tCallerAppVersion.majorVersion,
                minorVersion = tCallerAppVersion.minorVersion
            )
        ),
        phoneNumber = phoneNumber,
        region = "region-2",
        sequenceNo = 2
    )
}
