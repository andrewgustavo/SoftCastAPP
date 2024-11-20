package com.softcastapp;

import com.softcastapp.adapters.ContentAdapter;
import com.softcastapp.models.Conteudo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<Conteudo> listaDeConteudos;
    private Button btnBack, btnAddContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        btnBack = findViewById(R.id.btn_return);
        btnAddContent = findViewById(R.id.btn_add_content);
        recyclerView = findViewById(R.id.recyclerViewContent);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Criar a lista de conteúdos fictícia
        List<Conteudo> fakeConteudo = new ArrayList<>();
        fakeConteudo.add(new Conteudo("Título 1", "Descrição 1"));
        fakeConteudo.add(new Conteudo("Título 2", "Descrição 4"));
        fakeConteudo.add(new Conteudo("Título 3", "Descrição 5"));

        // Agora, cria o adapter passando o contexto e a lista de conteúdos
        ContentAdapter adapter = new ContentAdapter(this,fakeConteudo);
        recyclerView.setAdapter(adapter);
    }
}

