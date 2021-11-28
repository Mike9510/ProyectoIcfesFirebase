package com.example.proyectoicfesfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.proyectoicfesfirebase.databinding.CategoriasListItemBinding
import com.example.proyectoicfesfirebase.model.Categoria
import org.w3c.dom.Text
import java.text.FieldPosition

class CategoriasAdapter (private val mContext: Context, val listaCategorias : List<Categoria>)
    :ArrayAdapter<Categoria>(mContext , 0 , listaCategorias){



        override fun getView(position: Int,convertView:View?,parent:ViewGroup): View {
            val layout = LayoutInflater.from(mContext).inflate(R.layout.categorias_list_item,parent,false)
            val categoria = listaCategorias[position]
            layout.findViewById<TextView>(R.id.TvId).text = categoria.id
            layout.findViewById<TextView>(R.id.TvNombre).text = categoria.nombreCategoria

            return layout
        }
    }











