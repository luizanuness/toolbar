package com.example.toolbar.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toolbar.alunoactivity.CadAluno
import com.example.toolbar.databinding.ActivityItemAlunoBinding
import com.example.toolbar.model.Aluno

class AlunoAdapter(
    private val context: Context,
    private val deleteCallback: (Int) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {


    private var alunos: List<Aluno> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ActivityItemAlunoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]
        holder.bind(aluno)

        holder.binding.btnExcluirAluno.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Aluno")
                .setMessage("Deseja realmente excluir o aluno ${aluno.nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    deleteCallback(aluno.id)
                }
                .setNegativeButton("Não", null)
                .show()
        }


        holder.binding.btnEditarAluno.setOnClickListener {
            val intent = Intent(context, CadAluno::class.java)
            intent.putExtra("ALUNO_ID", aluno.id)
            intent.putExtra("ALUNO_NOME", aluno.nome)
            intent.putExtra("ALUNO_CPF", aluno.cpf)
            intent.putExtra("ALUNO_EMAIL", aluno.email)
            intent.putExtra("ALUNO_MATRICULA", aluno.matricula)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return alunos.size
    }

    fun setData (alunos: List<Aluno>){
        this.alunos = alunos
        notifyDataSetChanged()
    }

    inner class AlunoViewHolder(val binding: ActivityItemAlunoBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind (aluno: Aluno){
            binding.apply {
                textNomeAluno.text = aluno.nome
                textCPFAluno.text = aluno.cpf
                textEmailAluno.text = aluno.email
                textMatriculaAluno.text = aluno.matricula
            }
        }
    }

}