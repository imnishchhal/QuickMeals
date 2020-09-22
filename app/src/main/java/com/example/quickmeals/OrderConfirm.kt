package com.example.quickmeals

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_confirm.*

class OrderConfirm : AppCompatActivity() {
    val db = Firebase.firestore
    var listOfResto: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        readData() {
            Log.d("impi", it.size.toString() + it.toString())
            val adapter = MyAdapter(this, it)
            recyclerView.adapter = adapter
        }
    }

    fun readData(myCallback: (List<String>) -> Unit) {
        db.collection("resto")
            .whereArrayContains("menu", intent.getStringExtra("cl") as String)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listOfResto.add(document.id)
                }
                myCallback(listOfResto)
            }
            .addOnFailureListener { exception ->
                Log.w("findxe", "Error getting documents: ", exception)
            }
    }
}
