package com.example.quickmeals

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeMain : AppCompatActivity() {
    private var va: String? = null
    fun getValue(): String? {
        return va
    }
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
            va = "burger"
            intent.putExtra("cl", "burger")
            startActivity(intent)
        }
        pizzatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            va = "pizza"
            intent.putExtra("cl", "pizza")
            startActivity(intent)
        }
        chowmeintile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            va = "chowmein"
            intent.putExtra("cl", "chowmein")
            startActivity(intent)
        }
        dosatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            va = "dosa"
            intent.putExtra("cl", "dosa")
            startActivity(intent)
        }
        samosatile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            va = "samosa"
            intent.putExtra("cl", "samosa")
            startActivity(intent)
        }
        pavbhajitile.setOnClickListener{
            val intent = Intent(this, OrderConfirm::class.java)
            va = "pavbhaji"
            intent.putExtra("cl", "pav bhaji")
            startActivity(intent)
        }

    }
}