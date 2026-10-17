package com.example.data.repository

import android.util.Log
import com.example.data.datasource.PlesiranDataSource
import com.example.data.firestore.OperationType
import com.example.data.firestore.handleFirestoreError
import com.example.data.local.FavoritePlaceDao
import com.example.data.local.FavoritePlaceEntity
import com.example.data.model.DestinationRegion
import com.example.data.model.EmergencyContact
import com.example.data.model.LocalPhrase
import com.example.data.model.PlaceCategory
import com.example.data.model.PlaceItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.await

class PlesiranRepository(
    private val favoritePlaceDao: FavoritePlaceDao? = null,
    private val firestore: FirebaseFirestore? = null
) {
    fun getPlaces(region: DestinationRegion?, category: PlaceCategory? = null, searchQuery: String = ""): List<PlaceItem> {
        return PlesiranDataSource.places.filter { item ->
            val matchesRegion = region == null || item.region == region
            val matchesCategory = category == null || item.category == category
            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.address.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true) ||
                item.specialties.any { it.contains(searchQuery, ignoreCase = true) } ||
                item.facilities.any { it.contains(searchQuery, ignoreCase = true) }
            }
            matchesRegion && matchesCategory && matchesSearch
        }
    }

    fun getPlaceById(placeId: String): PlaceItem? {
        return PlesiranDataSource.places.find { it.id == placeId }
    }

    fun getEmergencyContacts(region: DestinationRegion?): List<EmergencyContact> {
        return if (region == null) {
            PlesiranDataSource.emergencyContacts
        } else {
            PlesiranDataSource.emergencyContacts.filter { it.region == region }
        }
    }

    fun getLocalPhrases(region: DestinationRegion?, categoryName: String? = null): List<LocalPhrase> {
        val targetRegion = region ?: DestinationRegion.JOGJA
        return PlesiranDataSource.localPhrases.filter {
            it.region == targetRegion && (categoryName == null || it.categoryName == categoryName)
        }
    }

    fun getTransportItems(region: DestinationRegion?, category: com.example.data.model.TransportCategory? = null): List<com.example.data.model.TransportItem> {
        return if (region == null) {
            PlesiranDataSource.transportItems.filter { category == null || it.category == category }
        } else {
            PlesiranDataSource.transportItems.filter {
                it.region == region && (category == null || it.category == category)
            }
        }
    }

    fun getTourismAgendas(region: DestinationRegion?): List<com.example.data.model.TourismAgenda> {
        return if (region == null) {
            PlesiranDataSource.tourismAgendas
        } else {
            PlesiranDataSource.tourismAgendas.filter {
                it.region == region
            }
        }
    }

    // Local Room Favorites & Cloud Firestore Synchronization
    fun getAllFavorites(): Flow<List<FavoritePlaceEntity>> =
        favoritePlaceDao?.getAllFavorites() ?: emptyFlow()

    suspend fun saveCloudFavorite(userId: String, place: PlaceItem) {
        val db = firestore ?: return
        val path = "users/$userId/favorites/${place.id}"
        val data = hashMapOf(
            "placeId" to place.id,
            "name" to place.name,
            "category" to place.category.id,
            "region" to place.region.id,
            "address" to place.address,
            "rating" to place.rating,
            "priceTag" to place.priceTag,
            "contactNumber" to place.contactNumber,
            "savedAt" to FieldValue.serverTimestamp()
        )
        try {
            db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(place.id)
                .set(data)
                .await()
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, path)
            throw e
        }
    }

    suspend fun deleteCloudFavorite(userId: String, placeId: String) {
        val db = firestore ?: return
        val path = "users/$userId/favorites/$placeId"
        try {
            db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(placeId)
                .delete()
                .await()
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, path)
            throw e
        }
    }

    suspend fun getCloudFavorites(userId: String): List<Map<String, Any>> {
        val db = firestore ?: return emptyList()
        val path = "users/$userId/favorites"
        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()
            snapshot.documents.mapNotNull { it.data }
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.LIST, path)
            throw e
        }
    }

    suspend fun toggleFavorite(place: PlaceItem, isCurrentlyFavorite: Boolean) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (isCurrentlyFavorite) {
            favoritePlaceDao?.deleteFavoriteById(place.id)
            if (uid != null && firestore != null) {
                try {
                    deleteCloudFavorite(uid, place.id)
                } catch (e: Exception) {
                    Log.w("PlesiranRepository", "Failed to delete cloud favorite", e)
                }
            }
        } else {
            favoritePlaceDao?.insertFavorite(
                FavoritePlaceEntity(
                    placeId = place.id,
                    regionId = place.region.id,
                    categoryId = place.category.id,
                    name = place.name,
                    address = place.address,
                    rating = place.rating,
                    priceTag = place.priceTag,
                    contactNumber = place.contactNumber
                )
            )

            if (uid != null && firestore != null) {
                try {
                    saveCloudFavorite(uid, place)
                } catch (e: Exception) {
                    Log.w("PlesiranRepository", "Failed to sync cloud favorite", e)
                }
            }
        }
    }
}
