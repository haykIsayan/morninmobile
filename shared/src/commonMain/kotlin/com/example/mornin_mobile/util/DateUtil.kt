package com.example.mornin_mobile.util

private val MONTH_ABBR = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

/** Converts an ISO date string (YYYY-MM-DD) to a readable form like "Jun 22, 2026". */
fun formatPublishedDate(isoDate: String): String {
    val parts = isoDate.split("-")
    if (parts.size != 3) return isoDate
    val year = parts[0]
    val month = parts[1].toIntOrNull() ?: return isoDate
    val day = parts[2].toIntOrNull() ?: return isoDate
    if (month !in 1..12) return isoDate
    return "${MONTH_ABBR[month - 1]} $day, $year"
}
