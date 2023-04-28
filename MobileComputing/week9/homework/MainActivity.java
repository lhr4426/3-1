package com.example.mc_week9_homework;


import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite, changePwBtn;
    String fileName;

    Switch lockSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        dp = (DatePicker) findViewById(R.id.datePicker);
        edtDiary = (EditText) findViewById(R.id.dailyEdit);
        btnWrite = (Button) findViewById(R.id.inputBtn);
        changePwBtn = (Button) findViewById(R.id.changePwBtn);
        lockSwitch = (Switch) findViewById(R.id.lockSwitch);

        fileName = Integer.toString(cYear) + "_"
                + Integer.toString(cMonth + 1) + "_"
                + Integer.toString(cDay) + ".txt";
        String str = readDiary(fileName);
        edtDiary.setText(str);

        SharedPreferences settings = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String checkedSwitch = settings.getString("checked", "unlock");

        if (checkedSwitch.equals("lock")) {
            lockSwitch.setOnCheckedChangeListener(null);
            lockSwitch.setChecked(true);
            lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());
            changePwBtn.setVisibility(View.VISIBLE);
            edtDiary.setVisibility(View.GONE);
            btnWrite.setVisibility(View.GONE);
        }
        else{
            lockSwitch.setOnCheckedChangeListener(null);
            lockSwitch.setChecked(false);
            lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());
            changePwBtn.setVisibility(View.GONE);
            edtDiary.setVisibility(View.VISIBLE);
            btnWrite.setVisibility(View.VISIBLE);
        }


        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_"
                        + Integer.toString(monthOfYear + 1) + "_"
                        + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);

                edtDiary.setText(str);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, MODE_PRIVATE);
                    // FileOutputStream outFs = new FileOutputStream(new File(getFilesDir(), fileName));
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),
                            fileName + " 이 저장됨", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "error:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("sp", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                String password = settings.getString("pw", "");

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                View dialogView;
                dialogView = (View) View.inflate(getApplicationContext(), R.layout.change_pw, null);
                dlg.setTitle("비밀번호 변경");
                dlg.setView(dialogView);
                EditText old_pw = (EditText) dialogView.findViewById(R.id.old_pw);
                EditText new_pw = (EditText) dialogView.findViewById(R.id.new_pw);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(old_pw.getText().toString().equals(password)){
                            editor.putString("pw", new_pw.getText().toString());
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "비밀번호 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dlg.show();
            }
        });


        lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());

    }
    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            // inFs=new  FileInputStream(new File(getFilesDir(), fileName));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정 하기");
        } catch (IOException e) {
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }

    class lockSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            SharedPreferences settings = getSharedPreferences("sp", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            String password = settings.getString("pw", "");

            // 잠그기
            if(b){
                // 비밀번호가 없을 때
                if(password.equals("")){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    View dialogView;
                    dialogView = (View) View.inflate(getApplicationContext(), R.layout.make_pw, null);
                    dlg.setTitle("비밀번호 생성");
                    dlg.setView(dialogView);
                    EditText make_pw = (EditText) dialogView.findViewById(R.id.make_pw);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putString("pw", make_pw.getText().toString());
                            editor.putString("checked", "lock");
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "비밀번호 생성 완료", Toast.LENGTH_SHORT).show();
                            changePwBtn.setVisibility(View.VISIBLE);
                            edtDiary.setVisibility(View.GONE);
                            btnWrite.setVisibility(View.GONE);
                        }
                    });
                    dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putString("checked", "unlock");
                            editor.commit();
                            lockSwitch.setOnCheckedChangeListener(null);
                            lockSwitch.setChecked(false);
                            lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());
                        }
                    });
                    dlg.show();
                }
                // 비밀번호가 있을 때
                else {
                    Toast.makeText(getApplicationContext(), "잠금 성공", Toast.LENGTH_SHORT).show();
                    editor.putString("checked", "lock");
                    editor.commit();
                    changePwBtn.setVisibility(View.VISIBLE);
                    edtDiary.setVisibility(View.GONE);
                    btnWrite.setVisibility(View.GONE);
                }
            }
            // 잠금 해제하기
            else {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                View dialogView;
                dialogView = (View) View.inflate(getApplicationContext(), R.layout.checking_pw, null);
                dlg.setTitle("비밀번호 입력");
                dlg.setView(dialogView);
                EditText check_pw = (EditText) dialogView.findViewById(R.id.check_pw);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 비밀번호가 맞을때
                        if (check_pw.getText().toString().equals(password)) {
                            editor.putString("checked", "unlock");
                            editor.commit();
                            changePwBtn.setVisibility(View.GONE);
                            edtDiary.setVisibility(View.VISIBLE);
                            btnWrite.setVisibility(View.VISIBLE);
                        }
                        // 비밀번호가 틀릴때
                        else {
                            editor.putString("checked", "lock");
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "비밀번호 오류", Toast.LENGTH_SHORT).show();
                            lockSwitch.setOnCheckedChangeListener(null);
                            lockSwitch.setChecked(true);
                            lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());
                        }
                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("checked", "lock");
                        editor.commit();
                        lockSwitch.setOnCheckedChangeListener(null);
                        lockSwitch.setChecked(true);
                        lockSwitch.setOnCheckedChangeListener(new lockSwitchListener());
                    }
                });
                dlg.show();
            }
        }
    }
}