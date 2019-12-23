package com.example.diaryalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_menu);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("설정");
    }

    public void clickSet(View v){  //매개변수 v에는 클릭된 버튼이 연결되어있음
        int button = v.getId(); //현재 눌러진 버튼의 아이디값을 알려달란 뜻
        switch(button){
            case R.id.alram: startActivity(new Intent(getApplicationContext(),SettingActivity.class)); break;
            case R.id.helper: startActivity(new Intent(getApplicationContext(),HelpActivity.class)); break;
        }
    }
}
