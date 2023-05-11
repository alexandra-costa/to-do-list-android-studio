package com.example.listadetarefas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetarefas.DAO.AppDatabase;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputLayout;

public class CadastrarTarefaActivity extends AppCompatActivity {

    private TextInputLayout tarefaDescricao;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_tarefa);

        tarefaDescricao = (TextInputLayout) findViewById(R.id.textInputLayout);
        btnSalvar = (Button) findViewById(R.id.btn_salvar);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDescricao()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setDescricao(tarefaDescricao.getEditText().getText().toString().trim());
                    AppDatabase.getInstance(CadastrarTarefaActivity.this).tarefaDAO().inserir(tarefa);
                    Intent intent = new Intent();
                    intent.putExtra("resposta", "OK");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            public boolean validarDescricao(){
                if (tarefaDescricao.getEditText().getText().toString().trim().equals("")){
                    tarefaDescricao.setError("A descrição da tarefa não pode estar em branco!");
                    return false;
                }
                tarefaDescricao.setError(null);
                return true;
            }
        });
    }


}