package com.softcastapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.softcastapp.R;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        VideoView videoView = findViewById(R.id.videoView);

        // Recupera o caminho do vídeo passado pela intent
        String videoPath = getIntent().getStringExtra("videoPath");

        if (videoPath != null && !videoPath.isEmpty()) {
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);

            // Controladores para pausar/avançar o vídeo
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            // Inicia o vídeo
            videoView.start();
        } else {
            Toast.makeText(this, "Erro ao carregar o vídeo", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a atividade caso não haja vídeo
        }
    }
}
