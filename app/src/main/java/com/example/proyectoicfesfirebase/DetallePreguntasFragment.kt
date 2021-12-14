package com.example.proyectoicfesfirebase

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectoicfesfirebase.databinding.FragmentDetallePreguntasBinding
import com.example.proyectoicfesfirebase.model.Pregunta
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.*


class DetallePreguntasFragment : Fragment() {
    private var _binding:FragmentDetallePreguntasBinding? = null
    private val binding get() = _binding!!

    //Conexion a la base de datos
    var database = Firebase.database
    val dbRefPreguntas = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetallePreguntasBinding.inflate(inflater,container,false)

        val context =  activity?.applicationContext
        context?.let { Firebase.initialize(it) }

        mostrarDatosPregunta()

        binding.btnActualizarP.setOnClickListener {
            editarPregunta()
        }

        binding.btnEliminarP.setOnClickListener {
            eliminarPregunta()
        }

        return binding.root
    }

    private fun mostrarDatosPregunta() {
        val spCategorias = binding.spCategoria
        val listaCategorias = resources.getStringArray(R.array.opcionesCategoria)
        val adaptador =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,listaCategorias) }
        spCategorias.adapter = adaptador

        var spPos: Int? = adaptador?.getPosition(requireArguments().getString("Categoria"))
        binding.spCategoria.setSelection(spPos!!)
        binding.edtDetId.setText(requireArguments().getString("idPregunta"))
        binding.edtDetEnunciado.setText(requireArguments().getString("Enunciado"))
        binding.edtDetOpcion1.setText(requireArguments().getString("Opcion1"))
        binding.edtDetOpcion2.setText(requireArguments().getString("Opcion2"))
        binding.edtDetOpcion3.setText(requireArguments().getString("Opcion3"))
        binding.edtDetRespuesta.setText(requireArguments().getString("Respuesta"))
    }
    private fun editarPregunta() {
        var pregunta = Pregunta(
            binding.edtDetId.text.toString(),
            binding.spCategoria.selectedItem.toString(),
            binding.edtDetEnunciado.text.toString(),
            binding.edtDetOpcion1.text.toString(),
            binding.edtDetOpcion2.text.toString(),
            binding.edtDetOpcion3.text.toString(),
            binding.edtDetRespuesta.text.toString()
        )
        dbRefPreguntas.child(pregunta.id).setValue(pregunta)
        Toast.makeText(context, "Se actualizo la pregunta correctamente", Toast.LENGTH_SHORT).show()
        salir()
    }

    private fun eliminarPregunta() {
        val positiveButton = { dialog: DialogInterface, which: Int ->
            dbRefPreguntas.child(binding.edtDetId.text.toString()).removeValue()
            Toast.makeText(context, "Se elimino la pregunta correctamente", Toast.LENGTH_SHORT).show()
            salir()
        }
        val negativeButton = { _: DialogInterface, _: Int -> }
        val dialog =
            context?.let {
                AlertDialog.Builder(it).setTitle(resources.getString(R.string.txt_tituloDel))
                    .setMessage(resources.getString(R.string.txt_Del))
                    .setPositiveButton(resources.getString(R.string.txt_positivo), positiveButton)
                    .setNegativeButton(resources.getString(R.string.txt_negativo), negativeButton)
            }
        dialog?.create()
        dialog?.show()
    }
    fun salir(){
        val intent = Intent(context, SecondActivity::class.java)
        this.startActivity(intent)
        activity?.getSupportFragmentManager()?.beginTransaction()
            ?.remove( this )
            ?.commit()

        /*val lvPreguntas = activity?.findViewById<ListView>( R.id.lvPreguntas)
        lvPreguntas?.visibility = View.VISIBLE*/
    }
}