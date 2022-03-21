package com.example.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private lateinit var databinding: ActivityMainBinding

    private lateinit var edit: TextView
    private lateinit var edit0: TextView

    private val xtra: ArrayList<String> = arrayListOf()

    private lateinit var shared: SharedPreferences

    private var no: Float = 0F

    private var str: String = ""
    private var strSub: String = ""
    private var strTemp: String = ""

    private lateinit var num1: String
    private lateinit var num2: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shared = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE)


        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        edit = databinding.e1
        edit0 = databinding.e0

        num1 = ""
        num2 = ""
        str = ""
        strTemp = ""
        strSub = ""


        databinding.one.setOnClickListener {
            strTemp += "1"
            str += "1"
            edit.text = str
        }
        databinding.two.setOnClickListener {
            strTemp += "2"
            str += "2"
            edit.text = str
        }
        databinding.three.setOnClickListener {
            strTemp += "3"
            str += "3"
            edit.text = str
        }
        databinding.four.setOnClickListener {
            strTemp += "4"
            str += "4"
            edit.text = str
        }
        databinding.five.setOnClickListener {
            strTemp += "5"
            str += "5"
            edit.text = str
        }
        databinding.six.setOnClickListener {
            strTemp += "6"
            str += "6"
            edit.text = str
        }
        databinding.seven.setOnClickListener {
            strTemp += "7"
            str += "7"
            edit.text = str
        }
        databinding.eight.setOnClickListener {
            strTemp += "8"
            str += "8"
            edit.text = str
        }
        databinding.nine.setOnClickListener {
            strTemp += "9"
            str += "9"
            edit.text = str
        }
        databinding.zero.setOnClickListener {
            strTemp += "0"
            str += "0"
            edit.text = str
        }
        databinding.dot.setOnClickListener {
            if (strTemp == "") {
                strTemp += "0"
                str += "0"
            }
            strTemp += "."
            str += "."
            edit.text = str
        }

        databinding.btnBackspace.setOnClickListener {
            var temp: String
            if (strTemp.isNotEmpty()) {
                temp = strTemp.substring(0, strTemp.length - 1)
                strTemp = temp
            }
            if (str.isNotEmpty()) {
                temp = str.substring(0, str.length - 1)
                str = temp
                edit.text = str
            }
            if (xtra.isNotEmpty()) {
                xtra.dropLast(1)
            }
            if (str.isEmpty()) {
                str = ""
                strTemp = ""
                xtra.clear()
                no = 0F
            }
        }

        databinding.btnAddition.setOnClickListener {
            xtra.add(strTemp)
            strTemp = ""
            xtra.add("+")
            str += xtra.last()
            edit.text = str
        }
        databinding.btnSubtraction.setOnClickListener {
            xtra.add(strTemp)
            strTemp = ""
            xtra.add("-")
            str += xtra.last()
            edit.text = str
        }
        databinding.btnMultiplication.setOnClickListener {
            xtra.add(strTemp)
            strTemp = ""
            xtra.add("*")
            str += xtra.last()
            edit.text = str
        }
        databinding.btnDivision.setOnClickListener {
            xtra.add(strTemp)
            strTemp = ""
            xtra.add("/")
            str += xtra.last()
            edit.text = str
        }
        databinding.btnModulus.setOnClickListener {
            val temp = (strTemp.toFloat() / 100).toString()
            str = str.replace(strTemp, temp)
            strTemp = temp
            xtra.add(strTemp)
            edit.text = str
        }

        databinding.btnAns.setOnClickListener {
            if (shared.getString(Password,"") == strTemp){
                str = ""
                num1 = ""
                xtra.clear()
                no = 0F
                num2 = ""
                edit.text = str
                strTemp = ""
                val intent = Intent (this, Hidden::class.java)
                startActivity(intent)
          }
            if (str.isNotEmpty()) {
                var a = 1
                xtra.add(strTemp)
                str += " = "
                strTemp = ""
                if (xtra.size > 1) {
                    if (xtra[0].isNotEmpty()) {
                        if (!(xtra[0].isDigitsOnly())) {
                            no = if (xtra[1].isDigitsOnly()) {
                                xtra[1].toFloat()
                            } else {
                                xtra[0].toFloat()
                            }
                            a = 0
                        } else {
                            no = xtra[0].toFloat()

                        }
                    }
                    for (i in a until xtra.size) {
                        try {
                            if (xtra[i] == "+") {
                                no += xtra[i + 1].toFloat()
                            }
                            if (xtra[i] == "-") {
                                no -= xtra[i + 1].toFloat()
                            }
                            if (xtra[i] == "*") {
                                no *= xtra[i + 1].toFloat()
                            }
                            if (xtra[i] == "/") {
                                no /= xtra[i + 1].toFloat()
                            }
                        } catch (ex: Exception) {
                        }
                    }
                } else {
                    no = xtra[0].toFloat()
                }
            }
            str += no.toString()
            edit.text = str
            strSub = shared.getString("History", "").toString()
            shared.edit().putString("History","$strSub \n $str \n").apply()
            str = no.toInt().toString()
            xtra.clear()
            strTemp = str
        }

            databinding.history.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (shared.getString("History", "") == "") {
                        edit0.text = "\n No History"
                    } else {
                        edit0.text = shared.getString("History", "")
                    }
                } else {
                    edit0.text = ""
                }
            }
            databinding.historyClear.setOnClickListener {
                if (shared.getString("History", "") == "") {
                    edit0.text = "\n No History"
                } else {
                    shared.edit().remove("History").apply()
                    edit0.text = shared.getString("History", "")
                }
            }

            databinding.btnAnsClear.setOnClickListener {
                str = ""
                num1 = ""
                xtra.clear()
                no = 0F
                num2 = ""
                edit.text = str
                strTemp = ""
            }

        databinding.hide.setOnLongClickListener {
            val intent = Intent(this, LoginHide::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

        }
    }