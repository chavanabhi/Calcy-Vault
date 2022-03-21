package com.example.calculator

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.example.calculator.databinding.ActivityMainBinding

// TODO: Rename parameter arguments, choose names that match

class HiddenLoginFragment : Fragment() {

    val patternLockView: PatternLockView = view?.findViewById(R.id.patternView)!!

    private lateinit var share: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_hidden_login, container, false)
        share = this.requireActivity().getSharedPreferences("sharedPrefFile", MODE_PRIVATE)
        val enter = view.findViewById<Button>(R.id.HideEnterPass)
        val password = view.findViewById<EditText>(R.id.HidePass)
        val forget = view.findViewById<TextView>(R.id.HideForget)

        enter.setOnClickListener {
            if (share.getString(Password,"") == password.text.toString()){
                val intent = Intent (activity, Hidden::class.java)
                activity?.startActivity(intent)
                activity?.finish()
                }
            else{
                Toast.makeText(requireContext(),"Wrong Password",Toast.LENGTH_SHORT).show()
            }
        }
        forget.setOnClickListener {
            val intent = Intent (activity, ForgetActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
        return view
    }
}


