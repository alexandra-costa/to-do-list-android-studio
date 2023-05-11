package com.example.listadetarefas.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;

import java.util.List;



public class ListarTarefasAdapter extends BaseAdapter {

    private final List<Tarefa> tarefas;
    private final Activity activity;

    public ListarTarefasAdapter(List<Tarefa> tarefas, Activity activity) {
        this.tarefas = tarefas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tarefas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.adapter_listar_tarefas, parent, false);
        Tarefa tarefa = tarefas.get(position);
        TextView txtItemDescricao = (TextView) view.findViewById(R.id.txt_item_descricao);
        txtItemDescricao.setText(tarefa.getDescricao());

        CheckBox tarefaCheckbox = view.findViewById(R.id.tarefa_checkbox);
        tarefaCheckbox.setChecked(tarefa.isRealizado());
        tarefaCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarefa.setRealizado(isChecked);
            if (tarefa.isRealizado()){
                txtItemDescricao.setTextColor(Color.RED);
                txtItemDescricao.setPaintFlags(txtItemDescricao.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });


        return view;
    }
}
