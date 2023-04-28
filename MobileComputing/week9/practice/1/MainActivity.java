package com.example.mc_week9_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText ed;
    Button save, load, clear;
    ImageView imageView;

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.ed);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear);
        imageView = (ImageView) findViewById(R.id.imageView);
        checkPermission();
    }

    public void clearText(View v) {
        ed.setText("");
    }

    public void sharedPreferencesSaveClick(View v) {
        // testShared 라는 설정을 하나 만듬
        SharedPreferences settings = getSharedPreferences("testShared", MODE_PRIVATE);
        // 세팅에 맞는 에디터를 만듬
        SharedPreferences.Editor editor = settings.edit();
        // 에디터의 key = name, value = ed의 text
        editor.putString("name", ed.getText().toString());
        editor.commit();

        // Device File Explorer -> data -> data -> 내가만든 앱 -> shared_prefs 안에 있음
        // 앱만 접근 가능한 폴더임
    }

    public void sharedPreferencesLoadClick(View v) {
        SharedPreferences settings = getSharedPreferences("testShared", MODE_PRIVATE);
        // key가 name인 value를 가져옴 ( 값이 없으면 공백 반환 )
        String str = settings.getString("name", "");
        ed.setText(str);
    }

    public void internalSaveClick(View v) {
        // Device File Explorer -> data -> data -> 내가만든 앱 -> files 안에 있음
        // stream : 데이터의 이동통로 (byte 단위)
        try {
            FileOutputStream outFs = openFileOutput("internal.txt", MODE_PRIVATE);
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void internalLoadClick(View v) {
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            //   FileInputStream inFs=new  FileInputStream(new File(getFilesDir(), "internal.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
        }
    }

    public void externalSaveClick(View v) {
        // String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //Log.d("hwang", "external storage path=" + strSDpath);
        //final File myDir = new File(strSDpath + "/mydir");

        // sdcard -> Android -> data -> 내가 만든 앱 -> files 안에 있음
        // 디렉토리 생성
        File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");   //
        myDir.mkdir();

        Log.d("hwang", myDir.toString());

        try {
            // 새 파일 만드는 방법
            FileOutputStream outFs = new FileOutputStream(new File(myDir, "external.txt"));
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
            Log.d("hwang", "external save ok");
        } catch (IOException e) {
            Log.d("hwang", "external save error" + e.toString());
        }
    }

    public void externalLoadClick(View v) {
        try {
            //final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();   //  no app permission required. api>=23
            // final File myDir = new File(strSDpath + "/mydir");
            File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");
            FileInputStream inFs = new FileInputStream(new File(myDir, "external.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);  // read()
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
            Log.d("hwang", "external load error" + e.toString());
        }

    }

    public void copyClick(View v) {
        // internal to external //
/*
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            FileOutputStream outFs = new FileOutputStream(new File(strSDpath + "/mydir", "internal.txt"));  //permission
            int c;
            while ((c = inFs.read()) != -1)
                outFs.write(c);

            outFs.close();
            inFs.close();
        } catch (IOException e) {
        }
        */
        // external storage : 외부인데 앱 파일 내부
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // external public(DIRECTORY.PICTURES) : sdcard -> Pictures 에 저장
        final String strSDpath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        Log.d("hwang", strSDpath + "   public path= " + strSDpath2);
        try {

            InputStream inFs = getResources().openRawResource(R.raw.cat);

            FileOutputStream outFs = new FileOutputStream(new File(strSDpath, "cat.jpg"));
            FileOutputStream outFs2 = new FileOutputStream(new File(strSDpath2, "cat.jpg"));
            int c;
            while ((c = inFs.read()) != -1) {
                outFs.write(c);
                outFs2.write(c);
            }

            outFs.close();
            outFs2.close();
            inFs.close();
        } catch (IOException e) {
            Log.d("hwang", "error" + e.toString());
        }

    }


    public void deleteClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "cat.jpg");

        file.delete();


    }

    public void showimageClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "cat.jpg");
        imageView.setImageURI(Uri.fromFile(file));
    }


    private void checkPermission() {
        //Log.d("hwang","check=" + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);
            // MY_PERMISSION_REQUEST_STORAGE is an app-defined int constant

        } else {
            //Log.e(TAG, "permission deny");
            //writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show();
                    Log.d("hwang", "Permission granted");

                } else {
                    Log.d("hwang", "Permission deny");
                    // permission denied,
                }
                break;
        }
    }

}