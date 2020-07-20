package com.example.dictionary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.adapter.TuVungAdapter;
import com.example.dictionary.dao.TuVungDAO;
import com.example.dictionary.model.TuVung;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpeechToTextActivity extends AppCompatActivity implements View.OnClickListener {
TextView txtResult;
ImageView imageView;
    ListView lvVocabulary;

    List<TuVung> listVocabulary = new ArrayList<>();
    TuVungDAO tuVungDAO;
    TuVungAdapter tuVungAdapter;

    private static final int requestCodeP  = 113;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        addControls();
    }

    private void addControls() {
        txtResult=findViewById(R.id.txtResult);
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        lvVocabulary=findViewById(R.id.lvVocabulary);

        tuVungDAO = new TuVungDAO(SpeechToTextActivity.this);
        tuVungAdapter = new TuVungAdapter(this, listVocabulary);
        lvVocabulary.setAdapter(tuVungAdapter);
    }

    public  void getSpeechInput()
    {
//use implicit intent to get the input in form speech
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,requestCodeP);
        }
        else
        {
            Toast.makeText(this, "Your device DOn't support Speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case requestCodeP:
                if(resultCode==RESULT_OK)
                {
                ArrayList<String>result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txtResult.setText(result.get(0));
                    TuVung t = tuVungDAO.getTuVungByID(result.get(0).trim());
                    if (t != null) {
                        Log.e("ListVocabularyActivity", t.toString());
                        tuVungAdapter.setResultSearch(t);
                    } else {
                        Toast.makeText(SpeechToTextActivity.this, "Từ cần tra không có trong danh sách", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    txtResult.setText("not match anything");
                }
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageView:
                getSpeechInput();
                break;
        }
    }
}