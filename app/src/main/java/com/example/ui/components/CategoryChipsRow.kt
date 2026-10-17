package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.PlaceCategory
import com.example.ui.theme.PaletteBerry
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoLight

@Composable
fun CategoryChipsRow(
    selectedCategory: PlaceCategory?,
    language: AppLanguage,
    onCategorySelected: (PlaceCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        // "Semua" / All chip
        val isAllSelected = selectedCategory == null
        CategoryItemCircle(
            title = when (language) {
                AppLanguage.INDONESIA -> "Semua"
                AppLanguage.ENGLISH -> "All"
                AppLanguage.TURKISH -> "Tümü"
                AppLanguage.RUSSIAN -> "Все"
                AppLanguage.JAPANESE -> "すべて"
                AppLanguage.CHINESE -> "全部"
            },
            icon = Icons.Default.Apps,
            pastelBg = if (isAllSelected) PaletteBerry else Color(0xFFF1F5F9),
            accentColor = if (isAllSelected) Color.White else Color(0xFF475569),
            isSelected = isAllSelected,
            selectedColor = PaletteBerry,
            onClick = { onCategorySelected(null) },
            testTag = "category_chip_all"
        )

        PlaceCategory.entries.forEach { category ->
            val isSelected = selectedCategory == category
            CategoryItemCircle(
                title = AppStrings.getCategoryTitle(category, language),
                icon = category.icon,
                pastelBg = if (isSelected) category.accentColor else category.pastelBg,
                accentColor = if (isSelected) Color.White else category.accentColor,
                isSelected = isSelected,
                selectedColor = category.accentColor,
                onClick = { onCategorySelected(category) },
                testTag = "category_chip_${category.id}"
            )
        }
    }
}

@Composable
private fun CategoryItemCircle(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    pastelBg: Color,
    accentColor: Color,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(pastelBg)
                .then(
                    if (isSelected) {
                        Modifier.border(2.dp, selectedColor, CircleShape)
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accentColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                lineHeight = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) selectedColor else Color(0xFF334155),
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
