package com.softcastapp.adapters;

import com.softcastapp.R;
import com.softcastapp.activities.EditPlaylistActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.softcastapp.activities.PlaylistActivity;
import com.softcastapp.models.Playlist;
import com.softcastapp.services.ApiService;
import com.softcastapp.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<Playlist> playlists;
    private Context context;

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.playlists = playlists;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.playlistName.setText(playlist.getNome()); // Exibindo o nome da playlist

        holder.playlistName.setOnClickListener(v -> {
            Intent contentIntent = new Intent(context, PlaylistActivity.class);
            contentIntent.putExtra("PLAYLIST_ID", playlist.getPlaylistID());
            contentIntent.putExtra("playlist_title", playlist.getNome());
            context.startActivity(contentIntent);
        });

        holder.editButton.setOnClickListener(v -> {
            // Intent para editar a playlist
            Intent editIntent = new Intent(context, EditPlaylistActivity.class);
            editIntent.putExtra("PLAYLIST_ID", playlist.getPlaylistID());
            editIntent.putExtra("playlist_title", playlist.getNome());

            // Convertendo o contexto para uma Activity para usar startActivityForResult
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(editIntent, 2);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            deletePlaylist(playlist.getPlaylistID());
        });
    }

    private void deletePlaylist(int playlistID) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.deletePlaylist(playlistID).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Playlist excluída com sucesso!", Toast.LENGTH_SHORT).show();
                    playlists.removeIf(p -> p.getPlaylistID() == playlistID);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Erro ao excluir playlist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(context, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistName;
        ImageView editButton, deleteButton;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlist_title);
            editButton = itemView.findViewById(R.id.edit_playlist);
            deleteButton = itemView.findViewById(R.id.delete_playlist);
        }
    }
}