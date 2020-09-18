package com.example.quickmeals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class AddRestaurant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant)
        val rn = findViewById<EditText>(R.id.restShopName)
        val ra = findViewById<EditText>(R.id.restAddress)
        val on = findViewById<EditText>(R.id.ownerName)
        val om = findViewById<EditText>(R.id.mobile)
        val ct = findViewById<EditText>(R.id.closeTime)
        val st = findViewById<EditText>(R.id.startTime)
        val sdb = findViewById<Button>(R.id.saveDetailsBtn)

        var res = Restaurant()
        sdb.setOnClickListener{
            res.name = rn.text.toString()
            res.addressRest = ra.text.toString()
            res.ownerName = on.text.toString()
            res.ownerMobile = om.text.toString()
            res.stiming = st.text.toString()
            res.ctiming = ct.text.toString()
            res.writeOn()
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}