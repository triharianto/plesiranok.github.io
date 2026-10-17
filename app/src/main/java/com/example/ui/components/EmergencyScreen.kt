package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.EmergencyContact
import com.example.data.model.EmergencyType
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.TravelokaBlue
import com.example.ui.theme.TravelokaBlueLight
import com.example.ui.theme.TravelokaOrange
import com.example.util.IntentUtils

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton

@Composable
fun EmergencyScreen(
    region: DestinationRegion,
    language: AppLanguage,
    contacts: List<EmergencyContact>,
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
                            modifier = Modifier.testTag("emergency_screen_back_btn")
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
                            text = AppStrings.getEmergencyTitle(language),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "Siaga 24 Jam Kawasan ${region.displayName}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFDC2626), fontWeight = FontWeight.SemiBold)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFEE2E2)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = Color(0xFFDC2626),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        }

        // Quick SOS Grid (Polisi 110, Ambulans 118, SAR 115)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFFCA5A5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Panggilan Cepat Darurat Nasional (Bebas Pulsa)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Polisi 110
                        QuickSosButton(
                            title = "POLISI",
                            number = "110",
                            bgColor = Color(0xFFEFF6FF),
                            textColor = Color(0xFF1D4ED8),
                            icon = Icons.Default.LocalPolice,
                            onClick = { IntentUtils.dialPhoneNumber(context, "110") },
                            testTag = "sos_btn_police"
                        )

                        // Ambulans 118
                        QuickSosButton(
                            title = "AMBULANS",
                            number = "118",
                            bgColor = Color(0xFFFEF2F2),
                            textColor = Color(0xFFDC2626),
                            icon = Icons.Default.LocalHospital,
                            onClick = { IntentUtils.dialPhoneNumber(context, "118") },
                            testTag = "sos_btn_ambulance"
                        )

                        // SAR / Basarnas 115
                        QuickSosButton(
                            title = "SAR RESCUE",
                            number = "115",
                            bgColor = Color(0xFFFFFBEB),
                            textColor = Color(0xFFD97706),
                            icon = Icons.Default.Security,
                            onClick = { IntentUtils.dialPhoneNumber(context, "115") },
                            testTag = "sos_btn_sar"
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Fasilitas Kesehatan & Keamanan ${region.displayName}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            )
        }

        // Contact Cards List
        items(contacts) { contact ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val iconBg = when (contact.type) {
                        EmergencyType.POLICE -> Color(0xFFEFF6FF)
                        EmergencyType.HOSPITAL -> Color(0xFFFEF2F2)
                        EmergencyType.AMBULANCE -> Color(0xFFFDF2F8)
                        EmergencyType.SAR_RESCUE -> Color(0xFFFFFBEB)
                        EmergencyType.TOURISM_POLICE -> Color(0xFFF0FDF4)
                    }
                    val iconColor = when (contact.type) {
                        EmergencyType.POLICE -> Color(0xFF2563EB)
                        EmergencyType.HOSPITAL -> Color(0xFFDC2626)
                        EmergencyType.AMBULANCE -> Color(0xFFDB2777)
                        EmergencyType.SAR_RESCUE -> Color(0xFFD97706)
                        EmergencyType.TOURISM_POLICE -> Color(0xFF16A34A)
                    }
                    val iconVector = when (contact.type) {
                        EmergencyType.POLICE, EmergencyType.TOURISM_POLICE -> Icons.Default.LocalPolice
                        EmergencyType.HOSPITAL, EmergencyType.AMBULANCE -> Icons.Default.LocalHospital
                        EmergencyType.SAR_RESCUE -> Icons.Default.Security
                    }

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = iconVector, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = contact.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                        Text(
                            text = contact.subTitle,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = contact.address,
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF64748B)),
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { IntentUtils.dialPhoneNumber(context, contact.phoneNumber) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                        modifier = Modifier.testTag("dial_emergency_${contact.id}")
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Hubungi", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun QuickSosButton(
    title: String,
    number: String,
    bgColor: Color,
    textColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp)
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = textColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = textColor))
            Text(text = number, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = textColor))
        }
    }
}
