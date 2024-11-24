package com.softcastapp.adapters;

import com.softcastapp.R;
import com.softcastapp.models.Conteudo;
import com.softcastapp.models.Playlist;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistContentAdapter extends RecyclerView.Adapter<PlaylistContentAdapter.ContentViewHolder> {

    private List<Conteudo> listaDeConteudos;
    private Playlist playlist; // Playlist associada
    private Context context;

    public PlaylistContentAdapter(Context context, Playlist playlist, List<Conteudo> listaDeConteudos) {
        this.context = context;
        this.playlist = playlist; // Recebe a playlist
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

        // Preenchendo os campos do layout com as informações do modelo
        holder.titulo.setText(conteudo.getTitulo());
        holder.descricao.setText(conteudo.getDescricao());
        holder.tipo.setText("Tipo: " + conteudo.getTipo());
        holder.classificacao.setText("Classificação: " + conteudo.getClassificacaoIndicativa());

        // Configurando o botão de deletar
        holder.deleteButton.setOnClickListener(v -> {
            deleteContentFromPlaylist(playlist.getPlaylistID(), conteudo.getId(), position);
        });
    }

    private void deleteContentFromPlaylist(int playlistID, int conteudoID, int position) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.deleteConteudo(playlistID, conteudoID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remover o conteúdo da lista localmente
                    listaDeConteudos.remove(position);
                    notifyItemRemoved(position); // Atualizar somente o item removido
                    Toast.makeText(context, "Conteúdo removido da playlist com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erro ao remover o conteúdo da playlist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeConteudos.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descricao, tipo, classificacao;
        Button deleteButton;

        public ContentViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.content_title);
            tipo = itemView.findViewById(R.id.content_type);
            descricao = itemView.findViewById(R.id.content_description);
            classificacao = itemView.findViewById(R.id.content_classification);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
