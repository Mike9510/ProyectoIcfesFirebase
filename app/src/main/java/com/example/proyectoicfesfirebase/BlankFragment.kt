package com.example.proyectoicfesfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectoicfesfirebase.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {
private var _binding: FragmentBlankBinding? = null
private val binding get() =_binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlankBinding.inflate(inflater,container,false)
        binding.btnClick.setOnClickListener{
            Toast.makeText(activity,"click",Toast.LENGTH_LONG).show()
        }
        return binding.root

    }



}