package com.example.mc_week7_homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mc_week7_homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    ImageView icon_img;
    TextView name_text, email_text;
    EditText edit_name, edit_email;
    RadioButton btn_cat, btn_dog, btn_horse;
    CheckBox check_login;

    MenuItem slide, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View navHeaderView = navigationView.getHeaderView(0);
        Menu navMenu = navigationView.getMenu();

        icon_img = (ImageView) navHeaderView.findViewById(R.id.icon_img);
        name_text = (TextView) navHeaderView.findViewById(R.id.name_text);
        email_text = (TextView) navHeaderView.findViewById(R.id.email_text);
        check_login = (CheckBox) findViewById(R.id.check_login);
        slide = (MenuItem) navMenu.findItem(R.id.nav_slideshow);
        logout = (MenuItem) navMenu.findItem(R.id.nav_logout);

        logout.setVisible(false);

        // 기본값으로 초기화
        icon_img.setImageResource(R.mipmap.ic_launcher_round);
        name_text.setText(R.string.nav_header_title);
        email_text.setText(R.string.nav_header_subtitle);
        check_login.setChecked(false);
        check_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    logout.setVisible(true);
                    slide.setVisible(false);
                }
                else {
                    logout.setVisible(false);
                    slide.setVisible(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_login){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            View dialogView;
            dialogView = (View) View.inflate(this, R.layout.dialogview, null);
            dlg.setTitle("사용자 입력");
            dlg.setIcon(R.mipmap.ic_launcher);
            dlg.setView(dialogView);
            edit_name = (EditText) dialogView.findViewById(R.id.edit_name);
            edit_email = (EditText) dialogView.findViewById(R.id.edit_email);
            btn_cat = (RadioButton) dialogView.findViewById(R.id.btn_cat);
            btn_dog = (RadioButton) dialogView.findViewById(R.id.btn_dog);
            btn_horse = (RadioButton) dialogView.findViewById(R.id.btn_horse);

            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(edit_name.getText().toString().equals("")||edit_email.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "입력란을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
                        return;
                    }
                    if(btn_cat.isChecked())
                        icon_img.setImageResource(R.mipmap.cat);
                    else if(btn_dog.isChecked())
                        icon_img.setImageResource(R.mipmap.dog);
                    else if(btn_horse.isChecked())
                        icon_img.setImageResource(R.mipmap.horse);
                    else{
                        Toast.makeText(getApplicationContext(), "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
                        return;
                    }
                    name_text.setText(edit_name.getText());
                    email_text.setText(edit_email.getText());
                    check_login.setChecked(true);
                }
            });
            dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlg.show();
        }
        return super.onOptionsItemSelected(item);
    }






}