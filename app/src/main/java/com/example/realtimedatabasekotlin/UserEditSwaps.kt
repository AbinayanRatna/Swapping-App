package com.example.realtimedatabasekotlin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.realtimedatabasekotlin.databinding.ActivityUserEditBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UserEditSwaps : AppCompatActivity() {
    private lateinit var binding: ActivityUserEditBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swapHeadingTV.text = "Edit Swap"
        binding.cancelBtn.text = "Delete"
        binding.swapBtn.text = "Update"
        binding.imagechangeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val swapId = intent.getStringExtra("id")
        setValuesToViews()
        binding.swapBtn.setOnClickListener {
            dataadding()

        }
        binding.cancelBtn.setOnClickListener {

            database = FirebaseDatabase.getInstance().getReference("swap")
            database.child(swapId!!).get().addOnSuccessListener {


                database.child(swapId).removeValue().addOnSuccessListener {
                    Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                    binding.nameTxt.text = null
                    binding.addressTxt.text = null
                    binding.pricext.text = null
                    binding.imgDoctor.setImageResource(R.drawable.ic_car)
                }.addOnFailureListener {
                    Toast.makeText(this, "Errors in deleting the data.", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            val intent = Intent(this, UserViewCategory::class.java)
            startActivity(intent)
            finish()

        }
    }

    var selectedImageUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            binding.imgDoctor.setImageResource(0)
            selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imgDoctor.setBackgroundDrawable(bitmapDrawable)

        }
    }

    private fun setValuesToViews() {
        val empID = intent.getStringExtra("id")
        binding.nameTxt.setText(intent.getStringExtra("name"))
        binding.addressTxt.setText(intent.getStringExtra("districtOfProduct"))
        binding.pricext.setText(intent.getStringExtra("priceOfProduct"))
        binding.nameView.setText(intent.getStringExtra("swapProductName"))
        binding.priceView.setText(intent.getStringExtra("swapProductPrice"))
        binding.addressView.setText(intent.getStringExtra("swapProductAddress"))
        val productImageUri = intent.getStringExtra("productimage")
        val swapImage = intent.getStringExtra("image")

        Glide.with(this)
            .load(productImageUri)
            .into(binding.productImage)
        Glide.with(this)
            .load(swapImage)
            .into(binding.imgDoctor)

    }

    private fun dataadding() {
        val swapImage = intent.getStringExtra("image")
        val swapId = intent.getStringExtra("id")
        val productImage = intent.getStringExtra("productimage")
        val name = binding.nameTxt.text.toString()
        val price = binding.pricext.text.toString()
        val address = binding.addressTxt.text.toString()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = current.format(formatter)

        if (swapId != null) {
            if (selectedImageUri != null) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
            ref.putFile(selectedImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Photo saved", Toast.LENGTH_SHORT).show()
                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            val image = uri.toString()
                            updateData(swapId, name, price, address, date, image, productImage!!)
                            val intent = Intent(this, UserViewCategory::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Swap Successfully Updated", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
        }else{
                updateData(
                    swapId,
                    name,
                    price,
                    address,
                    date,
                    swapImage!!,
                    productImage!!
                )
                val intent = Intent(this, UserViewCategory::class.java)
                startActivity(intent)
                Toast.makeText(
                    this,
                    "Swap Successfully Updated",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }}
    }

    private fun updateData(
        swapid: String,
        name: String,
        Price: String,
        address: String,
        date: String,
        image: String,
        productImage: String
    ) {

        database = FirebaseDatabase.getInstance().getReference("swap")
        val user = mapOf<String, String>(
            "swapId" to swapid,
            "nameOfProduct" to name,
            "priceOfProduct" to Price,
            "districtOfProduct" to address,
            "dateOfProduct" to date,
            "image" to image,
            "productImage" to productImage
        )

        database.child(swapid).updateChildren(user).addOnSuccessListener {


        }.addOnFailureListener {

            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }
}