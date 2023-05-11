package com.example.listadetarefas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetarefas.DAO.AppDatabase;
import com.example.listadetarefas.adapter.ListarTarefasAdapter;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listarTarefas;
    private List<Tarefa> tarefas;
    private FloatingActionButton fabCadastrarTarefa;

    private Spinner spinner_idiomas;

    public void atualizarLista(){
        tarefas = AppDatabase.getInstance(this).tarefaDAO().listarTodos();
        ListarTarefasAdapter adapter = new ListarTarefasAdapter(tarefas, this);
        listarTarefas.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout container = findViewById(R.id.container);


        listarTarefas = (ListView) findViewById(R.id.listar_tarefas);

        fabCadastrarTarefa = (FloatingActionButton) findViewById(R.id.fab_cadastrar_tarefa);
        fabCadastrarTarefa.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CadastrarTarefaActivity.class);
            startActivityForResult(intent, 1);
        });

        listarTarefas.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, AlterarTarefaActivity.class);
            intent.putExtra("id_tarefa", tarefas.get(position).getId());
            startActivityForResult(intent, 2);
        });
        List<Tarefa> tarefas = AppDatabase.getInstance(this).tarefaDAO().listarTodos();
        for (Tarefa tarefa : tarefas) {
            View view = getLayoutInflater().inflate(R.layout.adapter_listar_tarefas, null);
            CheckBox checkBox = view.findViewById(R.id.tarefa_checkbox);
            checkBox.setText(tarefa.getDescricao());
            checkBox.setChecked(tarefa.isRealizado());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tarefa.setRealizado(isChecked);
                AppDatabase.getInstance(MainActivity.this).tarefaDAO().alterar(tarefa);
            });
            container.addView(view);
        }
        listarTarefas.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            final Tarefa tarefaSelecionada = (Tarefa) listarTarefas.getAdapter().getItem(info.position);
            MenuItem compartilhar = menu.add("Compartilhar");
            MenuItem deletar = menu.add("Deletar");
            deletar.setOnMenuItemClickListener(item -> {
                AlertDialog a = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja realmente excluir?")
                        .setPositiveButton("Sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AppDatabase.getInstance(MainActivity.this).tarefaDAO().deletar(tarefaSelecionada);
                                        atualizarLista();
                                        Snackbar.make(findViewById(R.id.listar_tarefas), "Deletado com sucesso", Snackbar.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            });


        });

        spinner_idiomas = findViewById(R.id.spinner_linguagens);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.listar_idiomas, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_idiomas.setAdapter(arrayAdapter);
        spinner_idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    SelecionarIdioma("en");
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (position == 2) {
                    SelecionarIdioma("pt");
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarLista();
        tarefas = AppDatabase.getInstance(this).tarefaDAO().listarTodos();
        ListarTarefasAdapter adapter = new ListarTarefasAdapter(tarefas, this);
        listarTarefas.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Snackbar.make(findViewById(R.id.listar_tarefas), "Tarefa cadastrada com sucesso!", Snackbar.LENGTH_LONG).show();
        } else if (resultCode == RESULT_OK && requestCode == 2){
        Snackbar.make(findViewById(R.id.listar_tarefas), "Tarefa alterada com sucesso!", Snackbar.LENGTH_LONG).show();
    }
    }
    public void SelecionarIdioma(String linguagem){
        Locale localidade = new Locale(linguagem);
        Locale.setDefault(localidade);
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = localidade;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}
