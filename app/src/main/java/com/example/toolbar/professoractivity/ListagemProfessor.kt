package com.example.toolbar.professoractivity

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
import com.example.toolbar.adapter.ProfessorAdapter
import com.example.toolbar.alunoactivity.ListagemAluno
import com.example.toolbar.professoractivity.CadProfessor
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityListagemProfessorBinding
import com.example.toolbar.model.Professor
import com.example.toolbar.turmasactivity.ListagemTurma
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListagemProfessor : AppCompatActivity() {//Início class
//Configuração do viewBinding
private lateinit var binding: ActivityListagemProfessorBinding

    //variável do Adapter
    private lateinit var professorAdapter: ProfessorAdapter



    override fun onCreate(savedInstanceState: Bundle?) {//Início fun onCreate
        super.onCreate(savedInstanceState)
        binding = ActivityListagemProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        loadProfessores()

        binding.btnCadastroProfessor.setOnClickListener {
            val intent = Intent(this, CadProfessor::class.java)
            cadastroProfessorActivityResultLauncher.launch(intent)
        }
    }


    private val cadastroProfessorActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Atualizar a lista de professores após cadastro/edição
                loadProfessores()
            }
        }

    private fun setupRecyclerView() {
        professorAdapter = ProfessorAdapter(this) { professorId ->
            deleteProfessor(professorId)
        }
        binding.professoresRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.professoresRecyclerView.adapter = professorAdapter
    }



    //variáveis retrofit
    private fun loadProfessores() {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.getProfessor()

        //Body professores
        call.enqueue(object : Callback<List<Professor>> {
            override fun onResponse(call: Call<List<Professor>>, response: Response<List<Professor>>) {
                if (response.isSuccessful) {
                    response.body()?.let { professores ->
                        professorAdapter.setData(professores)
                    }
                } else {
                    Toast.makeText(
                        this@ListagemProfessor,
                        "Falha ao carregar professor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Professor>>, t: Throwable) {
                Toast.makeText(this@ListagemProfessor, "Erro: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun deleteProfessor(professorId: Int) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.excluirProfessor(professorId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ListagemProfessor,
                        "Professor excluído com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadProfessores() // Atualizar a lista de professores após exclusão
                } else {
                    Toast.makeText(
                        this@ListagemProfessor,
                        "Falha ao excluir professor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ListagemProfessor, "Erro: ${t.message}", Toast.LENGTH_SHORT)
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

