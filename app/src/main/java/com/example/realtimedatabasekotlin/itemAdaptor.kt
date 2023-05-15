package com.example.realtimedatabasekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*


class itemAdaptor(
    private val doctorList: ArrayList<productClass>,
    private val context: Context
) :
    RecyclerView.Adapter<itemAdaptor.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent, false
        )
        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = doctorList[position]

        holder.name.text = currentitem.nameOfProduct
        holder.address.text = currentitem.districtOfProduct
        holder.date.text = currentitem.dateOfProduct
        holder.price.text = currentitem.priceOfProduct
        Glide.with(context)
            .load(doctorList[position].image)
            .into(holder.imageDoctor)
        // holder.image.setImageResource(currentitem.image(context)


    }

    override fun getItemCount(): Int {

        return doctorList.size
    }


    class MyViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name_product)
        val address: TextView = itemView.findViewById(R.id.name_Address)
        val date: TextView = itemView.findViewById(R.id.name_Date)
        val price: TextView = itemView.findViewById(R.id.name_Price)
        val imageDoctor = itemView.findViewById<ImageView>(R.id.image_product)

        //val image:ShapeableImageView=itemView.findViewById(R.id.image_doctor)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}