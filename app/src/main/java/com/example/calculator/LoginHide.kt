package com.example.calculator

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class LoginHide : AppCompatActivity() {

    private lateinit var share: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_hide)
        share = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE)

        if(share.getBoolean(MainEntry,false)){
            val loginFragment = HiddenLoginFragment()
            replaceCurrentFragment(loginFragment)
        }else{
            val createFragment = HiddenCreateFragment()
            replaceCurrentFragment(createFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
    private fun replaceCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment,fragment.toString())
            commit()
        }
}