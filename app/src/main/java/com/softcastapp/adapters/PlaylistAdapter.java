package com.softcastapp.adapters;

import com.softcastapp.R;
import com.softcastapp.EditPlaylistActivity;
import com.softcastapp.PlaylistActivity;
import com.softcastapp.models.Playlist;
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

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<Playlist> playlists;
    private Context context;

    public PlaylistAdapter(Context context,List<Playlist> playlists) {
        this.playlists = playlists;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.title.setText(playlist.getTitle());

        holder.title.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlaylistActivity.class);
                intent.putExtra("playlist_title", playlist.getTitle());
                context.startActivity(intent);
        });

        holder.Edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPlaylistActivity.class);
            intent.putExtra("playlist_title", playlist.getTitle());
            context.startActivity(intent);
        });

        holder.Delete.setOnClickListener(v ->
            Toast.makeText(context, "Excluir " + playlist.getTitle(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView Edit, Delete;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.playlist_title);
            Edit = itemView.findViewById(R.id.edit_playlist);
            Delete = itemView.findViewById(R.id.delete_playlist);
        }
    }
}