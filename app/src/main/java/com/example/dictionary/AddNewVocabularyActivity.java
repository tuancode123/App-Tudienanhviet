package com.example.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictionary.dao.TuVungDAO;
import com.example.dictionary.model.TuVung;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewVocabularyActivity extends AppCompatActivity {
    EditText edtEnglish;
    EditText edtVietNam;
    Button btnAddVocabulary;
    Button btnCancel;
    TuVungDAO tuVungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm từ vựng ");
        setContentView(R.layout.activity_add_new_vocabulary);
        edtEnglish = (EditText) findViewById(R.id.edtEnglish);
        edtVietNam = (EditText) findViewById(R.id.edtVietNam);
        tuVungDAO = new TuVungDAO(AddNewVocabularyActivity.this);
    }

    public void addVocabulary(View v) {
        if (edtEnglish.getText().toString().trim().length() == 0) {
            Toast.makeText(AddNewVocabularyActivity.this, "Không được bỏ trống", Toast.LENGTH_LONG).show();
            edtEnglish.setText("");
            edtEnglish.setFocusable(true);
            return;
        }
        if (edtVietNam.getText().toString().trim().length() == 0) {
            Toast.makeText(AddNewVocabularyActivity.this, "Không được bỏ trống", Toast.LENGTH_LONG).show();
            edtVietNam.setText("");
            edtVietNam.setFocusable(true);
            return;
        }
        TuVung t = new TuVung(edtEnglish.getText().toString(), edtVietNam.getText().toString());
        try {
            if (tuVungDAO.insertTuVung(t) > 0) {
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public void cancelAddVocabulary(View v) {

    }
}