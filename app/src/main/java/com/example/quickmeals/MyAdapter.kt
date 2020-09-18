package com.example.quickmeals

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmeals.Supplier.item
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(val context: Context, item: List<MenuItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ite = item[position]
        holder.setData(ite, position)
    }

    override fun getItemCount(): Int {
        return item.size
    }
    inner class MyViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        fun setData(ite: MenuItem?, pos: Int){
            itemView.txvTitle.text = ite!!.title
            itemView.setOnClickListener {
                val intenti = Intent(context, SelectMenuItem::class.java)
                context.startActivity(intenti)
            }
        }
    }

}