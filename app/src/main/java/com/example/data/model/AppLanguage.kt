package com.example.data.model

enum class AppLanguage(
    val code: String,
    val flagEmoji: String,
    val nativeName: String,
    val englishName: String
) {
    INDONESIA(
        code = "id",
        flagEmoji = "🇮🇩",
        nativeName = "Bahasa Indonesia",
        englishName = "Indonesian"
    ),
    ENGLISH(
        code = "en",
        flagEmoji = "🇬🇧",
        nativeName = "English",
        englishName = "English"
    ),
    TURKISH(
        code = "tr",
        flagEmoji = "🇹🇷",
        nativeName = "Türkçe",
        englishName = "Turkish"
    ),
    RUSSIAN(
        code = "ru",
        flagEmoji = "🇷🇺",
        nativeName = "Русский",
        englishName = "Russian"
    ),
    JAPANESE(
        code = "ja",
        flagEmoji = "🇯🇵",
        nativeName = "日本語",
        englishName = "Japanese"
    ),
    CHINESE(
        code = "zh",
        flagEmoji = "🇨🇳",
        nativeName = "简体中文",
        englishName = "Chinese (Simplified)"
    );

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: INDONESIA
        }
    }
}
