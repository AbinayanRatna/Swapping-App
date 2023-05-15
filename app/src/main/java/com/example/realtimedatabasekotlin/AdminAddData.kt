package com.example.realtimedatabasekotlin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.realtimedatabasekotlin.databinding.ActivityUserAddDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdminAddData : AppCompatActivity() {


    private lateinit var binding: ActivityUserAddDetailsBinding
    private lateinit var database: DatabaseReference
    private var nameButton: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserAddDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelBtn.setOnClickListener {
            val intent = Intent(this, AdminViewCategory::class.java)
            startActivity(intent)

        }
        binding.deviceDot.setOnClickListener {
            nameButton = binding.deviceDot.text.toString()
        }
        binding.serviceDot.setOnClickListener {
            nameButton = binding.serviceDot.text.toString()
        }
        binding.vehicleDot.setOnClickListener {
            nameButton = binding.vehicleDot.text.toString()
        }
        binding.propertyDot.setOnClickListener {
            nameButton = binding.propertyDot.text.toString()
        }
        binding.sportsDot.setOnClickListener {
            nameButton = binding.deviceDot.text.toString()
        }
        binding.fashioDot.setOnClickListener {
            nameButton = binding.fashioDot.text.toString()
        }


        binding.uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        binding.addBtn.setOnClickListener {
            adddata()
        }


    }


    var selectedImageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imgDoctor.setBackgroundDrawable(bitmapDrawable)

        }
    }

    private fun adddata() {

        val productName = binding.nameTxt.text.trim().toString()
        Log.i("Firebase image URL", "date1")
        val price = binding.priceTxt.text.trim().toString() + ".Rs"
        Log.i("Firebase image URL", "date2")
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = current.format(formatter)
        Log.i("Firebase image URL", "date3")
        val address = binding.addressTxt.text.trim().toString()
        Log.i("Firebase image URL", "date4")
        var imgIdNew: String = ""
        Log.i("Firebase image URL", "date5")

        when (nameButton) {
            "Vehicle" -> {
                database = FirebaseDatabase.getInstance().getReference("Vehicle")
            }
            "Property" -> {
                database = FirebaseDatabase.getInstance().getReference("Property")
            }
            "Electronic" -> {
                database = FirebaseDatabase.getInstance().getReference("Electronic")
            }
            "Fashion" -> {
                database = FirebaseDatabase.getInstance().getReference("Fashion")
            }
            "Sport" -> {
                database = FirebaseDatabase.getInstance().getReference("Sport")
            }
            "Service" -> {
                database = FirebaseDatabase.getInstance().getReference("Service")
            }
            else -> {
                database = FirebaseDatabase.getInstance().getReference("else")
            }
        }
        Log.i("Firebase image URL", "dates7")
        val empId = database.push().key!!
        Log.i("Firebase image URL", "date8")

        if (selectedImageUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
        ref.putFile(selectedImageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                Toast.makeText(this, "Wait.Saving Process will take some time.", Toast.LENGTH_SHORT)
                    .show()
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        imgIdNew = uri.toString()
                        Log.i("Firebase image URL", "$imgIdNew")
                        val product =
                            productClass(empId, imgIdNew, productName, price, address, date)
                        database.child(empId).setValue(product).addOnSuccessListener {


                            val intent = Intent(this@AdminAddData, AdminViewCategory::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {

                            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()


                        }
                    }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to atttch image to database", Toast.LENGTH_SHORT)
                    .show()
            }
        Log.i("Firebase image URL", "$imgIdNew")


    }


}
