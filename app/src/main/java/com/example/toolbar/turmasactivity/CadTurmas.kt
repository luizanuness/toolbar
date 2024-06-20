package com.example.toolbar.turmasactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.toolbar.MainActivity
import com.example.toolbar.R
import com.example.toolbar.alunoactivity.ListagemAluno
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityCadTurmasBinding
import com.example.toolbar.model.Turmas
import com.example.toolbar.professoractivity.ListagemProfessor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadTurmas : AppCompatActivity() {
    private lateinit var binding: ActivityCadTurmasBinding
    private var turmasId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadTurmasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        turmasId = intent.getIntExtra("TURMAS_ID", -1)
        if (turmasId != -1) {
            binding.edtCurso.setText(intent.getStringExtra("TURMAS_CURSO"))
            binding.edtDataInicio.setText(intent.getStringExtra("TURMAS_DATAINICIO"))
        }

        binding.btnSalvar.setOnClickListener {
            val curso = binding.edtCurso.text.toString()
            val datainicio = binding.edtDataInicio.text.toString()

            if (curso.isNotEmpty() && datainicio.isNotEmpty()) {
                val turmas = Turmas(turmasId ?: 0, curso, datainicio)
                if (turmasId != null && turmasId != -1) {
                    alterarTurmas(turmas)
                } else {
                    salvarTurmas(turmas)
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarTurmas(turmas: Turmas) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.inserirTurmas(turmas)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@CadTurmas, "Erro ao salvar turma.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadTurmas, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alterarTurmas(turmas: Turmas) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.alterarTurmas(turmas)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("TURMAS_ALTERADO", true))
                    finish()
                } else {
                    Toast.makeText(this@CadTurmas, "Erro ao alterar turma.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadTurmas, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Criando o menu_principal da tela do App
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    //Ação de clique nos itens do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Testando a ação de clique
        when(item.itemId){
            R.id.menu_home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, MainActivity::class.java)
                startActivity( intent )
            }

            R.id.menu_alunos -> {
                Toast.makeText(this, "Alunos", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, ListagemAluno::class.java)
                startActivity( intent )
            }

            R.id.menu_professores -> {
                Toast.makeText(this, "Professores", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, ListagemProfessor::class.java)
                startActivity( intent )
            }

            R.id.menu_turmas -> {
                Toast.makeText(this, "Turmas", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, ListagemTurma::class.java)
                startActivity( intent )
            }

            R.id.menu_sair -> {
                Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, MainActivity::class.java)
                startActivity( intent )
            }

        }

        return true
    }//FIM - Ação de clique nos itens do menu

}
