package com.example.proyectoicfesfirebase.model

import java.io.Serializable

data class Pregunta(
    var id: String,
    var categoria: String,
    var enunciado: String,
    var opcion1: String,
    var opcion2: String,
    var opcion3: String,
    var respuesta: String
): Serializable
