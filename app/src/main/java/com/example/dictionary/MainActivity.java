package com.example.dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionary.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    String strUserName, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.activity_main);
        if (checkLoginShap() < 0) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    public int checkLoginShap(){
        SharedPreferences pref = getSharedPreferences("USER_FILE_",MODE_PRIVATE);
        boolean chk = pref.getBoolean("REMEMBER",false);
        if (chk){
            strUserName = pref.getString("USERNAME","");
            strPassword = pref.getString("PASSWORD","");
            return 1;
        }
        return -1;
    }

    public void viewTranslate(View v) {
        Intent intent = new Intent(MainActivity.this, ListVocabularyActivity.class);
        startActivity(intent);
    }

    public void viewHistoryTranslateActivity(View v) {
        Intent intent = new Intent(MainActivity.this, TranslateHistoryActivity.class);
        startActivity(intent);
    }

    public void viewInfoApp(View v) {
        Intent intent = new Intent(MainActivity.this, InfoAppActivity.class);
        startActivity(intent);
    }

    public void viewSearchVoice(View v) {
        Intent intent = new Intent(MainActivity.this, SpeechToTextActivity.class);
        startActivity(intent);
    }
}

