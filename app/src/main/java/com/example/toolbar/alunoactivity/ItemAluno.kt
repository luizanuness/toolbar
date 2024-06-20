package com.example.toolbar.alunoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toolbar.databinding.ActivityItemAlunoBinding

class ItemAluno : AppCompatActivity() {

        private val binding by lazy {
            ActivityItemAlunoBinding.inflate(layoutInflater)
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}