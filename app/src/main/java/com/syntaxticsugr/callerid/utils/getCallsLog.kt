package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.provider.CallLog
import com.syntaxticsugr.callerid.datamodel.CallerModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCallsLog(context: Context): Map<String, Map<String, Map<String, CallerModel>>> {
    //    "date1" = {
    //        "known" = {
    //            "phoneNumber1" = CallerModel,
    //            "phoneNumber2" = ...,
    //            ...
    //        },
    //        "unknown" = {
    //            ...
    //        }
    //    },
    //    "date2" = {
    //        ...
    //    },
    //    ...
    val callMap =
        mutableMapOf<String, MutableMap<String, MutableMap<String, CallerModel>>>()

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
                date = dateString,
                time = timeString,
                type = type,
                duration = formatDuration(duration)
            )

            if (!callMap.containsKey(dateString)) {
                callMap[dateString] = mutableMapOf()
                callMap[dateString]!!["known"] = mutableMapOf()
                callMap[dateString]!!["unknown"] = mutableMapOf()
            }

            if (name.isNotBlank()) {
                if (!callMap[dateString]!!["known"]!!.containsKey(number)) {
                    callMap[dateString]!!["known"]!![number] = caller
                }
            } else {
                if (!callMap[dateString]!!["unknown"]!!.containsKey(number)) {
                    callMap[dateString]!!["unknown"]!![number] = caller
                }
            }
        }
    }

    return callMap
}


fun getCallsLog(context: Context, phoneNumber: String): List<CallerModel> {
    //    [CallerModel, CallerModel, ...]
    val callMap = mutableListOf<CallerModel>()

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
                date = dateString,
                time = timeString,
                type = type,
                duration = formatDuration(duration)
            )

            if (caller.phoneNumber == phoneNumber) {
                callMap.add(caller)
            }
        }
    }

    return callMap
}
