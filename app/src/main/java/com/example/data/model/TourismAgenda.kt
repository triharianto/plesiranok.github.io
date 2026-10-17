package com.example.data.model

data class TourismAgenda(
    val id: String,
    val region: DestinationRegion,
    val title: String,
    val categoryTag: String,
    val dateRangeText: String,
    val timeText: String,
    val location: String,
    val ticketPrice: String,
    val organizer: String,
    val description: String,
    val startYear: Int = 2026,
    val startMonth: Int = 10,
    val startDay: Int = 15,
    val startHour: Int = 9,
    val startMinute: Int = 0,
    val endHour: Int = 21,
    val endMinute: Int = 0
)
