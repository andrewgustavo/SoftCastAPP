package com.softcastapp.activities;

import com.softcastapp.R;
import com.softcastapp.adapters.ContentAdapter;
import com.softcastapp.models.Conteudo;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private List<Conteudo> listaDeConteudos;
    private Button backButton;
    private int playlistId;
    private VideoView videoView;
    private String videoPath;
    private File videoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        playlistId = getIntent().getIntExtra("playlistId", -1);

        if (playlistId == -1) {
            Toast.makeText(this, "ID da Playlist não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerViewContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarConteudos();
    }
    private void carregarConteudos() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Conteudo>> call = apiService.getConteudos();

        call.enqueue(new Callback<List<Conteudo>>() {
            @Override
            public void onResponse(Call<List<Conteudo>> call, Response<List<Conteudo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaDeConteudos = response.body();
                    configurarAdapter();
                } else {
                    Toast.makeText(ContentActivity.this, "Falha ao carregar conteúdos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Conteudo>> call, Throwable t) {
                Toast.makeText(ContentActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void configurarAdapter() {
        contentAdapter = new ContentAdapter(this, listaDeConteudos,
                playlistId,
                (playlistId, conteudoId) -> {
                    adicionarConteudoNaPlaylist(playlistId, conteudoId);
                },
                videoPath -> {
                    File videoFile = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "video_" + videoPath.hashCode() + ".mp4");

                    if (videoFile.exists()) {
                        exibirPlayer(videoFile);
                    } else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            downloadVideo(videoPath, videoFile);
                        }
                    }
                });
        recyclerView.setAdapter(contentAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, faz o download
                downloadVideo(videoPath, videoFile);  // Chama o método de download
            } else {
                // Permissão negada, exibe uma mensagem ou lida com a situação
                Toast.makeText(this, "Permissão de armazenamento negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void adicionarConteudoNaPlaylist(int playlistId, int conteudoId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.vincularConteudo(playlistId, conteudoId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ContentActivity.this, "Conteúdo adicionado à playlist!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContentActivity.this, "Erro ao adicionar conteúdo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ContentActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void exibirPlayer(File videoFile) {
        Uri videoUri = Uri.fromFile(videoFile);
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoURI(videoUri);
        videoView.start();

            videoView.setOnCompletionListener(mp -> {
            videoView.setVisibility(View.GONE);
        });
    }
    private void downloadVideo(String videoUrl, File videoFile) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(videoUrl).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ContentActivity.this, "Erro ao baixar o vídeo", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try (InputStream inputStream = response.body().byteStream();
                         OutputStream outputStream = new FileOutputStream(videoFile)) {

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, len);
                        }

                        runOnUiThread(() -> exibirPlayer(videoFile));
                    }
                }
            }
        });
    }
}