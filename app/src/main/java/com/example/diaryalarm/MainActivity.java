package com.example.diaryalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 커스텀 타이틀 사용으로 기본 타이틀 사용하지 않음
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("알람일기");

        // 커스텀 액션바 사용 - 에러나서 주석처리함
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,);
        //getSupportActionBar().setCustomView(R.layout.custom_title);

        // 액션바 홈버튼 넣기 - list 버튼으로 사용할것
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.list);
    }

    public void clickButton(View v){
        int button = v.getId(); //현재 눌러진 버튼의 아이디값을 알려달란 뜻
        switch(button){
            case R.id.post: break;

        }
    }

    // 액션바 위의 버튼들에
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.home: startActivity(new Intent(getApplicationContext(),DiaryListActivity.class)); break;
            case R.id.action_setting: startActivity(new Intent(getApplicationContext(),SettingActivity.class)); break;
        }
        return super.onOptionsItemSelected(item);
    }

    // menu 연동하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
