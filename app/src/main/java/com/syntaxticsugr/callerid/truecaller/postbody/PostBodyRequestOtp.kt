package com.syntaxticsugr.callerid.truecaller.postbody

import android.content.Context
import com.syntaxticsugr.callerid.truecaller.datamodel.App
import com.syntaxticsugr.callerid.truecaller.datamodel.Device
import com.syntaxticsugr.callerid.truecaller.datamodel.InstallationDetails
import com.syntaxticsugr.callerid.truecaller.datamodel.RequestOtp
import com.syntaxticsugr.callerid.truecaller.datamodel.Version
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
    trueCallerAppVersion: Version
): RequestOtp {
    return RequestOtp(
        countryCode = getCountryCode(phoneNumber),
        dialingCode = getDialingCode(phoneNumber),
        installationDetails = InstallationDetails(
            app = App(
                buildVersion = trueCallerAppVersion.buildVersion,
                majorVersion = trueCallerAppVersion.majorVersion,
                minorVersion = trueCallerAppVersion.minorVersion,
                store = "GOOGLE_PLAY",
                updatedStore = null
            ),
            device = Device(
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
            storeVersion = Version(
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
