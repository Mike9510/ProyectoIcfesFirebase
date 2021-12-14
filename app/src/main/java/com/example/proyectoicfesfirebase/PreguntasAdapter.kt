package com.example.proyectoicfesfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.proyectoicfesfirebase.databinding.PreguntasItemBinding
import com.example.proyectoicfesfirebase.model.Pregunta


class PreguntasAdapter(private val pContext: Context, val listaPreguntas: List<Pregunta>)
    : ArrayAdapter<Pregunta>(pContext,0, listaPreguntas){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(pContext).inflate(R.layout.preguntas_item1,parent, false)
        val pregunta = listaPreguntas[position]
        val opt1 = pregunta.opcion1
        val opt2 = pregunta.opcion2
        val opt3 = pregunta.opcion3
        val resp = pregunta.respuesta
        layout.findViewById<TextView>(R.id.tvId).text = pregunta.id
        layout.findViewById<TextView>(R.id.tvArea).text = pregunta.categoria
        layout.findViewById<TextView>(R.id.tvEnunciado).text = pregunta.enunciado
        layout.findViewById<TextView>(R.id.tvOpcion1).text = "A. $opt1"
        layout.findViewById<TextView>(R.id.tvOpcion2).text = "B. $opt2"
        layout.findViewById<TextView>(R.id.tvOpcion3).text = "C. $opt3"
        layout.findViewById<TextView>(R.id.tvRespuesta).text = "D. $resp"

        return layout
    }

}