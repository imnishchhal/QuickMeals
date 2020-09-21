package com.example.quickmeals

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Address() : AppCompatActivity(){
    var a = ""
    private lateinit var mAuth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    private val db = Firebase.firestore
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        val btnList = findViewById<Button>(R.id.addSave) as Button
        val addOnApp = findViewById<TextView>(R.id.adxml)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    val gps = DoubleArray(2)
                    val addg: List<Address>?
                    val geocoder = Geocoder(applicationContext, Locale.getDefault())
                    if (location != null) {
                        gps[0] = location.getLatitude()
                        gps[1] = location.getLongitude()
                        addg = geocoder.getFromLocation(gps[0], gps[1], 1)
                        if (addg != null && addg.isNotEmpty()) {
                            val addres: String = addg[0].getAddressLine(0)
                            val city: String = addg[0].locality
                            val state: String = addg[0].adminArea
                            val country: String = addg[0].countryName
                            val postalCode: String = addg[0].postalCode
                            val knownName: String = addg[0].featureName
                            val joinAdd = "$addres, $city, $state, $country, $postalCode, $knownName" as String
                            addOnApp.text = joinAdd
                            btnList.setOnClickListener{
                                saveadd(joinAdd,
                                    findViewById<EditText>(R.id.addtext3).text.toString(),
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    findViewById<EditText>(R.id.mobileNum).text.toString())
                            }
                        }
                    }
                }
        }
    }

    public final fun saveadd(a: String, b: String, p1: Double, p2: Double, m: String){
        mAuth = FirebaseAuth.getInstance()
        val d: String = a + ", " + b
        val currentUser = mAuth.currentUser

        val usr = db.collection("users")
        val data1 = hashMapOf(
            "addressGeoPoint" to GeoPoint(p1, p2),
            "name" to currentUser?.displayName,
            "email" to currentUser?.email,
            "mobile" to m,
            "address" to d
        )
        if (currentUser != null) {
            currentUser.email?.let {
                db.collection("users").document(it)
                    .set(data1)
                    .addOnSuccessListener {
                        Log.d("Address", "DocumentSnapshot successfully written!")
                        val intent = Intent(this, HomeMain::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e -> Log.w("Address", "Error:", e) }
            }
        }

    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@Address,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_FINE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {

        Toast.makeText(this@Address, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }

    companion object {
        val TAG = "LocationProvider"
        val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}