package com.example.proyectoicfesfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.FragmentPreguntasBinding
import com.example.proyectoicfesfirebase.model.Pregunta
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.*


class PreguntasFragment : Fragment() {
    private var _binding:FragmentPreguntasBinding? = null
    private val binding get() = _binding!!

    //Conexion a la base de datos
    val database = Firebase.database
    val dbRefPreguntas = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreguntasBinding.inflate(inflater,container,false)

        val context =  activity?.applicationContext
        context?.let { Firebase.initialize(it) }

        val spCategorias = binding.spCategoria
        val listaCategorias = resources.getStringArray(R.array.opcionesCategoria)
        val adaptador =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,listaCategorias) }
        spCategorias.adapter = adaptador

        binding.btnGuardarP.setOnClickListener {
            guardarPregunta()
        }

        binding.btnCancelar.setOnClickListener {
            salir()
        }

        return binding.root
    }

    private fun guardarPregunta() {
        var pregunta = Pregunta(
            UUID.randomUUID().toString(),
            binding.spCategoria.selectedItem.toString(),
            binding.edtEnunciado.text.toString(),
            binding.edtOpcion1.text.toString(),
            binding.edtOpcion2.text.toString(),
            binding.edtOpcion3.text.toString(),
            binding.edtRespuesta.text.toString()
        )
        dbRefPreguntas.child(pregunta.id).setValue(pregunta)
        Toast.makeText(context, "Se guardo la pregunta correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun salir() {
        val intent = Intent(context, SecondActivity::class.java)
        this.startActivity(intent)
        activity?.getSupportFragmentManager()?.beginTransaction()
            ?.remove( this )
            ?.commit()
    }


}