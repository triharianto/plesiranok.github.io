package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.data.model.TransportCategory
import com.example.data.model.TransportItem
import com.example.ui.theme.*
import com.example.util.IntentUtils

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransportScreen(
    region: DestinationRegion,
    language: AppLanguage,
    items: List<TransportItem>,
    selectedCategory: TransportCategory?,
    onCategorySelect: (TransportCategory?) -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("trans_screen_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali ke Beranda",
                                tint = Color(0xFF1E293B)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Column {
                        Text(
                            text = "Transportasi ${region.displayName}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "Darat, Laut, Udara (Terkoneksi Google Bisnis)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TravelokaBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = TravelokaBlueLight
                ) {
                    Text(
                        text = "${items.size} Rute",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TravelokaBlue
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Category Filter Tabs (Darat, Laut, Udara)
        item {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier.horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // All
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selectedCategory == null) PaletteIndigo else Color.White,
                    border = BorderStroke(1.dp, if (selectedCategory == null) PaletteIndigo else Color(0xFFCBD5E1)),
                    modifier = Modifier
                        .clickable { onCategorySelect(null) }
                        .testTag("trans_filter_all")
                ) {
                    Text(
                        text = "Semua Transportasi",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (selectedCategory == null) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedCategory == null) Color.White else Color(0xFF334155)
                        ),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }

                // Darat
                val isLandSelected = selectedCategory == TransportCategory.LAND
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isLandSelected) PaletteOrange else Color.White,
                    border = BorderStroke(1.dp, if (isLandSelected) PaletteOrange else Color(0xFFCBD5E1)),
                    modifier = Modifier
                        .clickable { onCategorySelect(TransportCategory.LAND) }
                        .testTag("trans_filter_land")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.DirectionsBus, contentDescription = null, tint = if (isLandSelected) Color.White else PaletteOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getTransportCategoryTitle(TransportCategory.LAND, language),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isLandSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isLandSelected) Color.White else Color(0xFF334155)
                            )
                        )
                    }
                }

                // Laut
                val isSeaSelected = selectedCategory == TransportCategory.SEA
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSeaSelected) PaletteIndigo else Color.White,
                    border = BorderStroke(1.dp, if (isSeaSelected) PaletteIndigo else Color(0xFFCBD5E1)),
                    modifier = Modifier
                        .clickable { onCategorySelect(TransportCategory.SEA) }
                        .testTag("trans_filter_sea")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.DirectionsBoat, contentDescription = null, tint = if (isSeaSelected) Color.White else PaletteIndigo, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getTransportCategoryTitle(TransportCategory.SEA, language),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSeaSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSeaSelected) Color.White else Color(0xFF334155)
                            )
                        )
                    }
                }

                // Udara
                val isAirSelected = selectedCategory == TransportCategory.AIR
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isAirSelected) PaletteCoral else Color.White,
                    border = BorderStroke(1.dp, if (isAirSelected) PaletteCoral else Color(0xFFCBD5E1)),
                    modifier = Modifier
                        .clickable { onCategorySelect(TransportCategory.AIR) }
                        .testTag("trans_filter_air")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Flight, contentDescription = null, tint = if (isAirSelected) Color.White else PaletteCoral, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getTransportCategoryTitle(TransportCategory.AIR, language),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isAirSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isAirSelected) Color.White else Color(0xFF334155)
                            )
                        )
                    }
                }
            }
        }

        // List of Transportation Items
        items(items) { item ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("trans_card_${item.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header Subtype & Fare Tag
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val (chipBg, chipText, icon) = when (item.category) {
                            TransportCategory.LAND -> Triple(PastelAmberLight, PastelAmber, when (item.subType) {
                                "Kereta Api" -> Icons.Default.Train
                                else -> Icons.Default.DirectionsBus
                            })
                            TransportCategory.SEA -> Triple(PastelTealLight, PastelTeal, Icons.Default.DirectionsBoat)
                            TransportCategory.AIR -> Triple(Color(0xFFE0E7FF), Color(0xFF4338CA), Icons.Default.Flight)
                        }

                        Surface(shape = RoundedCornerShape(8.dp), color = chipBg) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = icon, contentDescription = null, tint = chipText, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${item.subType} (${AppStrings.getTransportCategoryTitle(item.category, language).split(" ")[0]})",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = chipText)
                                )
                            }
                        }

                        Text(
                            text = item.fareTag,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TravelokaOrange
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Service Name & Route
                    Text(
                        text = item.serviceName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Rute: ${item.route}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TravelokaBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(10.dp))

                    // Jadwal Keberangkatan
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = AppStrings.getScheduleLabel(language),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                            )
                            Text(
                                text = item.departureSchedule,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Agen Pemesanan & Alamat
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.Store, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = AppStrings.getBookingAgencyLabel(language),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                            )
                            Text(
                                text = item.agencyName,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                            )
                            Text(
                                text = item.agencyAddress,
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF64748B))
                            )
                        }
                    }

                    if (item.facilities.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            item.facilities.forEach { fac ->
                                Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFF1F5F9)) {
                                    Text(
                                        text = fac,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Color(0xFF475569)),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons: Google Bisnis + Maps + Phone + WhatsApp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Google Bisnis Button
                        Button(
                            onClick = {
                                IntentUtils.openGoogleBusiness(context, item.agencyName, item.agencyAddress)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TravelokaBlue),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_g_biz_${item.id}")
                        ) {
                            Icon(imageVector = Icons.Default.Business, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = AppStrings.getGoogleBusinessText(language),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1
                            )
                        }

                        // Call Agen
                        OutlinedButton(
                            onClick = {
                                IntentUtils.dialPhoneNumber(context, item.agencyContact)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = TravelokaOrangeLight, contentColor = TravelokaOrange),
                            border = BorderStroke(1.dp, TravelokaOrange.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_call_agency_${item.id}")
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = AppStrings.getCallButtonText(language),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1
                            )
                        }

                        // WhatsApp Booking
                        Button(
                            onClick = {
                                val msg = "Halo ${item.agencyName}, saya ingin menanyakan pemesanan tiket untuk ${item.serviceName} rute ${item.route} via aplikasi PLESIRAN."
                                IntentUtils.openWhatsApp(context, item.whatsappNumber, msg)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_wa_agency_${item.id}")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Pesan WA",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
