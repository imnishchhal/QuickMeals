package com.example.quickmeals

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(val context: Context, val rsi: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        Log.d("impx", "rsi txt is:" + rsi)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("impx", "rsi (position txt is:" + rsi[position])
        holder.setData(rsi[position])
    }

    override fun getItemCount(): Int {
        Log.d("impx", "rsi.size is:" + rsi.size)
        return rsi.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(ite: String){
            itemView.txvTitle.text = ite
            Log.d("impx", "ite txt is:" + ite)
            itemView.setOnClickListener {
                val intenti = Intent(context, SelectMenuItem::class.java)
                intenti.putExtra("cliked", ite)
                context.startActivity(intenti)
            }
        }
    }

}