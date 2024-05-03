package com.example.th2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.models.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {
    private Context context;
    private List<Work> works;

    private ItemListener listener;


    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public WorkAdapter(Context context) {
        this.context = context;
        works = new ArrayList<>();
    }

    public void setMusics(List<Work> musics) {
        this.works = musics;
        notifyDataSetChanged();
    }

    public Work getItem(int position) {
        return works.get(position);
    }
    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new WorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        Work work = works.get(position);
        if(work == null) return;

        holder.nameView.setText("Công việc: "+work.getName());
        holder.contentView.setText("Ca sỹ: "+work.getDescription());
        holder.dateView.setText(work.getDateDone());
        holder.statusView.setText(work.getStatus());
    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameView,contentView, statusView, dateView;
        public WorkViewHolder(@NonNull View itemView){
            super(itemView);
            nameView = itemView.findViewById(R.id.item_name);
            contentView = itemView.findViewById(R.id.item_description);
            dateView = itemView.findViewById(R.id.item_datedone);
            statusView = itemView.findViewById(R.id.item_status);
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
