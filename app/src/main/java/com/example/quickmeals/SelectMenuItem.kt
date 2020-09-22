package com.example.quickmeals

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.example.quickmeals.ExpandableListData.data
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SelectMenuItem : AppCompatActivity() {
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    val db = Firebase.firestore
    var listOfDish: MutableList<String> = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_menu_item)
        title = "Add To Your Cart"
        expandableListView = findViewById(R.id.expendableList)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            readData(){
                Log.d("impiS", it.size.toString() + it.toString())
            }
        }
    }
    fun readData(myCallback: (List<String>) -> Unit) {
        val shopName = intent.getStringExtra("cliked") as String
        Log.d("impiS", "resto/$shopName")
        db.collection("resto/$shopName")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("selectMenu", document.id)
                    listOfDish.add(document.id)
                }
                myCallback(listOfDish)
            }
            .addOnFailureListener { exception ->
                Log.w("findxe", "Error getting documents: ", exception)
            }
    }
}