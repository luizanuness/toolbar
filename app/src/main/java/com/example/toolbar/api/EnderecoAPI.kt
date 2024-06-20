package com.example.toolbar.api

import com.example.toolbar.model.Aluno
import com.example.toolbar.model.Professor
import com.example.toolbar.model.Turmas
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EnderecoAPI {

    //http://localhost/api/aluno/listar
    @GET ("aluno/listar")
    fun getAlunos(): Call<List<Aluno>>


    @POST("aluno/inserir")
    fun inserirAluno(@Body aluno: Aluno): Call<Void>

    @DELETE("aluno/excluir/{id}")
    fun excluirAluno(@Path("id") id: Int): Call<Void>


    @PUT("aluno/alterar")
    fun alterarAluno(@Body aluno: Aluno): Call<Void>




    //http://localhost/api/professor/listar
    @GET ("professor/listar")
    fun getProfessor(): Call<List<Professor>>

    @POST("professor/inserir")
    fun inserirProfessor(@Body professor: Professor): Call<Void>

    @DELETE("professor/excluir/{id}")
    fun excluirProfessor(@Path("id") id: Int): Call<Void>

    @PUT("professor/alterar")
    fun alterarProfessor(@Body professor: Professor): Call<Void>




    //http://localhost/api/turmas/listar
    @GET ("turmas/listar")
    fun getTurmas(): Call<List<Turmas>>


    @POST("turmas/inserir")
    fun inserirTurmas(@Body turmas: Turmas): Call<Void>

    @DELETE("turmas/excluir/{id}")
    fun excluirTurmas(@Path("id") id: Int): Call<Void>


    @PUT("turmas/alterar")
    fun alterarTurmas(@Body turmas: Turmas): Call<Void>

}