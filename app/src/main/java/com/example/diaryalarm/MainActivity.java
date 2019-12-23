package com.example.diaryalarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.diaryalarm.Database.DatabaseHelper;
import com.example.diaryalarm.Database.DbBitmapUtility;
import com.example.diaryalarm.Database.Model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import com.soundcloud.android.crop.Crop;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;

    private List<Model> modelList = new ArrayList<>();
    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private boolean isTakenFromCamera;
    private Bitmap rotatedBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("0");
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

        mImageView = findViewById(R.id.imageView);
        checkPermissions();

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
            case android.R.id.home: startActivity(new Intent(getApplicationContext(),DiaryActivity.class)); break;
            case R.id.action_setting: startActivity(new Intent(getApplicationContext(),SettingMenuActivity.class)); break;
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
        byte[] imgByte=null;
        if (isTakenFromCamera){
            Bitmap img =((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            imgByte = DbBitmapUtility.getBytes(img);
        }
        System.out.println(context.getText().toString());
        db.insertDiary(context.getText().toString(),imgByte);
    }

    private void checkPermissions() {
        if(Build.VERSION.SDK_INT < 23)
            return;

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
        }else if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)||shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show an explanation to the user *asynchronously*
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("This permission is important for the app.")
                            .setTitle("Important permission required");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);

                        }
                    });
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }

    // take photo button clicked
    public void onChangePhotoClicked(View v){
        DialogFragment fragment = MyRunsDialogFragment.newInstance(MyRunsDialogFragment.ID_PHOTO_PICKER_FROM_CAMERA);
        fragment.show(getSupportFragmentManager(),getString(R.string.dialog_fragment_tag_photo_picker));
    }

    // 다이얼로그 창에서 take photo 선택했을때 실행
    public void onPhotoPickerItemSelected(int item) {
        Intent intent; // 묵시적 인텐트 : 카메라 앱을 실행하기 위한
        switch (item){
            case MyRunsDialogFragment.ID_PHOTO_PICKER_FROM_CAMERA:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 인텐트에 무엇을 하고싶은지 지정
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "images/jpg");
                mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data",true);
                startActivityForResult(intent, 0);
                isTakenFromCamera = true;
                break;
            default:
        }

    }
    // 카메라 앱에서 사진을 찍고 돌아왔을 때

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) return;

        switch (requestCode){
            case 0:
                beginCrop(mImageCaptureUri);
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);
                if(isTakenFromCamera) {
                    File f = new File(mImageCaptureUri.getPath());
                    if(f.exists()) f.delete();
                }
                break;
        }
    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {

            Uri uri = Crop.getOutput(result);
            Bitmap bitmap;
            try {
                if(Build.VERSION.SDK_INT < 28)
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                else {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getContentResolver(), uri));
                }
                mImageView.setImageBitmap(imageOreintationValidator(bitmap, uri.getPath()));
            }catch (Exception e){
                Log.d("Error", "error");
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);

                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);

                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);

                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;

                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}
