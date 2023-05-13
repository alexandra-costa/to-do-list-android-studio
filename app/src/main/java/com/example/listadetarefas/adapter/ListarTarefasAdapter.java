
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.adapter_listar_tarefas, parent, false);

            holder = new ViewHolder();
            holder.txtItemDescricao = convertView.findViewById(R.id.txt_item_descricao);
            holder.tarefaCheckbox = convertView.findViewById(R.id.tarefa_checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tarefa tarefa = tarefas.get(position);
        holder.txtItemDescricao.setText(tarefa.getDescricao());

        holder.tarefaCheckbox.setOnCheckedChangeListener(null); // Remova o listener atual

        holder.tarefaCheckbox.setChecked(tarefa.isRealizado());

        holder.tarefaCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarefa.setRealizado(isChecked);
            if (tarefa.isRealizado()) {
                holder.txtItemDescricao.setTextColor(Color.RED);
                holder.txtItemDescricao.setPaintFlags(holder.txtItemDescricao.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.txtItemDescricao.setTextColor(Color.BLACK);
                holder.txtItemDescricao.setPaintFlags(holder.txtItemDescricao.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        return convertView;
    }



    private static class ViewHolder {
        TextView txtItemDescricao;
        CheckBox tarefaCheckbox;
    }
}
