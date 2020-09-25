package com.example.quickmeals

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.collections.set

class SelectMenuItem : AppCompatActivity() {
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    val menuLast = HashMap<String, List<String>>()
    val db = Firebase.firestore
    var listOfDish: MutableList<String> = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_menu_item)
        title = "Add To Your Cart"
        expandableListView = findViewById(R.id.expendableList)
        val shopi = intent.getStringExtra("cliked") as String
        val gmenu = db.collection("resto").document(shopi)
        val source = Source.CACHE
        gmenu.get(source).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                val d = task.result
                listOfDish = d?.get("menu") as MutableList<String>
                for (ld in listOfDish){
                    db.collection("resto/$shopi/$ld")
                        .get()
                        .addOnSuccessListener {
                            val mutMlist = mutableListOf<String>()
                            for (docu in it){
                                mutMlist.add(docu.id)
                            }
                            menuLast.put(ld,mutMlist)
                            if (expandableListView != null) {
                                titleList = ArrayList(menuLast.keys)
                                adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, menuLast)
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
                                        "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + menuLast[(
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
                            }
                        }
                }

            }else{
                Log.d("error", "data not found")
            }
        }
    }
}