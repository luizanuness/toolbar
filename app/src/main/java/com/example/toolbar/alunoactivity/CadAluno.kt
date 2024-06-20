package com.example.toolbar.alunoactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.toolbar.MainActivity
import com.example.toolbar.R
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityCadAlunoBinding
import com.example.toolbar.model.Aluno
import com.example.toolbar.professoractivity.ListagemProfessor
import com.example.toolbar.turmasactivity.ListagemTurma
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadAluno : AppCompatActivity() {
    private lateinit var binding: ActivityCadAlunoBinding
    private var alunoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alunoId = intent.getIntExtra("ALUNO_ID", -1)
        if (alunoId != -1) {
            binding.edtNome.setText(intent.getStringExtra("ALUNO_NOME"))
            binding.edtCpf.setText(intent.getStringExtra("ALUNO_CPF"))
            binding.edtEmail.setText(intent.getStringExtra("ALUNO_EMAIL"))
            binding.edtMatricula.setText(intent.getStringExtra("ALUNO_MATRICULA"))
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val cpf = binding.edtCpf.text.toString()
            val email = binding.edtEmail.text.toString()
            val matricula = binding.edtMatricula.text.toString()

            if (nome.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty() && matricula.isNotEmpty()) {
                val aluno = Aluno(alunoId ?: 0, nome, cpf, email, matricula)
                if (alunoId != null && alunoId != -1) {
                    alterarAluno(aluno)
                } else {
                    salvarAluno(aluno)
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarAluno(aluno: Aluno) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.inserirAluno(aluno)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@CadAluno, "Erro ao salvar aluno.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadAluno, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alterarAluno(aluno: Aluno) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.alterarAluno(aluno)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("ALUNO_ALTERADO", true))
                    finish()
                } else {
                    Toast.makeText(this@CadAluno, "Erro ao alterar aluno.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadAluno, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
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
