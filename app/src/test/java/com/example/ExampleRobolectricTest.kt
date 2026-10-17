package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.datasource.PlesiranDataSource
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.data.model.PlaceCategory
import com.example.data.model.TransportCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PLESIRAN", appName)
    }

    @Test
    fun `verify destination regions exist`() {
        assertEquals(5, DestinationRegion.entries.size)
        assertNotNull(DestinationRegion.fromId("jogja"))
        assertNotNull(DestinationRegion.fromId("malang"))
        assertNotNull(DestinationRegion.fromId("bandung"))
        assertNotNull(DestinationRegion.fromId("denpasar"))
        assertNotNull(DestinationRegion.fromId("medan"))
    }

    @Test
    fun `verify places data contains hotels and tourism`() {
        val places = PlesiranDataSource.places
        assertTrue(places.isNotEmpty())
        assertTrue(places.any { it.category == PlaceCategory.HOTEL })
        assertTrue(places.any { it.category == PlaceCategory.TOURISM })
        assertTrue(places.any { it.category == PlaceCategory.TRANSPORT })
        assertTrue(places.any { it.category == PlaceCategory.SOUVENIR })
        assertTrue(places.any { it.category == PlaceCategory.CULINARY })
    }

    @Test
    fun `verify transportation includes land, sea, and air`() {
        val transports = PlesiranDataSource.transportItems
        assertTrue(transports.isNotEmpty())
        assertTrue(transports.any { it.category == TransportCategory.LAND && it.subType == "Bis" })
        assertTrue(transports.any { it.category == TransportCategory.LAND && it.subType == "Kereta Api" })
        assertTrue(transports.any { it.category == TransportCategory.LAND && it.subType == "Travel" })
        assertTrue(transports.any { it.category == TransportCategory.SEA })
        assertTrue(transports.any { it.category == TransportCategory.AIR })

        // Check that each transport item has departure schedule, fare, agency, and contacts
        transports.forEach { item ->
            assertTrue(item.departureSchedule.isNotBlank())
            assertTrue(item.fareTag.isNotBlank())
            assertTrue(item.agencyName.isNotBlank())
            assertTrue(item.agencyContact.isNotBlank())
            assertTrue(item.googleBusinessQuery.isNotBlank())
        }
    }

    @Test
    fun `verify tourism agendas contain Google Calendar event info`() {
        val agendas = PlesiranDataSource.tourismAgendas
        assertTrue(agendas.isNotEmpty())
        agendas.forEach { agenda ->
            assertTrue(agenda.title.isNotBlank())
            assertTrue(agenda.location.isNotBlank())
            assertTrue(agenda.startYear >= 2026)
            assertTrue(agenda.startMonth in 1..12)
            assertTrue(agenda.startDay in 1..31)
        }
    }

    @Test
    fun `verify welcome translations across all languages`() {
        AppLanguage.entries.forEach { lang ->
            val title = AppStrings.getWelcomeTitle(lang)
            assertTrue(title.isNotBlank())
            val navHome = AppStrings.getNavExplore(lang)
            assertTrue(navHome.isNotBlank())
        }
        assertEquals("Selamat Berwisata di Indonesia", AppStrings.getWelcomeTitle(AppLanguage.INDONESIA))
        assertEquals("Welcome & Enjoy Traveling in Indonesia", AppStrings.getWelcomeTitle(AppLanguage.ENGLISH))
        assertEquals("Endonezya'da Harika Bir Seyahat Dileriz", AppStrings.getWelcomeTitle(AppLanguage.TURKISH))
    }

    @Test
    fun `verify emergency contacts available for all regions`() {
        val emergency = PlesiranDataSource.emergencyContacts
        assertTrue(emergency.isNotEmpty())
        DestinationRegion.entries.forEach { region ->
            assertTrue("Region ${region.displayName} must have emergency contacts", emergency.any { it.region == region })
        }
    }
}
