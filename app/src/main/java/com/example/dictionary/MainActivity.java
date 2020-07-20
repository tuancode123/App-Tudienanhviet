package com.example.dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String strUserName, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.activity_main);
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

