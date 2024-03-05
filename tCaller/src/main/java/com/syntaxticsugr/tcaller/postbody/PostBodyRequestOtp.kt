package com.syntaxticsugr.tcaller.postbody

import android.content.Context
import com.syntaxticsugr.tcaller.datamodel.AppDataModel
import com.syntaxticsugr.tcaller.datamodel.DeviceDataModel
import com.syntaxticsugr.tcaller.datamodel.InstallationDetailsDataModel
import com.syntaxticsugr.tcaller.datamodel.RequestOtpDataModel
import com.syntaxticsugr.tcaller.datamodel.VersionDataModel
import com.syntaxticsugr.tcaller.utils.getAndroidVersion
import com.syntaxticsugr.tcaller.utils.getCountryCode
import com.syntaxticsugr.tcaller.utils.getDeviceId
import com.syntaxticsugr.tcaller.utils.getDeviceLanguage
import com.syntaxticsugr.tcaller.utils.getDeviceManufacturer
import com.syntaxticsugr.tcaller.utils.getDeviceModel
import com.syntaxticsugr.tcaller.utils.getDialingCode
import com.syntaxticsugr.tcaller.utils.getMobileServices

fun postBodyRequestOtp(
    context: Context,
    phoneNumber: String,
    trueCallerAppVersion: VersionDataModel
): RequestOtpDataModel {
    return RequestOtpDataModel(
        countryCode = getCountryCode(phoneNumber),
        dialingCode = getDialingCode(phoneNumber),
        installationDetails = InstallationDetailsDataModel(
            app = AppDataModel(
                buildVersion = trueCallerAppVersion.buildVersion,
                majorVersion = trueCallerAppVersion.majorVersion,
                minorVersion = trueCallerAppVersion.minorVersion,
                store = "GOOGLE_PLAY",
                updatedStore = null
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
                buildVersion = trueCallerAppVersion.buildVersion,
                majorVersion = trueCallerAppVersion.majorVersion,
                minorVersion = trueCallerAppVersion.minorVersion
            )
        ),
        phoneNumber = phoneNumber,
        region = "region-2",
        sequenceNo = 2
    )
}
