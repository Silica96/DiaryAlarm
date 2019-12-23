package com.example.diaryalarm;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryalarm.Database.DatabaseHelper;
import com.example.diaryalarm.Database.Model;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends Activity {

    public DiaryAdapter mAdapter;
    private List<Model> modelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler);
        modelList.addAll(db.getAllNotes());


        mAdapter = new DiaryAdapter(this, modelList);

        System.out.println("ttt");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
