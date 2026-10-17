package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.ui.theme.PaletteCoral
import com.example.ui.theme.PaletteCoralLight
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoLight
import com.example.ui.theme.PaletteOrange
import com.example.ui.theme.PaletteOrangeLight
import com.example.ui.theme.PaletteYellowLight
import com.example.ui.theme.TravelokaBlue
import com.example.ui.theme.TravelokaBlueLight
import com.example.ui.theme.TravelokaOrange
import com.example.ui.theme.TravelokaOrangeLight
import com.example.util.IntentUtils

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlaceCard(
    place: PlaceItem,
    isFavorite: Boolean,
    language: AppLanguage,
    onPlaceClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onPlaceClick)
            .testTag("place_card_${place.id}")
    ) {
        Column {
            // Header Image & Overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                if (place.localDrawableRes != null) {
                    Image(
                        painter = painterResource(id = place.localDrawableRes),
                        contentDescription = place.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    val photoUrl = place.photoUrls.firstOrNull() ?: "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800"
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = place.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }

                // Category & Subcategory pills on top-left
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopStart),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = place.category.pastelBg
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = place.category.icon,
                                contentDescription = null,
                                tint = place.category.accentColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = AppStrings.getCategoryTitle(place.category, language),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = place.category.accentColor
                                )
                            )
                        }
                    }

                    if (!place.subCategory.isNullOrBlank()) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            border = BorderStroke(1.dp, place.category.accentColor.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = place.subCategory,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = place.category.accentColor,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Favorite Button on top-right
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(36.dp)
                        .align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.testTag("favorite_button_${place.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Bookmark",
                            tint = if (isFavorite) Color.Red else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Rating & Price Badge on bottom overlay
                Surface(
                    shape = RoundedCornerShape(topEnd = 12.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${place.rating}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = " (${place.reviewCount})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }

            // Body Content
            Column(modifier = Modifier.padding(14.dp)) {
                // Name & Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Price highlight
                Text(
                    text = place.priceTag,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TravelokaOrange
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Address",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF64748B)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Category Specific Badges / Chips
                when (place.category) {
                    PlaceCategory.HOTEL -> {
                        if (place.facilities.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                place.facilities.take(3).forEach { fac ->
                                    FacilityBadge(text = fac, bg = TravelokaBlueLight, color = TravelokaBlue)
                                }
                            }
                        }
                    }
                    PlaceCategory.ENTERTAINMENT -> {
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (!place.ticketPrice.isNullOrBlank()) {
                                FacilityBadge(text = "Tiket: ${place.ticketPrice}", bg = PaletteYellowLight, color = PaletteOrange)
                            }
                            FacilityBadge(text = "Buka: ${place.openingHours}", bg = PaletteIndigoLight, color = PaletteIndigo)
                            place.facilities.take(2).forEach { fac ->
                                FacilityBadge(text = fac, bg = PaletteCoralLight, color = PaletteCoral)
                            }
                        }
                    }
                    PlaceCategory.TRANSPORT -> {
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (!place.subCategory.isNullOrBlank()) {
                                FacilityBadge(text = place.subCategory, bg = PaletteOrangeLight, color = PaletteOrange)
                            }
                            if (!place.vehicleTypes.isNullOrBlank()) {
                                FacilityBadge(
                                    text = place.vehicleTypes,
                                    bg = PaletteIndigoLight,
                                    color = PaletteIndigo
                                )
                            }
                        }
                    }
                    PlaceCategory.SOUVENIR, PlaceCategory.CULINARY -> {
                        if (place.specialties.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                place.specialties.take(2).forEach { spec ->
                                    FacilityBadge(text = spec, bg = PastelPinkLight, color = PastelPink)
                                }
                            }
                        }
                    }
                    PlaceCategory.TOURISM -> {
                        Spacer(modifier = Modifier.height(8.dp))
                        FacilityBadge(
                            text = "Jam: ${place.openingHours}",
                            bg = PastelGreenLight,
                            color = PastelGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Buttons in Pastel Shades
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Google Business Button
                    OutlinedButton(
                        onClick = {
                            IntentUtils.openGoogleBusiness(context, place.name, place.address)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = TravelokaBlueLight,
                            contentColor = TravelokaBlue
                        ),
                        border = BorderStroke(1.dp, TravelokaBlue.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("google_biz_btn_${place.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = "Google Business",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "G-Bisnis",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }

                    // Google Maps Button
                    OutlinedButton(
                        onClick = {
                            IntentUtils.openGoogleMaps(context, place.latitude, place.longitude, place.name)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = PastelGreenLight,
                            contentColor = PastelGreen
                        ),
                        border = BorderStroke(1.dp, PastelGreen.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("google_maps_btn_${place.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Navigation,
                            contentDescription = "Maps",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Peta",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }

                    // Call Button
                    OutlinedButton(
                        onClick = {
                            IntentUtils.dialPhoneNumber(context, place.contactNumber)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = TravelokaOrangeLight,
                            contentColor = TravelokaOrange
                        ),
                        border = BorderStroke(1.dp, TravelokaOrange.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("call_btn_${place.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Call",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Kontak",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FacilityBadge(text: String, bg: Color, color: Color) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = bg
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                color = color,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}
