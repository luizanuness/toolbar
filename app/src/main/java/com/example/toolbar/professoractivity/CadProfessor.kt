package com.example.toolbar.professoractivity

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
import com.example.toolbar.databinding.ActivityCadProfessorBinding
import com.example.toolbar.model.Professor
import com.example.toolbar.turmasactivity.ListagemTurma
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadProfessor : AppCompatActivity() {
    private lateinit var binding: ActivityCadProfessorBinding
    private var professorId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        professorId = intent.getIntExtra("PROFESSOR_ID", -1)
        if (professorId != -1) {
            binding.edtNome.setText(intent.getStringExtra("PROFESSOR_NOME"))
            binding.edtCpf.setText(intent.getStringExtra("PROFESSOR_CPF"))
            binding.edtEmail.setText(intent.getStringExtra("PROFESSOR_EMAIL"))
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val cpf = binding.edtCpf.text.toString()
            val email = binding.edtEmail.text.toString()

            if (nome.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty()) {
                val professor = Professor(professorId ?: 0, nome, cpf, email)
                if (professorId != null && professorId != -1) {
                    alterarProfessor(professor)
                } else {
                    salvarProfessor(professor)
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarProfessor(professor: Professor) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.inserirProfessor(professor)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@CadProfessor, "Erro ao salvar professor.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadProfessor, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alterarProfessor(professor: Professor) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.alterarProfessor(professor)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("PROFESSOR_ALTERADO", true))
                    finish()
                } else {
                    Toast.makeText(this@CadProfessor, "Erro ao alterar professor.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadProfessor, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
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
