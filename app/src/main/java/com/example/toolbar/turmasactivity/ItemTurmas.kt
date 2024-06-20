package com.example.toolbar.turmasactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toolbar.R
import com.example.toolbar.databinding.ActivityItemAlunoBinding
import com.example.toolbar.databinding.ActivityItemTurmasBinding

class ItemTurmas : AppCompatActivity() {

    private val binding by lazy {
        ActivityItemTurmasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
