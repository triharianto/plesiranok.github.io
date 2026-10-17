package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.PlaceCategory
import com.example.data.model.PlaceItem
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelGreenLight
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.TravelokaBlue
import com.example.ui.theme.TravelokaBlueLight
import com.example.ui.theme.TravelokaOrange
import com.example.ui.theme.TravelokaOrangeLight
import com.example.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PlaceDetailSheet(
    place: PlaceItem,
    isFavorite: Boolean,
    language: AppLanguage,
    onDismiss: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            // Photos Carousel
            if (place.localDrawableRes != null) {
                Image(
                    painter = painterResource(id = place.localDrawableRes),
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(14.dp))
            } else if (place.photoUrls.isNotEmpty()) {
                val photoScrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(photoScrollState)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    place.photoUrls.forEach { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = place.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Top Header: Name, Rating, Category & Actions
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = place.category.pastelBg
                        ) {
                            Text(
                                text = AppStrings.getCategoryTitle(place.category, language),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = place.category.accentColor
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        )
                    }

                    // Share & Favorite Buttons
                    Row {
                        IconButton(
                            onClick = {
                                IntentUtils.shareText(
                                    context,
                                    place.name,
                                    "Cek tempat menarik di ${place.region.displayName}: ${place.name}\nAlamat: ${place.address}\nKontak: ${place.contactNumber}"
                                )
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.Gray)
                        }
                        IconButton(onClick = onToggleFavorite) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                }

                // Price Tag
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = place.priceTag,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TravelokaOrange
                    )
                )

                // Rating & Review
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${place.rating}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Text(text = " (${place.reviewCount} ulasan Google)", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0))
                Spacer(modifier = Modifier.height(14.dp))

                // Address & Hours
                Row(verticalAlignment = Alignment.Top) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = TravelokaBlue, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = place.address, style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155)))
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Koordinat: ${place.latitude}, ${place.longitude}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = PastelGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Jam Operasional: ${place.openingHours}", style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155)))
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0))
                Spacer(modifier = Modifier.height(14.dp))

                // Description
                Text(
                    text = "Tentang Tempat",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = place.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF475569),
                        lineHeight = 22.sp
                    )
                )

                // Facilities / Amenities
                if (place.facilities.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = AppStrings.getFacilitiesLabel(language),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        place.facilities.forEach { facility ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = TravelokaBlueLight
                            ) {
                                Text(
                                    text = facility,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = TravelokaBlue,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Fleet / Vehicles (for transport)
                if (!place.vehicleTypes.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = AppStrings.getVehiclesLabel(language),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = TravelokaOrangeLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = place.vehicleTypes,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TravelokaOrange,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Specialties (for food & souvenir)
                if (place.specialties.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = AppStrings.getSpecialtiesLabel(language),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        place.specialties.forEach { spec ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = PastelPinkLight
                            ) {
                                Text(
                                    text = spec,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = PastelPink,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0))
                Spacer(modifier = Modifier.height(16.dp))

                // Connect Google Business & Google Maps
                Text(
                    text = "Akses & Koneksi Resmi",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Google Business Button
                    Button(
                        onClick = {
                            IntentUtils.openGoogleBusiness(context, place.name, place.address)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TravelokaBlue),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_google_biz_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Business, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getGoogleBusinessText(language),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }

                    // Google Maps Button
                    Button(
                        onClick = {
                            IntentUtils.openGoogleMaps(context, place.latitude, place.longitude, place.name)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PastelGreen),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_google_maps_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getGoogleMapsText(language),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Contact Actions: Call & WhatsApp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { IntentUtils.dialPhoneNumber(context, place.contactNumber) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = TravelokaOrangeLight, contentColor = TravelokaOrange),
                        border = BorderStroke(1.dp, TravelokaOrange.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_phone_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${AppStrings.getCallButtonText(language)} (${place.contactNumber})",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }

                    Button(
                        onClick = {
                            val msg = "Halo ${place.name}, saya ingin bertanya tentang reservasi/informasi via aplikasi PLESIRAN."
                            IntentUtils.openWhatsApp(context, place.whatsappNumber, msg)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_whatsapp_btn")
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.getWhatsAppText(language),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
