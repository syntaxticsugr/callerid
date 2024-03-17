package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.provider.CallLog
import com.syntaxticsugr.callerid.datamodel.CallModel
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.enums.CallerType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCallsLog(context: Context): Map<String, Map<String, Map<String, CallModel>>> {
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
    val callMap = mutableMapOf<String, MutableMap<String, MutableMap<String, CallModel>>>()

    context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        null,
        null,
        null,
        CallLog.Calls.DATE + " DESC"
    )?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)
        val typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            var number = cursor.getString(numberIndex)
            val date = cursor.getLong(dateIndex)
            val type = cursor.getInt(typeIndex)
            val duration = cursor.getInt(durationIndex)

            if (!number.startsWith("+")) {
                CoroutineScope(Dispatchers.IO).launch {
                    val defaultDialingCode =
                        DataStorePref(context).readString(key = "defaultDialingCode", default = "")

                    number = "${defaultDialingCode}${number}"
                }
            }

            val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))

            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val timeString = timeFormat.format(Date(date))

            val call = CallModel(
                name = name,
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

            val callerType = if (name.isNotBlank()) {
                "known"
            } else {
                "unknown"
            }

            if (!callMap[dateString]!![callerType]!!.containsKey(number)) {
                callMap[dateString]!![callerType]!![number] = call
            }
        }
    }

    return callMap
}

fun getCallsLog(context: Context, phoneNumber: String): List<CallModel> {
    //    [CallerModel, CallerModel, ...]
    val callMap = mutableListOf<CallModel>()

    context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        null,
        null,
        null,
        CallLog.Calls.DATE + " DESC"
    )?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)
        val typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val number = cursor.getString(numberIndex)
            val date = cursor.getLong(dateIndex)
            val type = cursor.getInt(typeIndex)
            val duration = cursor.getInt(durationIndex)

            val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))

            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val timeString = timeFormat.format(Date(date))

            val call = CallModel(
                name = name,
                phoneNumber = number,
                date = dateString,
                time = timeString,
                type = type,
                duration = formatDuration(duration)
            )

            if (call.phoneNumber == phoneNumber) {
                callMap.add(call)
            }
        }
    }

    return callMap
}
