package com.example.data.localization

import com.example.data.model.AppLanguage
import com.example.data.model.PlaceCategory
import com.example.data.model.TransportCategory

object AppStrings {

    fun getWelcomeTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Selamat Berwisata di Indonesia"
            AppLanguage.ENGLISH -> "Welcome & Enjoy Traveling in Indonesia"
            AppLanguage.TURKISH -> "Endonezya'da Harika Bir Seyahat Dileriz"
            AppLanguage.RUSSIAN -> "Добро пожаловать в путешествие по Индонезии"
            AppLanguage.JAPANESE -> "インドネシアへようこそ！素敵な旅を"
            AppLanguage.CHINESE -> "欢迎畅游印度尼西亚！"
        }
    }

    fun getWelcomeSubtitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Panduan Lengkap Wisata, Penginapan, Kuliner, Transportasi, & Agenda Budaya Nusantara"
            AppLanguage.ENGLISH -> "Your Complete Guide to Travel, Stays, Food, Transportation & Cultural Events"
            AppLanguage.TURKISH -> "Seyahat, Konaklama, Yemek, Ulaşım ve Kültürel Etkinlikler İçin Kapsamlı Rehberiniz"
            AppLanguage.RUSSIAN -> "Ваш полный путеводитель по отдыху, отелям, еде, транспорту и культуре"
            AppLanguage.JAPANESE -> "観光、宿泊、グルメ、交通、文化イベントの完全ガイド"
            AppLanguage.CHINESE -> "印度尼西亚旅游、住宿、美食、交通与文化活动综合指南"
        }
    }

    fun getHomeNavHeader(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Layanan & Menu Beranda"
            AppLanguage.ENGLISH -> "Home Services & Quick Access"
            AppLanguage.TURKISH -> "Hızlı Erişim & Hizmetler"
            AppLanguage.RUSSIAN -> "Сервисы и быстрое меню"
            AppLanguage.JAPANESE -> "サービス＆クイックアクセス"
            AppLanguage.CHINESE -> "服务与快捷导航"
        }
    }

    fun getCategoryTitle(category: PlaceCategory, lang: AppLanguage): String {
        return when (category) {
            PlaceCategory.HOTEL -> when (lang) {
                AppLanguage.INDONESIA -> "Hotel & Penginapan"
                AppLanguage.ENGLISH -> "Hotels & Stays"
                AppLanguage.TURKISH -> "Oteller ve Konaklama"
                AppLanguage.RUSSIAN -> "Отели и проживание"
                AppLanguage.JAPANESE -> "ホテル＆宿泊施設"
                AppLanguage.CHINESE -> "酒店与住宿"
            }
            PlaceCategory.TOURISM -> when (lang) {
                AppLanguage.INDONESIA -> "Tempat Wisata"
                AppLanguage.ENGLISH -> "Tourist Attractions"
                AppLanguage.TURKISH -> "Gezilecek Yerler"
                AppLanguage.RUSSIAN -> "Достопримечательности"
                AppLanguage.JAPANESE -> "観光名所"
                AppLanguage.CHINESE -> "旅游景点"
            }
            PlaceCategory.ENTERTAINMENT -> when (lang) {
                AppLanguage.INDONESIA -> "Wisata Hiburan"
                AppLanguage.ENGLISH -> "Theme & Fun Parks"
                AppLanguage.TURKISH -> "Eğlence Parkları"
                AppLanguage.RUSSIAN -> "Развлечения и парки"
                AppLanguage.JAPANESE -> "レジャー・娯楽施設"
                AppLanguage.CHINESE -> "休闲娱乐公园"
            }
            PlaceCategory.TRANSPORT -> when (lang) {
                AppLanguage.INDONESIA -> "Travel & Sewa Kendaraan"
                AppLanguage.ENGLISH -> "Travel & Vehicle Rental"
                AppLanguage.TURKISH -> "Seyahat & Araç Kiralama"
                AppLanguage.RUSSIAN -> "Трансфер и аренда авто"
                AppLanguage.JAPANESE -> "送迎・レンタカー"
                AppLanguage.CHINESE -> "包车与租车服务"
            }
            PlaceCategory.SOUVENIR -> when (lang) {
                AppLanguage.INDONESIA -> "Oleh-oleh & Souvenir"
                AppLanguage.ENGLISH -> "Souvenirs & Gifts"
                AppLanguage.TURKISH -> "Hediyelik Eşya"
                AppLanguage.RUSSIAN -> "Сувениры и подарки"
                AppLanguage.JAPANESE -> "お土産・特産品"
                AppLanguage.CHINESE -> "特产与纪念品"
            }
            PlaceCategory.CULINARY -> when (lang) {
                AppLanguage.INDONESIA -> "Kuliner & Restoran"
                AppLanguage.ENGLISH -> "Food & Dining"
                AppLanguage.TURKISH -> "Restoranlar & Yemek"
                AppLanguage.RUSSIAN -> "Рестораны и кухня"
                AppLanguage.JAPANESE -> "グルメ・レストラン"
                AppLanguage.CHINESE -> "美食与餐厅"
            }
        }
    }

    fun getTransportCategoryTitle(category: TransportCategory, lang: AppLanguage): String {
        return when (category) {
            TransportCategory.LAND -> when (lang) {
                AppLanguage.INDONESIA -> "Transportasi Darat"
                AppLanguage.ENGLISH -> "Land Transport"
                AppLanguage.TURKISH -> "Kara Taşımacılığı"
                AppLanguage.RUSSIAN -> "Наземный транспорт"
                AppLanguage.JAPANESE -> "陸上交通 (バス・列車)"
                AppLanguage.CHINESE -> "陆路交通 (汽车/火车)"
            }
            TransportCategory.SEA -> when (lang) {
                AppLanguage.INDONESIA -> "Transportasi Laut"
                AppLanguage.ENGLISH -> "Sea Transport"
                AppLanguage.TURKISH -> "Deniz Taşımacılığı"
                AppLanguage.RUSSIAN -> "Морской транспорт"
                AppLanguage.JAPANESE -> "海上交通 (船舶・フェリー)"
                AppLanguage.CHINESE -> "水上交通 (客轮/轮渡)"
            }
            TransportCategory.AIR -> when (lang) {
                AppLanguage.INDONESIA -> "Transportasi Udara"
                AppLanguage.ENGLISH -> "Air Transport"
                AppLanguage.TURKISH -> "Hava Taşımacılığı"
                AppLanguage.RUSSIAN -> "Воздушный транспорт"
                AppLanguage.JAPANESE -> "航空交通 (飛行機)"
                AppLanguage.CHINESE -> "航空交通 (飞机航班)"
            }
        }
    }

    fun getAgendaTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Agenda Wisata"
            AppLanguage.ENGLISH -> "Tourism Events"
            AppLanguage.TURKISH -> "Turizm Etkinlikleri"
            AppLanguage.RUSSIAN -> "Календарь событий"
            AppLanguage.JAPANESE -> "観光カレンダー・祭事"
            AppLanguage.CHINESE -> "旅游日程与节庆"
        }
    }

    fun getAgendaSubtitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Terkoneksi dengan Google Kalender"
            AppLanguage.ENGLISH -> "Connected to Google Calendar"
            AppLanguage.TURKISH -> "Google Takvim ile Bağlantılı"
            AppLanguage.RUSSIAN -> "Синхронизировано с Google Календарем"
            AppLanguage.JAPANESE -> "Googleカレンダーと連携"
            AppLanguage.CHINESE -> "已连接Google日历"
        }
    }

    fun getAddToCalendarText(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Tambah ke Google Kalender"
            AppLanguage.ENGLISH -> "Add to Google Calendar"
            AppLanguage.TURKISH -> "Google Takvim'e Ekle"
            AppLanguage.RUSSIAN -> "В Google Календарь"
            AppLanguage.JAPANESE -> "Googleカレンダーに追加"
            AppLanguage.CHINESE -> "添加到Google日历"
        }
    }

    fun getScheduleLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Jadwal Keberangkatan"
            AppLanguage.ENGLISH -> "Departure Schedule"
            AppLanguage.TURKISH -> "Kalkış Saatleri"
            AppLanguage.RUSSIAN -> "Расписание рейсов"
            AppLanguage.JAPANESE -> "運行・出発時刻"
            AppLanguage.CHINESE -> "发车/起航时刻表"
        }
    }

    fun getFareLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Tarif Tiket"
            AppLanguage.ENGLISH -> "Fare / Ticket Price"
            AppLanguage.TURKISH -> "Bilet Ücreti"
            AppLanguage.RUSSIAN -> "Стоимость билета"
            AppLanguage.JAPANESE -> "運賃・チケット料金"
            AppLanguage.CHINESE -> "票价资费"
        }
    }

    fun getBookingAgencyLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Agen Pemesanan"
            AppLanguage.ENGLISH -> "Booking Agency"
            AppLanguage.TURKISH -> "Rezervasyon Acentesi"
            AppLanguage.RUSSIAN -> "Агентство бронирования"
            AppLanguage.JAPANESE -> "予約・運行会社"
            AppLanguage.CHINESE -> "售票与代理机构"
        }
    }

    fun getAgencyContactLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Kontak Agen"
            AppLanguage.ENGLISH -> "Agency Contact"
            AppLanguage.TURKISH -> "Acente İletişim"
            AppLanguage.RUSSIAN -> "Контакты агентства"
            AppLanguage.JAPANESE -> "お問い合わせ"
            AppLanguage.CHINESE -> "代理商联系方式"
        }
    }

    fun getSearchPlaceholder(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Cari hotel, wisata, kuliner, rental..."
            AppLanguage.ENGLISH -> "Search hotels, tours, food, rental..."
            AppLanguage.TURKISH -> "Otel, gezi, yemek, araç ara..."
            AppLanguage.RUSSIAN -> "Поиск отелей, туров, еды..."
            AppLanguage.JAPANESE -> "ホテル、観光、グルメ、レンタカーを検索..."
            AppLanguage.CHINESE -> "搜索酒店、景点、美食、租车..."
        }
    }

    fun getGoogleBusinessText(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Google Bisnis"
            AppLanguage.ENGLISH -> "Google Business"
            AppLanguage.TURKISH -> "Google İşletme"
            AppLanguage.RUSSIAN -> "Google Бизнес"
            AppLanguage.JAPANESE -> "Googleビジネス"
            AppLanguage.CHINESE -> "Google商家"
        }
    }

    fun getGoogleMapsText(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Petunjuk Arah (Maps)"
            AppLanguage.ENGLISH -> "Directions (Maps)"
            AppLanguage.TURKISH -> "Yol Tarifi (Harita)"
            AppLanguage.RUSSIAN -> "Маршрут (Карты)"
            AppLanguage.JAPANESE -> "道順 (Googleマップ)"
            AppLanguage.CHINESE -> "导航路线 (地图)"
        }
    }

    fun getCallButtonText(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Telepon"
            AppLanguage.ENGLISH -> "Call"
            AppLanguage.TURKISH -> "Ara"
            AppLanguage.RUSSIAN -> "Позвонить"
            AppLanguage.JAPANESE -> "電話する"
            AppLanguage.CHINESE -> "拨打电话"
        }
    }

    fun getWhatsAppText(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Chat WhatsApp"
            AppLanguage.ENGLISH -> "Chat WhatsApp"
            AppLanguage.TURKISH -> "WhatsApp İletişim"
            AppLanguage.RUSSIAN -> "Написать в WhatsApp"
            AppLanguage.JAPANESE -> "WhatsAppで連絡"
            AppLanguage.CHINESE -> "WhatsApp联系"
        }
    }

    fun getFacilitiesLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Fasilitas"
            AppLanguage.ENGLISH -> "Facilities & Amenities"
            AppLanguage.TURKISH -> "Tesis Özellikleri"
            AppLanguage.RUSSIAN -> "Удобства"
            AppLanguage.JAPANESE -> "設備・アメニティ"
            AppLanguage.CHINESE -> "设施与服务"
        }
    }

    fun getSpecialtiesLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Menu / Produk Khas"
            AppLanguage.ENGLISH -> "Specialties & Menu"
            AppLanguage.TURKISH -> "Özel Menü / Ürünler"
            AppLanguage.RUSSIAN -> "Фирменные блюда / товары"
            AppLanguage.JAPANESE -> "名物・おすすめ"
            AppLanguage.CHINESE -> "特色美食 / 特产"
        }
    }

    fun getVehiclesLabel(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Pilihan Kendaraan & Unit"
            AppLanguage.ENGLISH -> "Available Fleet & Vehicles"
            AppLanguage.TURKISH -> "Mevcut Araçlar"
            AppLanguage.RUSSIAN -> "Доступный транспорт"
            AppLanguage.JAPANESE -> "利用可能な車両"
            AppLanguage.CHINESE -> "可用车型"
        }
    }

    fun getEmergencyTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Kontak Darurat Daerah"
            AppLanguage.ENGLISH -> "Regional Emergency Contacts"
            AppLanguage.TURKISH -> "Bölgesel Acil Durum İletişimi"
            AppLanguage.RUSSIAN -> "Экстренные службы региона"
            AppLanguage.JAPANESE -> "地域の緊急連絡先"
            AppLanguage.CHINESE -> "当地紧急求助电话"
        }
    }

    fun getTranslatorTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.INDONESIA -> "Penerjemah Bahasa Daerah"
            AppLanguage.ENGLISH -> "Local Dialect Translator"
            AppLanguage.TURKISH -> "Yerel Lehçe Çevirmeni"
            AppLanguage.RUSSIAN -> "Переводчик местных языков"
            AppLanguage.JAPANESE -> "地方方言・言語翻訳"
            AppLanguage.CHINESE -> "当地土话/方言翻译器"
        }
    }

    fun getNavExplore(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Beranda"
        AppLanguage.ENGLISH -> "Home"
        AppLanguage.TURKISH -> "Ana Sayfa"
        AppLanguage.RUSSIAN -> "Главная"
        AppLanguage.JAPANESE -> "ホーム"
        AppLanguage.CHINESE -> "首页"
    }

    fun getNavTransport(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Transportasi"
        AppLanguage.ENGLISH -> "Transport"
        AppLanguage.TURKISH -> "Ulaşım"
        AppLanguage.RUSSIAN -> "Транспорт"
        AppLanguage.JAPANESE -> "交通機関"
        AppLanguage.CHINESE -> "交通出行"
    }

    fun getNavAgenda(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Agenda"
        AppLanguage.ENGLISH -> "Events"
        AppLanguage.TURKISH -> "Takvim"
        AppLanguage.RUSSIAN -> "События"
        AppLanguage.JAPANESE -> "イベント"
        AppLanguage.CHINESE -> "活动日程"
    }

    fun getNavMap(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Peta Daerah"
        AppLanguage.ENGLISH -> "Regional Map"
        AppLanguage.TURKISH -> "Bölge Haritası"
        AppLanguage.RUSSIAN -> "Карта"
        AppLanguage.JAPANESE -> "周辺地図"
        AppLanguage.CHINESE -> "区域地图"
    }

    fun getNavTranslator(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Bahasa Daerah"
        AppLanguage.ENGLISH -> "Dialect"
        AppLanguage.TURKISH -> "Yerel Dil"
        AppLanguage.RUSSIAN -> "Диалект"
        AppLanguage.JAPANESE -> "方言"
        AppLanguage.CHINESE -> "方言"
    }

    fun getNavEmergency(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Darurat"
        AppLanguage.ENGLISH -> "Emergency"
        AppLanguage.TURKISH -> "Acil"
        AppLanguage.RUSSIAN -> "SOS"
        AppLanguage.JAPANESE -> "緊急"
        AppLanguage.CHINESE -> "紧急"
    }

    fun getNavSaved(lang: AppLanguage): String = when (lang) {
        AppLanguage.INDONESIA -> "Simpanan"
        AppLanguage.ENGLISH -> "Saved"
        AppLanguage.TURKISH -> "Kaydedilen"
        AppLanguage.RUSSIAN -> "Избранное"
        AppLanguage.JAPANESE -> "保存済み"
        AppLanguage.CHINESE -> "收藏"
    }
}
