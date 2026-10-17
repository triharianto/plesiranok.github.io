package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.datasource.PlesiranDataSource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class PlesiranRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun saveFavorite_authenticatedUser_success() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val repository = PlesiranRepository(firestore = firestore)
        val place = PlesiranDataSource.places.first()

        withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveCloudFavorite(aliceUid, place)
        }

        val favorites = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.getCloudFavorites(aliceUid)
        }

        assertTrue("Favorites should not be empty", favorites.isNotEmpty())
        assertTrue("Favorites should contain place id", favorites.any { it["placeId"] == place.id })
    }

    @Test
    fun deleteFavorite_authenticatedUser_success() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val repository = PlesiranRepository(firestore = firestore)
        val place = PlesiranDataSource.places.first()

        withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.saveCloudFavorite(aliceUid, place)
            repository.deleteCloudFavorite(aliceUid, place.id)
        }

        val favorites = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.getCloudFavorites(aliceUid)
        }

        assertFalse("Favorites should not contain deleted place id", favorites.any { it["placeId"] == place.id })
    }

    @Test
    fun crossUserAccess_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val aliceRepo = PlesiranRepository(firestore = firestore)
        val place = PlesiranDataSource.places.first()

        withTimeout(DEFAULT_TIMEOUT_MS) {
            aliceRepo.saveCloudFavorite(aliceUid, place)
        }

        // Now Bob signs in and attempts to access Alice's favorites
        signInTestUser(BOB_EMAIL)
        val bobRepo = PlesiranRepository(firestore = firestore)

        try {
            withTimeout(DEFAULT_TIMEOUT_MS) {
                bobRepo.getCloudFavorites(aliceUid)
            }
            fail("Expected FirebaseFirestoreException but call succeeded")
        } catch (e: FirebaseFirestoreException) {
            assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, e.code)
        }
    }

    @Test
    fun unauthenticatedAccess_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val aliceRepo = PlesiranRepository(firestore = firestore)
        val place = PlesiranDataSource.places.first()

        withTimeout(DEFAULT_TIMEOUT_MS) {
            aliceRepo.saveCloudFavorite(aliceUid, place)
        }

        // Simulate unauthenticated user
        auth.signOut()
        val unauthRepo = PlesiranRepository(firestore = firestore)

        try {
            withTimeout(DEFAULT_TIMEOUT_MS) {
                unauthRepo.getCloudFavorites(aliceUid)
            }
            fail("Expected FirebaseFirestoreException but call succeeded")
        } catch (e: FirebaseFirestoreException) {
            assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, e.code)
        }
    }

    private companion object {
        const val ALICE_EMAIL = "alice@test.com"
        const val BOB_EMAIL = "bob@test.com"
        const val DEFAULT_TIMEOUT_MS = 5000L
    }
}
