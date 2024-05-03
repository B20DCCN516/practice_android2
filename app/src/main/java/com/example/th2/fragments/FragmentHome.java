package com.example.th2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.MusicDetail;
import com.example.th2.R;
import com.example.th2.adapter.WorkAdapter;
import com.example.th2.constant.Constant;
import com.example.th2.databaseConfig.DatabaseHelper;
import com.example.th2.models.Work;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentHome extends Fragment implements WorkAdapter.ItemListener{
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    private WorkAdapter musicAdapter;

    private DatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view) {
        db = DatabaseHelper.gI(getContext());
        floatingActionButton = view.findViewById(R.id.floatBtnAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicDetail.class);
                intent.putExtra("status", Constant.ADD);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.fr_home_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        musicAdapter = new WorkAdapter(getContext());
        List<Work> musics = db.getAllWork();
        musicAdapter.setMusics(musics);
        musicAdapter.setListener(this);
        recyclerView.setAdapter(musicAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Work> musics = db.getAllWork();
        musicAdapter.setMusics(musics);
    }

    @Override
    public void onItemClick(View view, int position) {
        Work music = musicAdapter.getItem(position);
        Intent intentDetail = new Intent(getActivity(),MusicDetail.class);
        intentDetail.putExtra("work",music);
        intentDetail.putExtra("status",Constant.EDIT);
        startActivity(intentDetail);

    }
}
