package com.example.proyectoicfesfirebase.model

data class Usuario(
    var id: String,
    var nombre: String,
    var apellido: String,
    var identificacion: Long,
    var telefono: Long,
    var correo: String,
    var clave: String,
    var rol: String
)
