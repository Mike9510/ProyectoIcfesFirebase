package com.example.proyectoicfesfirebase

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivityCategoriasMainBinding
import com.example.proyectoicfesfirebase.model.Categoria
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class CategoriasMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriasMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaCategorias :ArrayList<Categoria>
    private lateinit var categoriaAdapter: ArrayAdapter<Categoria>

    //Conectamos con la bd de firebase

    var database = Firebase.database
    var dbReferenciaCategorias = database.getReference("Categorias")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        Firebase.initialize(this)
        listaCategorias = ArrayList<Categoria>()

        binding.btnCerrarSesion.setOnClickListener{
            cerrarSesion()
        }
        binding.btnAdicionarCategoria.setOnClickListener{
            val intent = Intent(this, RegistroCategorias::class.java)
            this.startActivity(intent)
        }
        verListadoCategorias()
        
        binding.LvCategorias.setOnItemClickListener { parent, view, position, id ->
            var categoria = listaCategorias[position]
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("categoria",categoria)
            this.startActivity(intent)

        }

    }

    private fun verListadoCategorias() {
        val categoriaItemListener = object: ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (cat in datasnapshot.children){
                    val mapCategoria : Map<String , Any> = cat.value as HashMap<String,Any>


                    var categoria:Categoria = Categoria(
                        mapCategoria.get("id").toString(),
                        mapCategoria.get("nombreCategoria").toString()
                    )
                    listaCategorias.add(categoria)
                    categoriaAdapter = CategoriasAdapter(this@CategoriasMainActivity,listaCategorias)
                    binding.LvCategorias.adapter = categoriaAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        dbReferenciaCategorias.addValueEventListener(categoriaItemListener)
    }

    private fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}