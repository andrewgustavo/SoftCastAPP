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

    // Construtor para passar a lista de conteúdos
    public ContentAdapter(Context context, List<Conteudo> listaDeConteudos) {
        this.context = context;
        this.listaDeConteudos = listaDeConteudos;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_content, parent, false); // Aqui usamos o layout do item
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        // Pega o item da lista na posição correspondente
        Conteudo conteudo = listaDeConteudos.get(position);

        // Configura a exibição do item no RecyclerView
        holder.title.setText(conteudo.getTitulo());

        // Configura o clique no botão "Assistir"
        holder.watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exibe uma mensagem de que o vídeo está sendo "assistido"
                // Aqui você pode implementar o código para iniciar o player de vídeo
                Toast.makeText(context, "Assistindo: " + conteudo.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeConteudos.size();
    }

    // ViewHolder que mantém as referências dos itens de cada linha
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button watchButton;
        ImageView thumbnail;
        public ContentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content_title); // A referência ao TextView do layout
            watchButton = itemView.findViewById(R.id.watchButton);
            thumbnail = itemView.findViewById(R.id.videoThumbnail);
        }

    }
}