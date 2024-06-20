package com.example.toolbar.professoractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toolbar.R
import com.example.toolbar.databinding.ActivityItemAlunoBinding
import com.example.toolbar.databinding.ActivityItemProfessorBinding
import com.example.toolbar.databinding.ActivityListagemProfessorBinding

class ItemProfessor : AppCompatActivity() {

    private val binding by lazy {
        ActivityItemProfessorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
