package com.example.listadetarefas.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listadetarefas.model.Tarefa;

import java.util.List;
@Dao
public interface TarefaDAO {
    @Query("select*from tarefas")
    List<Tarefa> listarTodos();

    @Query("select*from tarefas where id = :id")
    Tarefa listarUm(int id);

    @Insert
    void inserir(Tarefa tarefa);

    @Update
    void alterar(Tarefa tarefa);

    @Delete
    void deletar(Tarefa tarefa);
}
