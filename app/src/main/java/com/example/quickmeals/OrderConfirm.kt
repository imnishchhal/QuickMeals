package com.example.quickmeals

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_confirm.*

class OrderConfirm : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        var rName: String = intent.getStringExtra("cl") as String
        val adapter = MyAdapter(this, qst(rName))
        recyclerView.adapter = adapter
    }
    fun qst(s: String): MutableList<String> {
        var listOfResto: MutableList<String> = mutableListOf<String>()
        db.collection("resto")
            .whereArrayContains("menu", s)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listOfResto.add(document.id)
                    Log.d("findx", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("findxe", "Error getting documents: ", exception)
            }
        return listOfResto
    }
}
