package com.example.proyectoicfesfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener{
            login(binding.edtEmail.text.toString(),binding.edtPass.text.toString())
            //Toast.makeText(this,binding.edtEmail.text.toString(),Toast.LENGTH_LONG).show()
            /*supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, ProfesorFragment::class.java,null,"Profesor")
                .commit()*/
        }

        binding.btnRegistrarUsuario.setOnClickListener{
            VerRegistroUsuario()
            //Toast.makeText(this,binding.edtEmail.text.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun VerRegistroUsuario() {
        val intent = Intent(this, RegistroActivity::class.java)
        this.startActivity(intent)
    }

    private fun login(email:String,pass:String){
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    verSegundaActividad()
                    Toast.makeText(baseContext, "Authentication Successfull.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Authentication Failed  .",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            verSegundaActividad()
        }
    }
    private fun verSegundaActividad(){
        val intent = Intent(this, SecondActivity::class.java)
        this.startActivity(intent)

    }
}