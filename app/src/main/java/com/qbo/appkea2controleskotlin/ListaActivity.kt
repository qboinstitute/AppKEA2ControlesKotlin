package com.qbo.appkea2controleskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista.*
import java.util.ArrayList

class ListaActivity : AppCompatActivity() {

    var listausuarios = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        //val parametro = intent.getDoubleExtra("paramdouble", 0.0)
        //val parametro = intent.getStringExtra("paramdouble")
        if(intent.hasExtra("listausuario")){
            listausuarios = intent.getSerializableExtra("listausuario")
                    as ArrayList<String>
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    listausuarios
            )
            lvusuario.adapter = adapter
        }



    }
}