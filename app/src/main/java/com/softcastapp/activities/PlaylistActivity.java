package com.softcastapp.activities;

import com.softcastapp.R;
import com.softcastapp.adapters.PlaylistContentAdapter;
import com.softcastapp.models.Conteudo;
import com.softcastapp.models.Playlist;
import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.services.RetrofitClient;
import com.softcastapp.services.ApiService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private Button btnBack, btnAddContent;
    private RecyclerView recyclerView;
    private PlaylistContentAdapter adapter;
    private List<Conteudo> contentsPlaylist;
    private int playlistId;
    private ApiService apiService;
    private VideoView videoView;
    private String videoPath;
    private File videoFile;
    private static final int REQUEST_ADD_CONTENT = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_CONTENT) {
            int playlistId = data.getIntExtra("PLAYLIST_ID", -1); // Recupera o ID da playlist
            if (playlistId != -1) {
                // Recarrega os conteúdos da playlist com o ID atualizado
                fetchConteudosByPlaylistId(playlistId);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        // Inicializar o serviço da API
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Recuperando o ID da Playlist da Intent
        Intent playlistIntent = getIntent();
        int playlistId = playlistIntent.getIntExtra("PLAYLIST_ID", -1);  // Usando a chave correta

        // Pegando o objeto UsuarioLogin da Intent
        UsuarioLogin usuarioLogin = (UsuarioLogin) playlistIntent.getSerializableExtra("USUARIO");

        btnBack = findViewById(R.id.btn_return);
        btnAddContent = findViewById(R.id.btn_add_content);

        btnBack.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(PlaylistActivity.this, DashboardActivity.class);
            dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(dashboardIntent);
            finish();
        });

        btnAddContent.setOnClickListener(v -> {
            Intent intent = new Intent(PlaylistActivity.this, ContentActivity.class);
            intent.putExtra("playlist_title", getIntent().getStringExtra("playlist_title"));
            intent.putExtra("playlistId", playlistId);  // Usando o valor que já pegamos
            intent.putExtra("PLAYLIST_ID", playlistId);
            startActivityForResult(intent, REQUEST_ADD_CONTENT);
        });

        String playlistTitle = getIntent().getStringExtra("playlist_title");
        TextView playlistTitleTextView = findViewById(R.id.playlistTitle);
        playlistTitleTextView.setText(playlistTitle);

        recyclerView = findViewById(R.id.recyclerViewPlaylistContents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializando a lista de conteúdos
        Playlist playlist = new Playlist();
        playlist.setPlaylistID(playlistId); // Setar o ID da playlist
        playlist.setNome(playlistTitle);    // Opcional: setar o nome da playlist

        adapter = new PlaylistContentAdapter(this, playlist, contentsPlaylist);

        // Chamada à API para buscar os conteúdos relacionados à playlist
        fetchConteudosByPlaylistId(playlistId);

        contentsPlaylist = new ArrayList<>();
        adapter = new PlaylistContentAdapter(this, playlist, contentsPlaylist);
        recyclerView.setAdapter(adapter);
    }

    private void fetchConteudosByPlaylistId(int playlistId) {
        Call<List<Conteudo>> call = apiService.getConteudosByPlaylistId(playlistId);
        call.enqueue(new Callback<List<Conteudo>>() {
            @Override
            public void onResponse(Call<List<Conteudo>> call, Response<List<Conteudo>> response) {
                if (response.isSuccessful()) {
                    List<Conteudo> conteudos = response.body();
                    if (conteudos != null && !conteudos.isEmpty()) {
                        contentsPlaylist.clear();  // Limpa a lista existente
                        contentsPlaylist.addAll(conteudos);  // Adiciona os novos conteúdos
                        adapter.notifyDataSetChanged();  // Notifica o adapter para atualizar a view
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Conteudo>> call, Throwable t) {
                // Tratar erros de rede ou resposta inválida
                t.printStackTrace();
            }
        });
    }
}
