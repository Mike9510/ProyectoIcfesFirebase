package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivityTestBinding
import com.example.proyectoicfesfirebase.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import  java.util.Random

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var listaTest: ArrayList<Pregunta>
    private lateinit var testAdapter: ArrayAdapter<Pregunta>

    //Conexion a la base de datos
    var database = Firebase.database
    var dbRefPreguntas = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar Firebase authentication
        auth = Firebase.auth
        //Inicializar la conexion a la base de datos Firebase
        Firebase.initialize(this)
        //Inicializar la lista de preguntas en vacio
        listaTest = ArrayList<Pregunta>()
        listaPreguntas = ArrayList<Pregunta>()

        verTest()

        binding.btnResultado.setOnClickListener {
            verResultado()
        }

    }

    private fun verResultado() {
        val intent = Intent(this, PuntajeActivity::class.java)
        this.startActivity(intent)
    }

    private fun verTest() {
        val testItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pre in datasnapshot.children) {
                        val mapTest: Map<String, Any> = pre.value as HashMap<String, Any>
                        var test: Pregunta = Pregunta(
                            mapTest.get("id").toString(),
                            mapTest.get("categoria").toString(),
                            mapTest.get("enunciado").toString(),
                            mapTest.get("opcion1").toString(),
                            mapTest.get("opcion2").toString(),
                            mapTest.get("opcion3").toString(),
                            mapTest.get("respuesta").toString()
                        )
                        listaPreguntas.add(test)

                }

                if(listaPreguntas.size > 5){
                    var  n = 0
                    var aleatorio = Random()
                    while (n < 5) {
                        var preguntaTest = listaPreguntas[aleatorio.nextInt(listaPreguntas.size)]
                        listaTest.add(preguntaTest)
                        if (listaTest.contains(preguntaTest)) {
                            //Toast.makeText(this@TestActivity, "${preguntaTest}", Toast.LENGTH_LONG).show()
                        } else {
                            listaTest.add(preguntaTest)

                        }
                        n += 1
                    }

                }
                else {
                    listaTest = listaPreguntas
                }

                testAdapter = TestAdapter(this@TestActivity, listaTest)
                binding.lvTest.adapter = testAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbRefPreguntas.addValueEventListener(testItemListener)
    }
}