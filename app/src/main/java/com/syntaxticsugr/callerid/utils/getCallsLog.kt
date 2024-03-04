package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.provider.CallLog
import com.syntaxticsugr.callerid.datamodel.CallerModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCallsLog(context: Context): Map<String, List<CallerModel>> {
    val callMap = mutableMapOf<String, MutableList<CallerModel>>()

    val cursor = context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        null,
        null,
        null,
        CallLog.Calls.DATE + " DESC"
    )

    cursor?.use { c ->
        val nameIndex = c.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val numberIndex = c.getColumnIndex(CallLog.Calls.NUMBER)
        val dateIndex = c.getColumnIndex(CallLog.Calls.DATE)
        val typeIndex = c.getColumnIndex(CallLog.Calls.TYPE)
        val durationIndex = c.getColumnIndex(CallLog.Calls.DURATION)

        while (c.moveToNext()) {
            val name = c.getString(nameIndex)
            val number = c.getString(numberIndex)
            val date = c.getLong(dateIndex)
            val duration = c.getInt(durationIndex)
            val type = c.getInt(typeIndex)

            val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
            val timeString = timeFormat.format(Date(date))

            val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))

            val caller = CallerModel(
                callerName = name,
                phoneNumber = number,
                time = timeString,
                duration = formatDuration(duration),
                type = type
            )

            if (!callMap.containsKey(dateString)) {
                callMap[dateString] = mutableListOf()
            }

            callMap[dateString]!!.add(caller)
        }
    }

    return callMap
}
