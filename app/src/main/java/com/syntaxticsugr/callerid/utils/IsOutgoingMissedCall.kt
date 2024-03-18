package com.syntaxticsugr.callerid.utils

import android.provider.CallLog

fun isOutgoingMissedCall(type: Int, duration: String): Boolean {
    return type == CallLog.Calls.OUTGOING_TYPE && duration == "0 sec"
}
