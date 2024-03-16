package com.syntaxticsugr.callerid.utils

fun formatDuration(duration: Int): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val seconds = duration % 60

    return if (hours == 0 && minutes == 0) {
        String.format("%01d sec", seconds)
    } else if (hours == 0) {
        String.format("%01d min %01d sec", minutes, seconds)
    } else {
        String.format("%01d hr %01d min %01d sec", hours, minutes, seconds)
    }
}
