package com.example.calculator


import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.random
import kotlin.properties.Delegates


class Hidden : AppCompatActivity() {

    private val tempImages: ArrayList<Uri> = arrayListOf()
    private val hereImages: ArrayList<Bitmap> = arrayListOf()
    private lateinit var gvGallery: GridView
    var posi: Int = 0
    private lateinit var preference: SharedPreferences

    companion object {
        val IMAGE_REQUEST_CODE = 1
        val PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hidden)

        preference = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE)


        val button = findViewById<Button?>(R.id.GetImage)
        gvGallery = findViewById(R.id.gv)
        gvGallery.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val message = (tempImages[position])
            preference.edit().putString("position",position.toString()).apply()
            val intent = Intent(this, ShowImageActivity::class.java)
            intent.putExtra("Image_Uri", message.toString())
            startActivity(intent)
        }
        button.setOnClickListener {
            pickImageGallery()
        }
        if(preference.getBoolean( Dlt,false)){
            val direct = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "/DirName/CalCyData/Cache/.data/HideOut/"
            )
            posi = preference.getString("position","")?.toInt()!!
            val files: Array<File> = direct.listFiles()
            if (files[posi].exists()) {
                files[posi].delete()
            }
            preference.edit().putBoolean( Dlt,false).apply()
        }
    }

    override fun onResume() {
        super.onResume()
        showFromHide()
    }

//    override fun onStop() {
//        super.onStop()
//        finish()
//    }

    private fun pickImageGallery() {
        if (grantpermission()) {
//    For Storing Single Image
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, IMAGE_REQUEST_CODE)

//    For Storing multiple Images
            // For latest versions API LEVEL 19+
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        } else askpermission()
    }

    override fun onBackPressed() {
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//     Selecting Images from Storage
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            hereImages.clear()
            if (data?.clipData != null) {
                // if multiple images are selected
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val filePath: Uri = data.clipData?.getItemAt(i)?.uri!!
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(this.contentResolver, filePath)
                    val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)
                    hereImages.add(bitmap)
                }
            } else {
                // if single image is selected
                val filePath: Uri = data?.data!!
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, filePath)
                val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)
                hereImages.add(bitmap)
            }
            createDirectoryAndSaveFile(hereImages)
        }
    }

    //  Storing Images in local Storage
    private fun createDirectoryAndSaveFile(imageToSave: ArrayList<Bitmap>) {
        val direct = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "/DirName/CalCyData/Cache/.data/HideOut"
        )
        if (!direct.exists()) {
            direct.mkdirs()
        }
        for (i in 0 until imageToSave.size) {
            val fname = random().toString()
            val file = File(direct.toString(), fname)
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            try {
                val out = FileOutputStream(file)
                imageToSave[i].compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //    Showing Images From Folder
    private fun showFromHide() {
        tempImages.clear()
        val direct = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "/DirName/CalCyData/Cache/.data/HideOut/"
        )
        if (!direct.exists()) {
            direct.mkdirs()
        }
        val files: Array<File> = direct.listFiles()
        for (i in files.indices) {
            val imagepath = files[i].toUri()
            tempImages.add(imagepath)
        }
        val mainAdapter = MainAdapter(this@Hidden, tempImages)
        gvGallery.adapter = mainAdapter
    }


    private fun askpermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "Write External Storage permission allows us to save files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf((android.Manifest.permission.WRITE_EXTERNAL_STORAGE)),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun grantpermission(): Boolean {
        val result: Int = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("value", "Permission Granted, Now you can use local drive .")
        } else {
            Log.e("value", "Permission Denied, You cannot use local drive .")
        }
    }
}
