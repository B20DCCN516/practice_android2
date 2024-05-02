package com.example.th2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.adapter.MusicAdapter;
import com.example.th2.databaseConfig.DatabaseHelper;
import com.example.th2.models.Music;

import java.util.List;

public class FragmentSearch extends Fragment implements SearchView.OnQueryTextListener{

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private SearchView searchView;

    private MusicAdapter musicAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setup();
    }

    public void initView(View view) {
        searchView = view.findViewById(R.id.fr_search_searchView);
        recyclerView = view.findViewById(R.id.fr_search_recycler);
    }

    public void setup() {
        db = DatabaseHelper.gI(getContext());
        musicAdapter = new MusicAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        List<Music> musics = db.getAllMusic();
        musicAdapter.setMusics(musics);
        recyclerView.setAdapter(musicAdapter);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<Music> musics = db.searhByNameOrAlbum(s);
        musicAdapter.setMusics(musics);
        return false;
    }
}
