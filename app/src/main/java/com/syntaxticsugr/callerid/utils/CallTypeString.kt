package com.syntaxticsugr.callerid.utils

import android.provider.CallLog

fun callTypeString(type: Int): String {
    return when (type) {
        CallLog.Calls.INCOMING_TYPE -> "Incoming Call"
        CallLog.Calls.OUTGOING_TYPE -> "Outgoing Call"
        CallLog.Calls.MISSED_TYPE -> "Missed Call"
        CallLog.Calls.VOICEMAIL_TYPE -> "Voicemail"
        CallLog.Calls.REJECTED_TYPE -> "Rejected Call"
        CallLog.Calls.BLOCKED_TYPE -> "Blocked"
        CallLog.Calls.ANSWERED_EXTERNALLY_TYPE -> "Answered Externally"
        else -> ""
    }
}
