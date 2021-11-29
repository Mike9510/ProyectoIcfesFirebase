package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoicfesfirebase.databinding.ActivityCategoriasMainBinding
import com.example.proyectoicfesfirebase.databinding.ActivityRegistroCategoriasBinding
import com.example.proyectoicfesfirebase.model.Categoria
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.*

class RegistroCategorias : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroCategoriasBinding
    val database = Firebase.database
    val dbReferenceCategorias = database.getReference("Categorias")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_categorias)
        binding = ActivityRegistroCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.initialize(this)

        binding.btnGuardarCategoria.setOnClickListener{
            // guardaremos la categoria
            guardarCategoria()

            VeractividadCategoria()

    }

    }

    private fun VeractividadCategoria() {
        val intent = Intent(this, CategoriasMainActivity::class.java)
        this.startActivity(intent)
    }

    private fun guardarCategoria() {
        var categoria = Categoria(
            UUID.randomUUID().toString(),
            binding.edtTitulo.text.toString()

        )
        dbReferenceCategorias.child(categoria.id).setValue(categoria)

    }

}