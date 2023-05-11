package com.example.listadetarefas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetarefas.DAO.AppDatabase;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AlterarTarefaActivity extends AppCompatActivity {

    private TextInputEditText alterarDescricao;
    private Button btnAlterar;
    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_tarefa);

        alterarDescricao = findViewById(R.id.alterar_descricao);
        btnAlterar = findViewById(R.id.btn_alterar);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        int id = args.getInt("id_tarefa");
        tarefa = AppDatabase.getInstance(this).tarefaDAO().listarUm(id);

        alterarDescricao.setText(tarefa.getDescricao());
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = alterarDescricao.getText().toString();

                if (descricao.isEmpty()) {
                    alterarDescricao.setError("Descrição não pode ser vazia");
                    return;
                }

                tarefa.setDescricao(descricao);
                AppDatabase.getInstance(AlterarTarefaActivity.this).tarefaDAO().alterar(tarefa);

                Intent intent = new Intent(AlterarTarefaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
