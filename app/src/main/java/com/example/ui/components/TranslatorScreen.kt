package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.data.model.LocalPhrase
import com.example.ui.theme.PastelAmber
import com.example.ui.theme.PastelAmberLight
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelGreenLight
import com.example.ui.theme.PastelPurple
import com.example.ui.theme.PastelPurpleLight
import com.example.ui.theme.TravelokaBlue
import com.example.ui.theme.TravelokaBlueLight
import com.example.ui.theme.TravelokaOrange
import com.example.ui.theme.TravelokaOrangeDark
import com.example.ui.theme.TravelokaOrangeLight
import com.example.util.IntentUtils

@Composable
fun TranslatorScreen(
    region: DestinationRegion,
    language: AppLanguage,
    inputText: String,
    outputText: String,
    phrases: List<LocalPhrase>,
    selectedCategory: String?,
    onInputTextChange: (String) -> Unit,
    onCategoryFilterChange: (String?) -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val categories = listOf("Semua", "Sapaan (Greetings)", "Belanja & Nawar", "Arah & Lokasi", "Makan & Restoran", "Keadaan Darurat", "Kamus Gaul Medan")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            // Screen Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("translator_screen_back_btn")
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
                            text = AppStrings.getTranslatorTitle(language),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "Bahasa Daerah: ${region.regionalLanguageName}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TravelokaBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PastelPurpleLight
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = PastelPurple,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        }

        // Live Translation Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Text(
                                text = "Bahasa Indonesia / Global",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF475569),
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Switch",
                            tint = TravelokaBlue,
                            modifier = Modifier.size(20.dp)
                        )

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TravelokaBlueLight
                        ) {
                            Text(
                                text = region.regionalLanguageName.split("(")[0].trim(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TravelokaBlue,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        placeholder = {
                            Text(
                                text = "Ketik kata atau kalimat (contoh: terima kasih, berapa harga, permisi)...",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("translator_input_field"),
                        minLines = 2,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TravelokaBlue,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    if (outputText.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PastelGreenLight,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Hasil Terjemahan:",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PastelGreen,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = outputText,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF1B5E20)
                                        )
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(outputText))
                                        Toast.makeText(context, "Teks terjemahan disalin!", Toast.LENGTH_SHORT).show()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Salin",
                                        tint = PastelGreen
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Google Translate Integration Button
                    Button(
                        onClick = {
                            val targetCode = when (region) {
                                DestinationRegion.JOGJA -> "jv" // Javanese
                                DestinationRegion.BANDUNG -> "su" // Sundanese
                                else -> "id"
                            }
                            val query = inputText.ifBlank { "Halo terima kasih" }
                            IntentUtils.openGoogleTranslate(context, query, sourceLang = "auto", targetLang = targetCode)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TravelokaBlue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("open_google_translate_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.GTranslate,
                            contentDescription = "Google Translate",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Buka di Google Translate",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Region Dialect Guide / Culture Note Card
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = PastelAmberLight,
                border = BorderStroke(1.dp, PastelAmber.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = PastelAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (region) {
                            DestinationRegion.JOGJA -> "Tips Jogja: Gunakan 'Matur nuwun' untuk ucapan terima kasih dan 'Nyuwun sewu' saat permisi lewat."
                            DestinationRegion.MALANG -> "Tips Malang: Warga Malang akrab dengan Boso Walikan (kebalikan kata). 'Sam' = Mas, 'Ayas' = Saya, 'Oskab' = Bakso."
                            DestinationRegion.BANDUNG -> "Tips Bandung: Kata 'Punten' dan 'Hatur nuhun' sangat dihormati. Gunakan panggilan 'Kang' (pria) atau 'Teh' (wanita)."
                            DestinationRegion.DENPASAR -> "Tips Bali: Awali percakapan dengan 'Om Swastyastu' dan akhiri dengan 'Matur suksma' (terima kasih)."
                            DestinationRegion.MEDAN -> "Tips Medan: Sepeda motor disebut 'Kereta', jalan beraspal disebut 'Pasar', dan pasar belanja disebut 'Pajak'."
                        },
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF78350F),
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }

        // Category Filter Chips for Phrases
        item {
            Text(
                text = "Ungkapan Sehari-hari Paling Berguna",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier.horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = (selectedCategory == null && cat == "Semua") || (selectedCategory == cat)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) TravelokaBlue else Color.White,
                        border = BorderStroke(1.dp, if (isSelected) TravelokaBlue else Color(0xFFCBD5E1)),
                        modifier = Modifier
                            .clickable {
                                onCategoryFilterChange(if (cat == "Semua") null else cat)
                            }
                            .testTag("phrase_cat_${cat.replace(" ", "_")}")
                    ) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF334155)
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Phrase Cards
        items(phrases) { phrase ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = TravelokaBlueLight
                        ) {
                            Text(
                                text = phrase.categoryName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TravelokaBlue,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString("${phrase.regionalText} (${phrase.pronunciation})"))
                                Toast.makeText(context, "Disalin: ${phrase.regionalText}", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Phrase",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = phrase.regionalText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TravelokaOrangeDark
                        )
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = "Pronunciation",
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = phrase.pronunciation,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Bahasa Indonesia: \"${phrase.indonesian}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1E293B)
                        )
                    )

                    Text(
                        text = "English: \"${phrase.englishMeaning}\"",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                    )

                    if (!phrase.culturalNote.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "💡 Catatan: ${phrase.culturalNote}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFF0369A1),
                                fontStyle = FontStyle.Italic
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
