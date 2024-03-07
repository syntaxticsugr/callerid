package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.provider.CallLog
import com.syntaxticsugr.callerid.datamodel.CallerModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCallsLog(context: Context): MutableMap<String, MutableMap<String, MutableMap<String, MutableList<CallerModel>>>> {
    val callMap = mutableMapOf<String, MutableMap<String, MutableMap<String, MutableList<CallerModel>>>>()

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

            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
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
                callMap[dateString] = mutableMapOf()
                callMap[dateString]!!["known"] = mutableMapOf()
                callMap[dateString]!!["unknown"] = mutableMapOf()
            }

            if (name.isNotBlank()) {
                if (!callMap[dateString]!!["known"]!!.containsKey(number)) {
                    callMap[dateString]!!["known"]!![number] = mutableListOf()
                }

                callMap[dateString]!!["known"]!![number]!!.add(caller)
            } else {
                if (!callMap[dateString]!!["unknown"]!!.containsKey(number)) {
                    callMap[dateString]!!["unknown"]!![number] = mutableListOf()
                }

                callMap[dateString]!!["unknown"]!![number]!!.add(caller)
            }
        }
    }

    return callMap
}
