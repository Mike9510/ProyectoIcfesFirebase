package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= Firebase.auth
        binding.btnGuardarUsuario.setOnClickListener{
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val email= binding.edtEmailRegistro.text.toString()
        val pass = binding.edtPassRegistro.text.toString()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    VeractividadCategoria()
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun VeractividadCategoria() {
        val intent = Intent(this, CategoriasMainActivity::class.java)
        this.startActivity(intent)
    }
}
