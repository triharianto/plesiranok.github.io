package com.example.data.model

enum class TransportCategory(
    val id: String,
    val titleId: String
) {
    LAND("land", "Transportasi Darat"),
    SEA("sea", "Transportasi Laut"),
    AIR("air", "Transportasi Udara")
}

data class TransportItem(
    val id: String,
    val region: DestinationRegion,
    val category: TransportCategory,
    val subType: String, // "Bis", "Kereta Api", "Travel", "Kapal Laut", "Feri", "Pesawat"
    val serviceName: String,
    val route: String,
    val departureSchedule: String,
    val fareTag: String,
    val agencyName: String,
    val agencyContact: String,
    val agencyAddress: String,
    val whatsappNumber: String,
    val googleBusinessQuery: String,
    val googleMapsQuery: String,
    val facilities: List<String> = emptyList(),
    val description: String
)
