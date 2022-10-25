package com.cs3450.dansfrappesraps.ui.repositories

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

import com.google.firebase.storage.StorageReference

object DrinkImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Uri {
        try {
            return FirebaseStorage.getInstance("gs://dansfrappesraps").reference.child("images/${imageUri.lastPathSegment}")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
        } catch (e: Exception) {
            return Uri.EMPTY
        }
    }
}