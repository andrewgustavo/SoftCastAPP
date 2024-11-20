package com.softcastapp.adapters;

import com.softcastapp.R;
import com.softcastapp.models.Conteudo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import java.util.List;

public class PlaylistContentAdapter extends RecyclerView.Adapter<PlaylistContentAdapter.ContentViewHolder> {

    private List<Conteudo> listaDeConteudos;
    private Context context;

    public PlaylistContentAdapter(Context context, List<Conteudo> listaDeConteudos) {
        this.context = context;
        this.listaDeConteudos = listaDeConteudos;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_playlist_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Conteudo conteudo = listaDeConteudos.get(position);

        holder.title.setText(conteudo.getTitulo());

        holder.watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Assistindo: " + conteudo.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeConteudos.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button watchButton;
        ImageView thumbnail;
        public ContentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content_title);
            watchButton = itemView.findViewById(R.id.watchButton);
            thumbnail = itemView.findViewById(R.id.videoThumbnail);
        }

    }
}