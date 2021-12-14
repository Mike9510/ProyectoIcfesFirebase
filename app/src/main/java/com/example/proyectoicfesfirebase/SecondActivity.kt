package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivitySecondBinding
import com.example.proyectoicfesfirebase.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var preguntasAdapter: ArrayAdapter<Pregunta>

    //Conexion a la base de datos
    var database = Firebase.database
    var dbRefPreguntas = database.getReference("preguntas")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializa Firebase Autentication
        auth = Firebase.auth

        //Inicializa la conexion a la base de datos
        Firebase.initialize(this)

        listaPreguntas = ArrayList<Pregunta>()

        binding.btnCerrar.setOnClickListener {
            cerrarSesion()
        }

        binding.btnPuntaje.setOnClickListener {
            verPuntaje()
        }

        binding.btnTest.setOnClickListener {
            verTest()
        }

        binding.btnPreguntas.setOnClickListener {
            binding.lvPreguntas.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace( R.id.fragment_container_view, PreguntasFragment::class.java, null, "EditPreguntas")
                .commit()
        }
        verListadoPreguntas()

        binding.lvPreguntas.setOnItemClickListener { parent, view, position, id ->
            binding.lvPreguntas.visibility = View.GONE
            val pregunta = Bundle()
            pregunta.putString( "idPregunta", listaPreguntas[position].id)
            pregunta.putString("Categoria", listaPreguntas[position].categoria)
            pregunta.putString("Enunciado", listaPreguntas[position].enunciado)
            pregunta.putString("Opcion1", listaPreguntas[position].opcion1)
            pregunta.putString("Opcion2", listaPreguntas[position].opcion2)
            pregunta.putString("Opcion3", listaPreguntas[position].opcion3)
            pregunta.putString("Respuesta", listaPreguntas[position].respuesta)

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace( R.id.fragment_container_view, DetallePreguntasFragment::class.java, pregunta, "EditPreguntas")
                .commit()
        }
    }

    private fun verListadoPreguntas() {
        val preguntaItemListener = object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (preg in datasnapshot.children){
                    //val pregunta = Pregunta ("","","","","","","")
                    // objeto MAP
                    val mapPregunta: Map<String, Any> = preg.value as HashMap<String, Any>
                    var pregunta: Pregunta = Pregunta(
                        mapPregunta.get("id").toString(),
                        mapPregunta.get("categoria").toString(),
                        mapPregunta.get("enunciado").toString(),
                        mapPregunta.get("opcion1").toString(),
                        mapPregunta.get("opcion2").toString(),
                        mapPregunta.get("opcion3").toString(),
                        mapPregunta.get("respuesta").toString()
                    )

                    //Adicionar la pregunta a la listaPreguntas
                    listaPreguntas.add(pregunta)

                    //linkear el adapter con el listView y el item de preguntas
                    preguntasAdapter = PreguntasAdapter(this@SecondActivity, listaPreguntas)
                    binding.lvPreguntas.adapter = preguntasAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbRefPreguntas.addValueEventListener(preguntaItemListener)
    }

    private fun cerrarSesion(){
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun verPuntaje(){
        val intent = Intent(this, PuntajeActivity::class.java)
        this.startActivity(intent)
    }

    private fun verTest(){
        val intent = Intent(this, TestActivity::class.java)
        this.startActivity(intent)

    }
}