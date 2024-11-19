package com.softcastapp.adapters;

import com.softcastapp.R;
import android.content.Context;
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

    private List<String> playlists;
    private Context context;

    public PlaylistAdapter(List<String> playlists, Context context) {
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
        String playlist = playlists.get(position);
        holder.textViewTitle.setText(playlist);

        // Ações de clique para os ícones
        holder.imageViewEdit.setOnClickListener(v ->
                Toast.makeText(context, "Editar " + playlist, Toast.LENGTH_SHORT).show()
        );
        holder.imageViewDelete.setOnClickListener(v ->
                Toast.makeText(context, "Excluir " + playlist, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView imageViewEdit;
        ImageView imageViewDelete;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            imageViewEdit = itemView.findViewById(R.id.image_view_edit);
            imageViewDelete = itemView.findViewById(R.id.image_view_delete);
        }
    }
}
