package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.ui.theme.PaletteBerry
import com.example.ui.theme.PaletteCoral
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoDark
import com.example.ui.theme.PaletteOrange
import com.example.ui.theme.PaletteYellow

@Composable
fun DestinationHeader(
    region: DestinationRegion?,
    language: AppLanguage,
    searchQuery: String,
    currentUser: com.google.firebase.auth.FirebaseUser? = null,
    onSignInClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit,
    onRegionClick: () -> Unit,
    onLanguageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Top banner for Beranda:
    // 1. Kolom kiri atas HANYA BERISI: "Mau Berwisata Kemana?" (diklik tampil pilihan kota)
    // 2. Banner paling atas bertuliskan "Selamat Berwisata di Indonesia" (dengan latar belakang berbagai tujuan wisata Indonesia)
    // 3. Saat klik kota tujuan (Yogyakarta / Malang / Bali / Medan / Bandung), banner atas berubah otomatis dengan landmark kota tersebut.
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
    ) {
        // Latar Belakang Foto Banner:
        // Jika region == null: Menampilkan kolase berbagai tujuan wisata Indonesia
        // Jika region != null: Menampilkan landmark kota tujuan yang dipilih (Tugu Jogja, Bromo/Malang, Tanah Lot Bali, Gedung Sate, Istana Maimun)
        AnimatedContent(
            targetState = region,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "banner_background_image"
        ) { currentRegion ->
            val bannerImageRes = currentRegion?.bannerResId ?: R.drawable.banner_indonesia_travel_1790853534159
            val contentDesc = currentRegion?.landmarkName ?: "Berbagai Tujuan Wisata di Indonesia"
            Image(
                painter = painterResource(id = bannerImageRes),
                contentDescription = contentDesc,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
            )
        }

        // Overlay Gradasi Palet Warna (#606BA6, #8C425D, #F25A38):
        // Mempertahankan kejernihan dan keindahan foto landmark sekaligus memastikan teks sangat terbaca
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.50f),
                            Color.Black.copy(alpha = 0.20f),
                            PaletteIndigoDark.copy(alpha = 0.78f),
                            PaletteIndigoDark.copy(alpha = 0.94f)
                        )
                    )
                )
        )

        // Aksen dekorasi palet warna
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(160.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            PaletteYellow.copy(alpha = 0.30f),
                            PaletteOrange.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Konten Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // ==============================================================
            // Baris Paling Atas:
            // Kolom Kiri: HANYA TERTULIS "Mau Berwisata Kemana?"
            // Kolom Kanan: Pilihan Bahasa (Bendera)
            // ==============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Kolom Kiri Atas: HANYA BERISI "Mau Berwisata Kemana?"
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 4.dp,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, PaletteBerry.copy(alpha = 0.35f)),
                    modifier = Modifier
                        .clickable(onClick = onRegionClick)
                        .testTag("mau_berwisata_kemana_button")
                        .testTag("region_selector_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(PaletteBerry.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = PaletteBerry,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mau Berwisata Kemana?",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = PaletteBerry,
                                fontSize = 13.sp,
                                letterSpacing = 0.2.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Pilih Kota",
                            tint = PaletteOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Kolom Kanan Atas: Sign-in & Language Selector
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentUser == null) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .clickable(onClick = onSignInClick)
                                .testTag("btn_google_sign_in")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Sign in with Google",
                                    tint = PaletteIndigo,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Masuk",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PaletteIndigo,
                                        fontSize = 11.5.sp
                                    )
                                )
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .clickable(onClick = onSignOutClick)
                                .testTag("btn_user_profile")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(PaletteBerry),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = (currentUser.displayName?.firstOrNull() ?: 'U').uppercase(),
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentUser.displayName?.split(" ")?.firstOrNull() ?: "Profil",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PaletteBerry,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    // Language Selector (Bendera & Kode Bahasa)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.95f),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .clickable(onClick = onLanguageClick)
                            .testTag("language_selector_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = language.flagEmoji,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = language.code.uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PaletteIndigo,
                                    fontSize = 11.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Pilih Bahasa",
                                tint = Color.Gray,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==============================================================
            // Konten Banner:
            // Jika belum pilih kota: "Selamat Berwisata di Indonesia" dengan berbagai tujuan wisata Indonesia
            // Jika pilih kota (Yogyakarta/Malang/Bali/Medan/Bandung): Latar & info berubah otomatis sesuai landmark kota
            // ==============================================================
            AnimatedContent(
                targetState = region,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "city_banner_content_transition"
            ) { currentRegion ->
                Column {
                    if (currentRegion == null) {
                        // 1. TAMPILAN DEFAULT INDONESIA:
                        // "Selamat Berwisata di Indonesia" dengan latar belakang berbagai tujuan wisata Indonesia
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PaletteYellow.copy(alpha = 0.95f)
                        ) {
                            Text(
                                text = "🇮🇩 WONDERFUL INDONESIA • NUSANTARA",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF4A2800),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Selamat Berwisata di Indonesia",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 26.sp,
                                lineHeight = 32.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.Black.copy(alpha = 0.35f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Pesona Destinasi Ikonik Nusantara",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = PaletteYellow,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Jelajahi keindahan Yogyakarta, Malang, Bali, Bandung, dan Medan. Klik 'Mau Berwisata Kemana?' untuk memilih kota tujuan Anda.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.95f),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                )
                            }
                        }
                    } else {
                        // 2. TAMPILAN KOTA TUJUAN TERPILIH (Yogyakarta, Malang, Bali, Medan, Bandung):
                        // Menampilkan landmark dan nama kota dalam huruf besar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PaletteYellow.copy(alpha = 0.95f)
                            ) {
                                Text(
                                    text = "🇮🇩 WONDERFUL INDONESIA",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF4A2800),
                                        fontWeight = FontWeight.Black,
                                        fontSize = 10.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PaletteOrange.copy(alpha = 0.90f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountBalance,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Landmark: ${currentRegion.landmarkName}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Selamat Berwisata di",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.White.copy(alpha = 0.90f),
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                letterSpacing = 1.sp
                            )
                        )

                        // NAMA KOTA DALAM HURUF BESAR: YOGYAKARTA, MALANG, BALI, MEDAN, BANDUNG
                        Text(
                            text = currentRegion.cityUppercaseName,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 30.sp,
                                lineHeight = 34.sp,
                                letterSpacing = 0.5.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Kotak Informasi Akurat Kota
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.Black.copy(alpha = 0.35f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                Text(
                                    text = "${currentRegion.province} • ${currentRegion.tagline}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = PaletteYellow,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 11.5.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentRegion.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.95f),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    ),
                                    maxLines = 2
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.RecordVoiceOver,
                                        contentDescription = null,
                                        tint = PaletteYellow,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Bahasa Daerah: ${currentRegion.regionalLanguageName}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White.copy(alpha = 0.90f),
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Kolom Pencarian Cepat
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_bar_surface")
            ) {
                val searchPrompt = if (region != null) {
                    "Cari di ${region.displayName.split(" ")[0]} (hotel, wisata, kuliner)..."
                } else {
                    "Cari hotel, wisata, kuliner di Indonesia..."
                }
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = searchPrompt,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF94A3B8),
                                fontSize = 13.sp
                            ),
                            maxLines = 1
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = PaletteIndigo
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PaletteYellow,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
