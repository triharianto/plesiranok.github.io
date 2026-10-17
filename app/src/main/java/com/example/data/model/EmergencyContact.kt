package com.example.data.model

data class EmergencyContact(
    val id: String,
    val region: DestinationRegion,
    val title: String,
    val subTitle: String,
    val phoneNumber: String,
    val address: String,
    val type: EmergencyType,
    val is24Hours: Boolean = true
)

enum class EmergencyType {
    POLICE,
    HOSPITAL,
    AMBULANCE,
    SAR_RESCUE,
    TOURISM_POLICE
}
