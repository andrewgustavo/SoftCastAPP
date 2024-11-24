package com.softcastapp.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.softcastapp.R;
import com.softcastapp.models.Conteudo;
import com.softcastapp.activities.ContentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private Context context;
    private List<Conteudo> conteudos;
    private int playlistId;
    private ConteudoClickListener conteudoClickListener;
    private VideoClickListener videoClickListener;

    public ContentAdapter(Context context, List<Conteudo> conteudos, int playlistId, ConteudoClickListener conteudoClickListener, VideoClickListener videoClickListener) {
        this.context = context;
        this.conteudos = conteudos;
        this.playlistId = playlistId;
        this.conteudoClickListener = conteudoClickListener;
        this.videoClickListener = videoClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conteudo conteudo = conteudos.get(position);

        holder.titulo.setText(conteudo.getTitulo());
        holder.tipo.setText(conteudo.getTipo());
        holder.descricao.setText(conteudo.getDescricao());
        holder.classificacao.setText(conteudo.getClassificacaoIndicativa());
        holder.videoPath.setText(conteudo.getVideoPath());

        holder.addButton.setOnClickListener(v -> {
            conteudoClickListener.onConteudoClick(playlistId, conteudo.getId());
        });

         holder.playButton.setOnClickListener(v -> {
            String videoPath = conteudo.getVideoPath();

            File videoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SoftCastVideos");
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }
            File videoFile = new File(videoDir, conteudo.getId() + ".mp4");

            if (videoFile.exists()) {
                exibirPlayer(holder, videoFile);
            } else {
                downloadVideo(conteudo.getVideoPath(), videoFile, holder);
            }
        });
    }

    private void downloadVideo(String videoUrl, File videoFile, ViewHolder holder) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        if (downloadManager != null) {
            Uri downloadUri = Uri.parse(videoUrl);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, "SoftCastVideos/" + videoFile.getName());

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("Baixando vídeo...");

            long downloadId = downloadManager.enqueue(request);

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Download iniciado!", Toast.LENGTH_SHORT).show();
            });

        } else {
            Log.e("VideoPath", "Erro: DownloadManager não disponível.");
        }
    }

    private void exibirPlayer(ViewHolder holder, File videoFile) {
        Uri videoUri = Uri.fromFile(videoFile);
        holder.videoView.setVisibility(View.VISIBLE);
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.start(); // Inicia o vídeo

        MediaController mediaController = new MediaController(context);
        holder.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(holder.videoView);
    }

    @Override
    public int getItemCount() {
        return conteudos.size();
    }

    public interface ConteudoClickListener {
        void onConteudoClick(int playlistId, int conteudoId);
    }

    public interface VideoClickListener {
        void onVideoClick(String videoPath);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, tipo, descricao, classificacao, videoPath;
        Button addButton, playButton;
        VideoView videoView;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.content_title);
            tipo = itemView.findViewById(R.id.content_type);
            descricao = itemView.findViewById(R.id.content_description);
            classificacao = itemView.findViewById(R.id.content_classification);
            videoPath = itemView.findViewById(R.id.content_video_path);
            playButton = itemView.findViewById(R.id.playButton);
            addButton = itemView.findViewById(R.id.addButton);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }
}
