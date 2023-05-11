package com.example.listadetarefas.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tarefas")
public class Tarefa {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descricao;
    private boolean isRealizado;

    public Tarefa(int id, String descricao, boolean isRealizado) {
        this.id = id;
        this.descricao = descricao;
        this.isRealizado = isRealizado;
    }

    public Tarefa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRealizado() {
        return isRealizado;
    }

    public void setRealizado(boolean realizado) {
        isRealizado = realizado;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
