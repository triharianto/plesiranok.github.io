package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentUtils {

    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        val cleanNumber = phoneNumber.replace("[^0-9+]".toRegex(), "")
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$cleanNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak dapat membuka panggilan: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, phoneNumber: String, message: String = "") {
        val cleanNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
        val formattedNumber = if (cleanNumber.startsWith("0")) {
            "62" + cleanNumber.substring(1)
        } else if (!cleanNumber.startsWith("62")) {
            "62$cleanNumber"
        } else {
            cleanNumber
        }

        val url = "https://api.whatsapp.com/send?phone=$formattedNumber&text=${Uri.encode(message)}"
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp tidak terpasang di perangkat", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGoogleMaps(context: Context, latitude: Double, longitude: Double, label: String) {
        val encodedLabel = Uri.encode(label)
        val geoUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($encodedLabel)")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            // Fallback to browser Google Maps
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            try {
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Gagal membuka peta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openGoogleMapsSearch(context: Context, query: String) {
        val encoded = Uri.encode(query)
        val geoUri = Uri.parse("geo:0,0?q=$encoded")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$encoded")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            try {
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Gagal membuka peta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openGoogleMapsRoute(context: Context, latitude: Double, longitude: Double, label: String = "", destinationName: String = label) {
        val targetName = if (destinationName.isNotBlank()) destinationName else label
        val navUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, navUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            openGoogleMaps(context, latitude, longitude, targetName)
        }
    }

    fun openGoogleBusiness(context: Context, businessName: String, address: String) {
        val query = "$businessName $address"
        val webUri = Uri.parse("https://www.google.com/search?q=${Uri.encode(query)}")
        val intent = Intent(Intent.ACTION_VIEW, webUri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Gagal membuka Google Bisnis", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGoogleTranslate(context: Context, query: String, sourceLang: String = "auto", targetLang: String = "id") {
        val cleanQuery = query.trim()
        val url = if (cleanQuery.isNotEmpty()) {
            "https://translate.google.com/?sl=$sourceLang&tl=$targetLang&text=${Uri.encode(cleanQuery)}&op=translate"
        } else {
            "https://translate.google.com/"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Gagal membuka Google Translate", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareText(context: Context, title: String, text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Bagikan lewat"))
    }

    fun addToGoogleCalendar(
        context: Context,
        title: String,
        description: String,
        location: String,
        startYear: Int,
        startMonth: Int,
        startDay: Int,
        startHour: Int = 9,
        startMinute: Int = 0,
        endHour: Int = 18,
        endMinute: Int = 0
    ) {
        try {
            val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
                data = android.provider.CalendarContract.Events.CONTENT_URI
                putExtra(android.provider.CalendarContract.Events.TITLE, title)
                putExtra(android.provider.CalendarContract.Events.DESCRIPTION, description)
                putExtra(android.provider.CalendarContract.Events.EVENT_LOCATION, location)
                val calStart = java.util.Calendar.getInstance().apply {
                    set(startYear, startMonth - 1, startDay, startHour, startMinute)
                }
                val calEnd = java.util.Calendar.getInstance().apply {
                    set(startYear, startMonth - 1, startDay, endHour, endMinute)
                }
                putExtra(android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME, calStart.timeInMillis)
                putExtra(android.provider.CalendarContract.EXTRA_EVENT_END_TIME, calEnd.timeInMillis)
            }
            context.startActivity(calendarIntent)
        } catch (e: Exception) {
            // Fallback to Google Calendar Web Link
            val startDateStr = String.format("%04d%02d%02dT%02d%02d00Z", startYear, startMonth, startDay, startHour, startMinute)
            val endDateStr = String.format("%04d%02d%02dT%02d%02d00Z", startYear, startMonth, startDay, endHour, endMinute)
            val webUrl = "https://calendar.google.com/calendar/render?action=TEMPLATE" +
                    "&text=${Uri.encode(title)}" +
                    "&details=${Uri.encode(description)}" +
                    "&location=${Uri.encode(location)}" +
                    "&dates=$startDateStr/$endDateStr"
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                context.startActivity(browserIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Tidak dapat membuka Google Kalender", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
