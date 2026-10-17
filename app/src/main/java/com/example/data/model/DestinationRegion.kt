package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

enum class DestinationRegion(
    val id: String,
    val displayName: String,
    val cityUppercaseName: String,
    val province: String,
    val tagline: String,
    val landmarkName: String,
    val regionalLanguageName: String,
    val latitude: Double,
    val longitude: Double,
    @DrawableRes val bannerResId: Int,
    val description: String
) {
    JOGJA(
        id = "jogja",
        displayName = "Yogyakarta",
        cityUppercaseName = "YOGYAKARTA",
        province = "D.I. Yogyakarta",
        tagline = "Kota Budaya & Istimewa",
        landmarkName = "Tugu Pal Putih Jogja",
        regionalLanguageName = "Basa Jawa (Ngoko & Krama)",
        latitude = -7.7956,
        longitude = 110.3695,
        bannerResId = R.drawable.banner_jogja_1790340186211,
        description = "Pusat seni, budaya klasik Jawa, candi megah, keraton, dan surga kuliner istimewa berlatar Tugu Jogja legendaris."
    ),
    MALANG(
        id = "malang",
        displayName = "Malang",
        cityUppercaseName = "MALANG",
        province = "Jawa Timur",
        tagline = "Kota Apel, Bromo & Hawanya Sejuk",
        landmarkName = "Kampung Warna-Warni Jodipan",
        regionalLanguageName = "Basa Walikan & Malangan",
        latitude = -7.9797,
        longitude = 112.6304,
        bannerResId = R.drawable.banner_malang_1790340199102,
        description = "Kota sejuk berhawa pegunungan dengan daya tarik Kampung Warna-Warni Jodipan, pesona Bromo, apel segar, dan wisata sejarah."
    ),
    DENPASAR(
        id = "denpasar",
        displayName = "Bali",
        cityUppercaseName = "BALI",
        province = "Bali",
        tagline = "Pintu Gerbang Pulau Dewata",
        landmarkName = "Pura Tanah Lot",
        regionalLanguageName = "Basa Bali (Alus & Madya)",
        latitude = -8.6705,
        longitude = 115.2126,
        bannerResId = R.drawable.banner_denpasar_1790340226439,
        description = "Pintu gerbang Pulau Dewata dengan panorama eksotis Pura Tanah Lot, pantai selancar dunia, kesenian tari sakral, dan pura agung."
    ),
    MEDAN(
        id = "medan",
        displayName = "Medan",
        cityUppercaseName = "MEDAN",
        province = "Sumatera Utara",
        tagline = "Kota Heritage Deli & Kuliner Paten",
        landmarkName = "Istana Maimun",
        regionalLanguageName = "Bahasa Medan & Batak",
        latitude = 3.5952,
        longitude = 98.6722,
        bannerResId = R.drawable.banner_medan_1790340247894,
        description = "Metropolitan Sumatera dengan warisan Istana Maimun Kesultanan Deli, gerbang Danau Toba, durian legendaris, dan ragam budaya."
    ),
    BANDUNG(
        id = "bandung",
        displayName = "Bandung",
        cityUppercaseName = "BANDUNG",
        province = "Jawa Barat",
        tagline = "Paris van Java & Kota Kembang",
        landmarkName = "Gedung Sate",
        regionalLanguageName = "Basa Sunda (Loma & Lemes)",
        latitude = -6.9025,
        longitude = 107.6186,
        bannerResId = R.drawable.banner_bandung_1790340213204,
        description = "Paris van Java nan romantis dengan ikon Gedung Sate, pegunungan hijau, factory outlet belanja, dan sentra kuliner kreatif."
    );

    companion object {
        fun fromId(id: String): DestinationRegion {
            return entries.find { it.id.equals(id, ignoreCase = true) } ?: JOGJA
        }
    }
}
