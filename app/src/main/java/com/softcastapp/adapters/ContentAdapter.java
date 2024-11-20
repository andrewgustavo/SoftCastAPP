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

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private List<Conteudo> listaDeConteudos;
    private Context context;

    public ContentAdapter(Context context, List<Conteudo> listaDeConteudos) {
        this.context = context;
        this.listaDeConteudos = listaDeConteudos;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Conteudo conteudo = listaDeConteudos.get(position);

        holder.title.setText(conteudo.getTitulo());

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, conteudo.getTitulo() + " Adicionado a Playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeConteudos.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button addButton;
        ImageView thumbnail;
        public ContentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content_title);
            addButton = itemView.findViewById(R.id.addButton);
            thumbnail = itemView.findViewById(R.id.videoThumbnail);
        }

    }
}