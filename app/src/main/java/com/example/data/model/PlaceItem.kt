package com.example.data.model

import androidx.annotation.DrawableRes

data class PlaceItem(
    val id: String,
    val region: DestinationRegion,
    val category: PlaceCategory,
    val name: String,
    val address: String,
    val rating: Double,
    val reviewCount: Int,
    val priceTag: String,
    val ticketPrice: String? = null,
    val subCategory: String? = null,
    @DrawableRes val localDrawableRes: Int? = null,
    val facilities: List<String> = emptyList(),
    val vehicleTypes: String? = null,
    val specialties: List<String> = emptyList(),
    val contactNumber: String,
    val whatsappNumber: String,
    val googleBusinessQuery: String,
    val googleMapsQuery: String,
    val latitude: Double,
    val longitude: Double,
    val openingHours: String = "08:00 - 21:00 WIB",
    val description: String,
    val photoUrls: List<String> = emptyList()
)
