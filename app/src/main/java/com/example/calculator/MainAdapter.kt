package com.example.calculator

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Math.random
import java.util.*

internal class MainAdapter(
    private val context: Context,
    private val imageUri: ArrayList<Uri>
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    private val random = random()


    override fun getCount(): Int {
        return imageUri.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.rowitem, null)
        }
        imageView = convertView!!.findViewById(R.id.imageView)

        imageView.setImageURI(imageUri[position])


//        uploadImageToFirebase(imageUri[position])
        return convertView
    }

    private fun uploadImageToFirebase(filePath: Uri) {

//        val formatter = SimpleDateFormat("yyyy_mm_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formatter.format(now)

        val refStorage = FirebaseStorage.getInstance().reference.child("images/$random")

        refStorage.putFile(filePath)
            .addOnSuccessListener {
                Toast.makeText(context, "Successed...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                print(e.message)
            }
    }

}
