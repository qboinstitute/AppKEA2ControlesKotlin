package com.qbo.appkea2controleskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val listapreferencias = ArrayList<String>()
    private val listausuario = ArrayList<String>()
    private var estadocivil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArrayAdapter.createFromResource(this,
            R.array.estado_civil_array,
            android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
            spestadocivil.adapter = adapter
        }
        spestadocivil.onItemSelectedListener = this

        cbdeporte.setOnClickListener {
            agregarPreferencia(it)
        }
        cbdibujo.setOnClickListener {
            agregarPreferencia(it)
        }
        cbotros.setOnClickListener {
            agregarPreferencia(it)
        }

        btnregistrar.setOnClickListener { vista ->
            if(validarFormulario(vista)){
                var infousuario = etnombre.text.toString()+ " - "+
                        etapellido.text.toString() + " - "+
                        obtenerGeneroSeleccionado() + " - " +
                        obtenerPreferenciasSeleccionadas() + "-" +
                        estadocivil+" - "+
                        swemail.isChecked
                listausuario.add(infousuario)
                setearControles()
                enviarMensaje(vista, "Usuario Registrado")
            }
        }
        btnlistar.setOnClickListener {
            val intentLista = Intent(
                    this,
                    ListaActivity::class.java).apply {
                putExtra("listausuario", listausuario)
            }
            startActivity(intentLista)
            //Invocar a una actividad sin enviar parametros.
            //startActivity(Intent(this, ListaActivity::class.java))
        }
    }
    fun setearControles(){
        listapreferencias.clear()
        etnombre.setText("")
        etapellido.setText("")
        swemail.isChecked = false
        cbotros.isChecked = false
        cbdibujo.isChecked = false
        cbdeporte.isChecked = false
        rggenero.clearCheck()
        spestadocivil.setSelection(0)
        etnombre.isFocusableInTouchMode = true
        etnombre.requestFocus()
    }

    private fun agregarPreferencia(vista: View) {
        if(vista is CheckBox){
            when(vista.id){
                R.id.cbdeporte ->{
                    if(vista.isChecked){
                        listapreferencias.add(vista.text.toString())
                    }else{
                        listapreferencias.remove(vista.text.toString())
                    }
                }
                R.id.cbdibujo ->{
                    if(vista.isChecked){
                        listapreferencias.add(vista.text.toString())
                    }else{
                        listapreferencias.remove(vista.text.toString())
                    }
                }
                R.id.cbotros ->{
                    if(vista.isChecked){
                        listapreferencias.add(vista.text.toString())
                    }else{
                        listapreferencias.remove(vista.text.toString())
                    }
                }
            }
        }
    }

    fun validarFormulario(vista: View): Boolean{
        var respuesta = false
        if(!validarNombreApellido()){
            enviarMensaje(vista, getString(R.string.errorNombreApellido))
        } else if(!validarGenero()){
            enviarMensaje(vista, getString(R.string.errorGenero))
        }else if(!validarEstadoCivil()){
            enviarMensaje(vista, getString(R.string.errorEstadoCivil))
        } else if(!validarPreferencias()){
            enviarMensaje(vista, getString(R.string.errorPreferencias))
        }else{
            respuesta = true
        }
        return respuesta
    }

    fun obtenerGeneroSeleccionado(): String{
        var genero = ""
        when(rggenero.checkedRadioButtonId){
            R.id.rbmasculino -> {
                genero = rbmasculino.text.toString()
            }
            R.id.rbfemenino ->{
                genero = rbfemenino.text.toString()
            }
        }
        return genero
    }

    fun obtenerPreferenciasSeleccionadas(): String{
        var preferencias = ""
        for(pref in listapreferencias){
            preferencias += "$pref - "
        }
        return preferencias
    }

    fun validarPreferencias(): Boolean{
        if(cbdeporte.isChecked || cbdibujo.isChecked || cbotros.isChecked){
            return true
        }
        return false
    }

    fun validarEstadoCivil(): Boolean {
        if(estadocivil == ""){
            return false
        }
        return true
    }

    fun validarGenero(): Boolean{
        var respuesta = true
        if(rggenero.checkedRadioButtonId == -1){
            respuesta = false
        }
        return respuesta
    }

    fun validarNombreApellido(): Boolean{
        var respuesta = true
        if(etnombre.text.toString().trim().isEmpty()){
            etnombre.isFocusableInTouchMode = true
            etnombre.requestFocus()
            respuesta = false
        } else if(etapellido.text.toString().trim().isEmpty()){
            etapellido.isFocusableInTouchMode = true
            etapellido.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    fun enviarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadocivil = if(position > 0){
            parent!!.getItemAtPosition(position).toString()
        } else{
            ""
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}