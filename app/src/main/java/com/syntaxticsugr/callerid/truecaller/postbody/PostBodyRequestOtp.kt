package com.syntaxticsugr.callerid.truecaller.postbody

import android.content.Context
import com.syntaxticsugr.callerid.truecaller.datamodel.AppDataModel
import com.syntaxticsugr.callerid.truecaller.datamodel.DeviceDataModel
import com.syntaxticsugr.callerid.truecaller.datamodel.InstallationDetailsDataModel
import com.syntaxticsugr.callerid.truecaller.datamodel.RequestOtpDataModel
import com.syntaxticsugr.callerid.truecaller.datamodel.VersionDataModel
import com.syntaxticsugr.callerid.truecaller.utils.getAndroidVersion
import com.syntaxticsugr.callerid.truecaller.utils.getCountryCode
import com.syntaxticsugr.callerid.truecaller.utils.getDeviceId
import com.syntaxticsugr.callerid.truecaller.utils.getDeviceLanguage
import com.syntaxticsugr.callerid.truecaller.utils.getDeviceManufacturer
import com.syntaxticsugr.callerid.truecaller.utils.getDeviceModel
import com.syntaxticsugr.callerid.truecaller.utils.getDialingCode
import com.syntaxticsugr.callerid.truecaller.utils.getMobileServices

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
