package com.example.toolbar.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toolbar.turmasactivity.CadTurmas
import com.example.toolbar.databinding.ActivityItemTurmasBinding
import com.example.toolbar.model.Turmas

class TurmaAdapter(
    private val context: Context,
    private val deleteCallback: (Int) -> Unit
) : RecyclerView.Adapter<TurmaAdapter.TurmaViewHolder>() {


    private var turmas: List<Turmas> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurmaViewHolder {
        val binding = ActivityItemTurmasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TurmaViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TurmaViewHolder, position: Int) {
        val turma = turmas[position]
        holder.bind(turma)

        holder.binding.btnExcluirTurmas.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Turma")
                .setMessage("Deseja realmente excluir a turma ${turma.curso}?")
                .setPositiveButton("Sim") { _, _ ->
                    deleteCallback(turma.id)
                }
                .setNegativeButton("Não", null)
                .show()
        }


        holder.binding.btnEditarTurmas.setOnClickListener {
            val intent = Intent(context, CadTurmas::class.java)
            intent.putExtra("TURMA_ID", turma.id)
            intent.putExtra("TURMA_CURSO", turma.curso)
            intent.putExtra("TURMA_DATAINICIO", turma.datainicio)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return turmas.size
    }

    fun setData (turmas: List<Turmas>){
        this.turmas = turmas
        notifyDataSetChanged()
    }

    inner class TurmaViewHolder(val binding: ActivityItemTurmasBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind (turma: Turmas){
            binding.apply {
                textCursoTurmas.text = turma.curso
                textDataTurmas.text = turma.datainicio
            }
        }
    }

}