package com.example.realtimedatabasekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class categoryAdaptor(private val categoryMainList: ArrayList<categoryMain>, private val context: Context) :
    RecyclerView.Adapter<categoryAdaptor.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item,
            parent, false
        )
        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = categoryMainList[position]

        holder.name.text = currentitem.name
        holder.imageDoctor.setImageResource(currentitem.id!!)

    }

    override fun getItemCount(): Int {

        return categoryMainList.size
    }


    class MyViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.textCategory)

        val imageDoctor = itemView.findViewById<ImageView>(R.id.image_doctor)

        //val image:ShapeableImageView=itemView.findViewById(R.id.image_doctor)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}