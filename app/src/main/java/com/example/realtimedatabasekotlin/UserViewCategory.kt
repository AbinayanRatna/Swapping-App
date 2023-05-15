package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.databinding.ActivityViewDatasBinding
import com.google.firebase.database.*
import java.util.*

class UserViewCategory : AppCompatActivity() {
    private lateinit var binding: ActivityViewDatasBinding

    private lateinit var dbref: DatabaseReference
    private lateinit var empRecyclerview: RecyclerView
    private lateinit var empList: ArrayList<categoryMain>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, UserViewSwaps::class.java)
            startActivity(intent)

        }

        binding.viewBtn.setOnClickListener {
            val intent = Intent(this, UserViewCategory::class.java)
            startActivity(intent)

        }

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        empRecyclerview = findViewById(R.id.doctorRecycleView)
        empRecyclerview.layoutManager = GridLayoutManager(this, 2)
        empRecyclerview.setHasFixedSize(true)

        empList = arrayListOf<categoryMain>()
        getUserData()
    }

    private fun getUserData() {
        empList.add(categoryMain(R.drawable.ic_car, "Vehicle"))
        empList.add(categoryMain(R.drawable.ic_electric, "Eectric Devices"))
        empList.add(categoryMain(R.drawable.ic_fashion, "Fashion and cosmetics"))
        empList.add(categoryMain(R.drawable.ic_sports, "Sports"))
        empList.add(categoryMain(R.drawable.ic_servise, "Services"))
        empList.add(categoryMain(R.drawable.ic_property, "Properties"))
        //categoryList.add(categoryClass("Properties",R.drawable.ic_property))
        // categoryList.add(categoryClass("Electronic Devices",R.drawable.ic_electronic))
        //  categoryList.add(categoryClass("Fashion and cosmetics",R.drawable.ic_fashion))
        // categoryList.add(categoryClass("Sports",R.drawable.ic_sports))
        //  categoryList.add(categoryClass("Services",R.drawable.ic_servise))
        val mAdapter = categoryAdaptor(empList, this@UserViewCategory)
        empRecyclerview.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : categoryAdaptor.onItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@UserViewCategory, UserViewItems::class.java)
                val category: String = ""
                val accessState = intent.getStringExtra("access").toString()

                intent.putExtra("name", empList[position].name)
                intent.putExtra("accessState", accessState)
                startActivity(intent)
            }

        })
    }


}

