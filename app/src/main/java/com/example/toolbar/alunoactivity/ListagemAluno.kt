package com.example.toolbar.alunoactivity

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
import com.example.toolbar.adapter.AlunoAdapter
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityListagemAlunoBinding
import com.example.toolbar.model.Aluno
import com.example.toolbar.professoractivity.ListagemProfessor
import com.example.toolbar.turmasactivity.ListagemTurma
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ListagemAluno : AppCompatActivity() {//Início class
    //Configuração do viewBinding
    private lateinit var binding: ActivityListagemAlunoBinding

    //variável do Adapter
    private lateinit var alunoAdapter: AlunoAdapter



    override fun onCreate(savedInstanceState: Bundle?) {//Início fun onCreate
        super.onCreate(savedInstanceState)
        binding = ActivityListagemAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        loadAlunos()

        binding.btnCadastroAluno.setOnClickListener {
            val intent = Intent(this, CadAluno::class.java)
            cadastroAlunoActivityResultLauncher.launch(intent)
        }
    }


    private val cadastroAlunoActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Atualizar a lista de alunos após cadastro/edição
                loadAlunos()
            }
        }

    private fun setupRecyclerView() {
        alunoAdapter = AlunoAdapter(this) { alunoId ->
            deleteAluno(alunoId)
        }
        binding.alunosRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.alunosRecyclerView.adapter = alunoAdapter
    }



    //variáveis retrofit
    private fun loadAlunos() {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.getAlunos()

        //Body Alunos
        call.enqueue(object : Callback<List<Aluno>> {
            override fun onResponse(call: Call<List<Aluno>>, response: Response<List<Aluno>>) {
                if (response.isSuccessful) {
                    response.body()?.let { alunos ->
                        alunoAdapter.setData(alunos)
                    }
                } else {
                    Toast.makeText(
                        this@ListagemAluno,
                        "Falha ao carregar alunos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Aluno>>, t: Throwable) {
                Toast.makeText(this@ListagemAluno, "Erro: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun deleteAluno(alunoId: Int) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.excluirAluno(alunoId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ListagemAluno,
                        "Aluno excluído com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadAlunos() // Atualizar a lista de alunos após exclusão
                } else {
                    Toast.makeText(
                        this@ListagemAluno,
                        "Falha ao excluir aluno",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ListagemAluno, "Erro: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }



    //Fim fun onCreate
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






}//Fim class

