package com.syntaxticsugr.callerid.utils

fun formatDuration(duration: Int): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val seconds = duration % 60

    return if (hours == 0 && minutes == 0) {
        String.format("%02d sec", seconds)
    } else if (hours == 0) {
        String.format("%02d min %02d sec", minutes, seconds)
    } else {
        String.format("%02d hr %02d min %02d sec", hours, minutes, seconds)
    }
}
