package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivityEditBinding
import com.example.proyectoicfesfirebase.model.Categoria
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class EditActivity : AppCompatActivity() {
     lateinit var binding: ActivityEditBinding

     var database = Firebase.database

    var dbReferenceCategoria = database.getReference("Categorias")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.initialize(this)

        mostrarDatosCategoria()
        binding.btnActualizarCategoria.setOnClickListener{
            actualizarCategoria()
        }

        binding.btnEliminarCategoria.setOnClickListener{
            eliminarCategoria()
        }
    }

    private fun eliminarCategoria() {
        dbReferenceCategoria.child(binding.edtId.text.toString()).removeValue()
        Toast.makeText(this,"Elimino la categoria ${binding.edtNombreCategoria.text.toString()}",Toast.LENGTH_LONG).show()
        verListadoCategorias()
    }

    private fun actualizarCategoria() {
        var categoria = Categoria(
            binding.edtId.text.toString(),
            binding.edtNombreCategoria.text.toString()
        )
        dbReferenceCategoria.child(categoria.id).setValue(categoria)
        verListadoCategorias()

    }

    private fun verListadoCategorias() {
        val intent = Intent(this, CategoriasMainActivity::class.java)
        this.startActivity(intent)
    }

    private fun mostrarDatosCategoria() {
        var bundle = intent.extras
        val categoria = bundle?.get("categoria") as Categoria

        binding.edtId.setText(categoria.id)
        binding.edtNombreCategoria.setText(categoria.nombreCategoria.toString())
    }
}