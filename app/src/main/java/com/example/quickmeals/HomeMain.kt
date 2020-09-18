package com.example.quickmeals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_home_main.*

class HomeMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        val burgertile = findViewById<CardView>(R.id.burgerTile) as CardView
        val pizzatile = findViewById<CardView>(R.id.pizzaTile) as CardView
        val chowmeintile = findViewById<CardView>(R.id.chowmeinTile) as CardView
        val dosatile = findViewById<CardView>(R.id.dosaTile) as CardView
        val samosatile = findViewById<CardView>(R.id.samosaTile) as CardView
        val pavbhajitile = findViewById<CardView>(R.id.pavBajiTile) as CardView

        burgertile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "burger")
            startActivity(intent)
        }
        pizzatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "pizza")
            startActivity(intent)
        }
        chowmeintile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "chowmein")
            startActivity(intent)
        }
        dosatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "dosa")
            startActivity(intent)
        }
        samosatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "samosa")
            startActivity(intent)
        }
        pavbhajitile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            intent.putExtra("cl", "pav bhaji")
            startActivity(intent)
        }

    }
}