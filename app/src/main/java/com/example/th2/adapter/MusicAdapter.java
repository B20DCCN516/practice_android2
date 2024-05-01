package com.example.th2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.models.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context context;
    private List<Music> musics;

    private ItemListener listener;


    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public MusicAdapter(Context context) {
        this.context = context;
        musics = new ArrayList<>();
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
        notifyDataSetChanged();
    }

    public Music getItem(int position) {
        return musics.get(position);
    }
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = musics.get(position);
        if(music == null) return;

        holder.nameView.setText("Bài hát: "+music.getName());
        holder.singerView.setText("Ca sỹ: "+music.getSinger());
        holder.albumView.setText("Bài hát: "+music.getAlbum());
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameView,singerView, albumView;
        public MusicViewHolder(@NonNull View itemView){
            super(itemView);
            nameView = itemView.findViewById(R.id.item_name);
            singerView = itemView.findViewById(R.id.item_singer);
            albumView = itemView.findViewById(R.id.item_album);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null) {
                listener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
