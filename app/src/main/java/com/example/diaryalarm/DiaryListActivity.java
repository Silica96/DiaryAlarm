package com.example.diaryalarm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DiaryListActivity extends Activity {

    ArrayList<DiaryData> diaryDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.list_view);
        final DiaryAdapter diaryAdapter = new DiaryAdapter(this,diaryDataList);

        listView.setAdapter(diaryAdapter);
    }

    public void InitializeMovieData() {
        diaryDataList = new ArrayList<DiaryData>();

        diaryDataList.add(new DiaryData(R.drawable.blackcat, "12월 25일 일기", "오늘은 크리스마스이다."));

    }


}
