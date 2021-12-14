package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoicfesfirebase.databinding.ActivityPuntajeBinding


class PuntajeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPuntajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPuntajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTest.setOnClickListener {
            nuevoTest()
        }

        binding.btnSecundario.setOnClickListener {
            volver()
        }

    }

    private fun nuevoTest(){
        val intent = Intent(this, TestActivity::class.java)
        this.startActivity(intent)
    }

    private fun volver(){
        val intent = Intent(this, SecondActivity::class.java)
        this.startActivity(intent)
    }
}