package com.syntaxticsugr.callerid.ui.widgets

import android.provider.CallLog
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.utils.isOutgoingMissedCall

@Composable
fun CallTypeIcon(
    type: Int,
    duration: String,
    size: Dp
) {
    val iconResId = when (type) {
        CallLog.Calls.INCOMING_TYPE -> R.drawable.baseline_call_received_24
        CallLog.Calls.OUTGOING_TYPE -> if (isOutgoingMissedCall(type, duration)) {
            R.drawable.baseline_call_missed_outgoing_24
        } else {
            R.drawable.baseline_call_made_24
        }
        CallLog.Calls.MISSED_TYPE -> R.drawable.baseline_call_missed_24
        CallLog.Calls.VOICEMAIL_TYPE -> R.drawable.baseline_voicemail_24
        CallLog.Calls.REJECTED_TYPE -> R.drawable.baseline_call_received_24
        CallLog.Calls.BLOCKED_TYPE -> R.drawable.baseline_block_24
        CallLog.Calls.ANSWERED_EXTERNALLY_TYPE -> R.drawable.devices_other_24
        else -> R.drawable.baseline_bug_report_24
    }

    val tint = if (
        type == CallLog.Calls.MISSED_TYPE ||
        type == CallLog.Calls.REJECTED_TYPE ||
        isOutgoingMissedCall(type, duration)
    ) {
        MaterialTheme.colorScheme.error
    } else {
        LocalContentColor.current
    }

    Icon(
        painter = painterResource(id = iconResId),
        modifier = Modifier.size(size),
        tint = tint,
        contentDescription = null
    )
}
