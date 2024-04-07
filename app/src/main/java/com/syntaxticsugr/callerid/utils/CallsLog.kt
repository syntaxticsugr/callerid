package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.provider.CallLog
import com.syntaxticsugr.callerid.datamodel.CallModel
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CallsLog {

    suspend fun allDates(context: Context): List<String> {
        //    ["d MMM yyyy", "d MMM yyyy", ...]
        val dates = mutableListOf<String>()

        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

        return withContext(Dispatchers.IO) {
            context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                arrayOf(CallLog.Calls.DATE),
                null,
                null,
                "${CallLog.Calls.DATE} DESC"
            )?.use { cursor ->
                val dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE)

                while (cursor.moveToNext()) {
                    val date = cursor.getLong(dateIndex)
                    val dateString = dateFormat.format(Date(date))
                    if (dateString !in dates) {
                        dates.add(dateString)
                    }
                }
            }

            dates
        }
    }

    suspend fun byDate(context: Context, date: String): Map<String, Map<String, CallModel>> {
        //    {
        //        "known" = {
        //            "phoneNumber1" = CallModel,
        //            "phoneNumber2" = ...,
        //            ...
        //        },
        //        "unknown" = {
        //            ...
        //        }
        //    }
        val callMap = mutableMapOf<String, MutableMap<String, CallModel>>()
        callMap["known"] = mutableMapOf()
        callMap["unknown"] = mutableMapOf()

        val savedDialingCode = getSavedDialingCode(context)

        val selection = "${CallLog.Calls.DATE} >= ? AND ${CallLog.Calls.DATE} < ?"
        val startDate = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).parse(date)?.time ?: 0
        val endDate = startDate + (24 * 60 * 60 * 1000)

        return withContext(Dispatchers.IO) {
            context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                arrayOf(
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.DATE,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DURATION
                ),
                selection,
                arrayOf(startDate.toString(), endDate.toString()),
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
                    val callDate = cursor.getLong(dateIndex)
                    val type = cursor.getInt(typeIndex)
                    val duration = cursor.getInt(durationIndex)

                    if (!number.startsWith("+")) {
                        number = "+${savedDialingCode}${number}"
                    }

                    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                    val timeString = timeFormat.format(Date(callDate))

                    val call = CallModel(
                        name = name,
                        phoneNumber = number,
                        date = date,
                        time = timeString,
                        type = type,
                        duration = formatDuration(duration)
                    )

                    val callerType = if (name.isNullOrBlank()) {
                        "unknown"
                    } else {
                        "known"
                    }

                    if (!callMap[callerType]!!.containsKey(number)) {
                        callMap[callerType]!![number] = call
                    }
                }
            }

            callMap
        }
    }

    suspend fun byPhoneNumber(context: Context, phoneNumber: String): List<CallModel> {
        //    [CallModel, CallModel, ...]
        val callList = mutableListOf<CallModel>()

        val savedDialingCode = getSavedDialingCode(context)
        val dialingCode = "+${getDialingCodeFromPhoneNumber(phoneNumber)}"

        val selection = "${CallLog.Calls.NUMBER} = ? OR ${CallLog.Calls.NUMBER} = ?"

        return withContext(Dispatchers.IO) {
            context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                arrayOf(
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.DATE,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DURATION
                ),
                selection,
                arrayOf(phoneNumber, phoneNumber.replace(dialingCode, "")),
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
                    val callDate = cursor.getLong(dateIndex)
                    val type = cursor.getInt(typeIndex)
                    val duration = cursor.getInt(durationIndex)

                    if (!number.startsWith("+")) {
                        number = "+${savedDialingCode}${number}"
                    }

                    val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
                    val dateString = dateFormat.format(Date(callDate))

                    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                    val timeString = timeFormat.format(Date(callDate))

                    val call = CallModel(
                        name = name,
                        phoneNumber = number,
                        date = dateString,
                        time = timeString,
                        type = type,
                        duration = formatDuration(duration)
                    )

                    callList.add(call)
                }
            }

            callList
        }
    }

}
