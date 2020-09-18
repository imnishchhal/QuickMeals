package com.example.quickmeals

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Restaurant {
    private lateinit var mAuth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    private val db = Firebase.firestore
    var name: String = ""
    var ownerName: String = ""
    var ownerMobile: String = ""
    var addressRest: String = ""
    var rating: Int = 5
    var stiming: String = "1000"
    var ctiming: String = "2000"
    var commission: Int = 20
/* May needed later
    var addLat: Double = 1.0
    var addLng: Double = 1.0
    var active: Boolean = true
*/
    fun writeOn(){
    val restData = hashMapOf(
        "restName" to name,
        "active" to true,
        "address" to addressRest,
        "ownerName" to ownerName,
        "stiming" to stiming,
        "ctiming" to ctiming,
        "rating" to rating,
        "commission" to commission,
        "ownerMobile" to ownerMobile
    )
    db.collection("resto").document(name)
        .set(restData)
        .addOnSuccessListener {
            Log.d("Restaurant", "Restaurant added successfully written!")
        }
        .addOnFailureListener { e -> Log.w("Address", "Error:", e)
        }
    }
}