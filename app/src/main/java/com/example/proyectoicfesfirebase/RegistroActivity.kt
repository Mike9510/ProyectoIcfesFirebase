package com.example.proyectoicfesfirebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectoicfesfirebase.databinding.ActivityRegistroBinding
import com.example.proyectoicfesfirebase.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import com.google.firebase.auth.FirebaseUser




class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth:FirebaseAuth

    val database = Firebase.database
    val dbRefUsuarios = database.getReference("usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= Firebase.auth
        Firebase.initialize(this)

        llenarRoles()

        binding.btnTerminos.setOnClickListener {
            validarTerminos()
        }

        binding.btnGuardarUsuario.setOnClickListener{
            validarCampos()
        }
    }

    private fun llenarRoles() {
        val listaRol = resources.getStringArray(R.array.opcionesRol)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item,listaRol)
        binding.spRol.adapter = adaptador
    }


    private fun validarCampos() {
        if (binding.edtNombre.text.isEmpty() || binding.edtApellido.text.isEmpty() ||
            binding.edtIdentificacion.text.isEmpty() || binding.edtTelefono.text.isEmpty() ||
            binding.edtEmailRegistro.text.isEmpty() || binding.edtPassRegistro.text.isEmpty() ||
            binding.spRol.getSelectedItemPosition()==0){
            Toast.makeText(this,resources.getString(R.string.txt_vacio),Toast.LENGTH_SHORT).show()
        }

        if ( validarCorreo(binding.edtEmailRegistro.text.toString())) {
            if (validarClave(binding.edtPassRegistro.text.toString())) {
                if (binding.chkTerminos.isChecked){
                    //Añadir datos de los campos editText a la lista de acuerdo a dataclass----
                    registrarDatosLogin()



                    //Dialogo para confirmar registro exitoso----------------------------------
                    val positiveButton = { dialog: DialogInterface, which: Int ->
                        val ingreso = Intent(this, MainActivity::class.java)
                        startActivity(ingreso)
                    }
                    val negativeButton = { _: DialogInterface, _: Int ->
                        binding.edtNombre.setText("")
                        binding.edtApellido.setText("")
                        binding.edtIdentificacion.setText("")
                        binding.edtTelefono.setText("")
                        binding.edtEmailRegistro.setText("")
                        binding.edtPassRegistro.setText("")
                        binding.spRol.setSelection(0)
                    }
                    val dialog =
                        AlertDialog.Builder(this).setTitle(resources.getString(R.string.txt_tituloReg))
                            .setMessage(resources.getString(R.string.txt_logueo))
                            .setPositiveButton(resources.getString(R.string.txt_positivo), positiveButton)
                            .setNegativeButton(resources.getString(R.string.txt_negativo), negativeButton)
                    dialog.create()
                    dialog.show()
                }else{
                    Toast.makeText(this, resources.getString(R.string.txt_errorTerm), Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this, resources.getString(R.string.txt_errorClave), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, resources.getString(R.string.txt_errorCorreo), Toast.LENGTH_LONG).show()
        }
    }

    //validacion del campo de correo con expresiones regulares
    private fun validarCorreo ( correo: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val patron = Patterns.EMAIL_ADDRESS.toString()

        pattern = Pattern.compile(patron)
        matcher = pattern.matcher( correo )
        return matcher.matches()
    }

    //validacion del campo de clave con expresiones regulares
    private fun validarClave ( clave: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val patron = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!%*?&.#<>¡=°|¬])(?=\\S+$).{8,}$"

        pattern = Pattern.compile( patron )
        matcher = pattern.matcher( clave )
        return matcher.matches()
    }

    private fun validarTerminos() {
        val positiveButton = { dialog: DialogInterface, which: Int ->
            binding.chkTerminos.setChecked(true)
        }
        val dialog =
            AlertDialog.Builder(this).setTitle(resources.getString(R.string.txt_tituloterm))
                .setMessage(resources.getString(R.string.txt_terminos))
                .setPositiveButton(resources.getString(R.string.txt_aceptar), positiveButton)
        dialog.create()
        dialog.show()
    }

    private fun registrarDatosLogin() {
        val email= binding.edtEmailRegistro.text.toString()
        val pass = binding.edtPassRegistro.text.toString()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    registrarUsuario()
                    volverInicio()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun registrarUsuario() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {

            val uid = user.uid

            var usuario = Usuario(
                uid,
                binding.edtNombre.text.toString(),
                binding.edtApellido.text.toString(),
                binding.edtIdentificacion.text.toString().toLong(),
                binding.edtTelefono.text.toString().toLong(),
                binding.edtEmailRegistro.text.toString(),
                binding.edtPassRegistro.text.toString(),
                binding.spRol.selectedItem.toString()
            )
            dbRefUsuarios.child(uid).setValue(usuario)
        }
    }

    private fun volverInicio() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}
