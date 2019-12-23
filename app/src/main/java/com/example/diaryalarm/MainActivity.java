package com.example.diaryalarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryalarm.Database.DatabaseHelper;
import com.example.diaryalarm.Database.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private DiaryAdapter mAdapter;
    private List<Model> modelList = new ArrayList<>();

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

        db = new DatabaseHelper(this);


        // 알람 구현
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

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
            case android.R.id.home: startActivity(new Intent(getApplicationContext(),DiaryListActivity.class)); break;
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

    public void saveDiary(View v){
        TextView context = findViewById(R.id.diary);
        if (TextUtils.isEmpty(context.getText().toString())){
            Toast.makeText(MainActivity.this, "Enter note!", Toast.LENGTH_SHORT).show();
            return;
        }

        // db에 새로운 노트를 insert한 다음 추가된 노트를 읽어와서 Note객체를 생성 하기
        System.out.println(context.getText().toString());
        long id = db.insertDiary(context.getText().toString());
//        Model diary = db.getDiary(id);
//
//        if (diary != null) {
//            // adding new note to array list at 0 position
//            modelList.add(0, diary);
//
//            // refreshing the list
//            mAdapter.notifyDataSetChanged();
//
//        }
    }

}
