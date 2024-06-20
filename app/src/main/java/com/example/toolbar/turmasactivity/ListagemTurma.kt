package com.example.toolbar.turmasactivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolbar.MainActivity
import com.example.toolbar.R
import com.example.toolbar.adapter.TurmaAdapter
import com.example.toolbar.alunoactivity.ListagemAluno
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityListagemTurmaBinding
import com.example.toolbar.model.Turmas
import com.example.toolbar.professoractivity.ListagemProfessor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListagemTurma : AppCompatActivity() {
    private lateinit var binding: ActivityListagemTurmaBinding
    private lateinit var turmaAdapter: TurmaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListagemTurmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadTurmas()

        binding.btnCadastroTurma.setOnClickListener {
            val intent = Intent(this, CadTurmas::class.java)
            cadastroTurmasActivityResultLauncher.launch(intent)
        }
    }

    private val cadastroTurmasActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Atualizar a lista de turmas após cadastro/edição
                loadTurmas()
            }
        }



    private fun setupRecyclerView() {
        turmaAdapter = TurmaAdapter(this) { turmaId ->
            deleteTurma(turmaId)
        }
        binding.turmasRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.turmasRecyclerView.adapter = turmaAdapter
    }

    private fun loadTurmas() {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.getTurmas()

        call.enqueue(object : Callback<List<Turmas>> {
            override fun onResponse(call: Call<List<Turmas>>, response: Response<List<Turmas>>) {
                if (response.isSuccessful) {
                    response.body()?.let { turmas ->
                        turmaAdapter.setData(turmas)
                    }
                } else {
                    Toast.makeText(
                        this@ListagemTurma,
                        "Falha ao carregar turmas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Turmas>>, t: Throwable) {
                Toast.makeText(this@ListagemTurma, "Erro: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }



    private fun deleteTurma(turmaId: Int) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.excluirTurmas(turmaId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ListagemTurma,
                        "Turma excluída com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadTurmas() // Atualizar a lista de turmas após exclusão
                } else {
                    Toast.makeText(
                        this@ListagemTurma,
                        "Falha ao excluir turma",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ListagemTurma, "Erro: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
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
