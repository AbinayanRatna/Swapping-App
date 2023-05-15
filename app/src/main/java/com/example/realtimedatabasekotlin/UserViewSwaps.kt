package com.example.realtimedatabasekotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.databinding.ActivityViewNewBinding
import com.google.firebase.database.*
import java.util.*

class UserViewSwaps : AppCompatActivity() {
    private lateinit var binding: ActivityViewNewBinding

    private lateinit var dbref: DatabaseReference
    private lateinit var empRecyclerview: RecyclerView
    private lateinit var empList: ArrayList<swapClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        empRecyclerview = findViewById(R.id.doctorRecycleView)
        empRecyclerview.layoutManager = GridLayoutManager(this, 2)
        empRecyclerview.setHasFixedSize(true)
        empList = arrayListOf<swapClass>()
        binding.swapHeadingTV2.text = "Your Swaps"
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
        getUserData()
    }

    private fun getUserData() {
        val nameButton = intent.getStringExtra("name")
        val imageUri = intent.getStringExtra("productImage")
        val swapName = intent.getStringExtra("swapProductName")
        val swapPrice = intent.getStringExtra("swapProductPrice")
        val swapAddress = intent.getStringExtra("swapProductAddress")
        dbref = FirebaseDatabase.getInstance().getReference("swap")

        Log.i("Firebase image URL", "date8")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Firebase image URL", "date8")
                if (snapshot.exists()) {
                    Log.i("Firebase image URL", "date8")
                    for (userSnapshot in snapshot.children) {

                        val doctor = userSnapshot.getValue(swapClass::class.java)
                        empList.add(doctor!!)

                    }
                    val mAdapter = swapItemAdaptor(empList, this@UserViewSwaps)
                    empRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object :
                        swapItemAdaptor.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent =
                                Intent(this@UserViewSwaps, UserEditSwaps::class.java)


                            intent.putExtra("name", empList[position].nameOfProduct)
                            intent.putExtra("id", empList[position].swapId)
                            intent.putExtra("priceOfProduct", empList[position].priceOfProduct)
                            intent.putExtra("swapProductName", empList[position].productName)
                            intent.putExtra("swapProductAddress", empList[position].productAddress)
                            intent.putExtra("swapProductPrice", empList[position].productPrice)

                            intent.putExtra(
                                "districtOfProduct",
                                empList[position].districtOfProduct
                            )
                            intent.putExtra("image", empList[position].image)
                            intent.putExtra("productimage", empList[position].productImage)
                            startActivity(intent)


                        }

                    })


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}