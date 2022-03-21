package com.example.calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

import com.google.android.material.textfield.TextInputEditText


class HiddenCreateFragment : Fragment() {

    private lateinit var share: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_hidden_create, container, false)
        share = this.requireActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        val password = view.findViewById<TextInputEditText>(R.id.HideCreatePass)
        val create = view.findViewById<Button>(R.id.Create)
        val key = view.findViewById<EditText>(R.id.Key)


        create.setOnClickListener {
            var temp = password.text.toString()
            share.edit().putString(Password, temp).apply()
            temp = key.text.toString()
            share.edit().putString(Key,temp).apply()
            val intent = Intent (activity, Hidden::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }

        share.edit().putBoolean(MainEntry,true).apply()
        return view
    }

}
