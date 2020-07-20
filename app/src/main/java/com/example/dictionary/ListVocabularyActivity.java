package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dictionary.adapter.TuVungAdapter;
import com.example.dictionary.dao.TranslateHistoryDAO;
import com.example.dictionary.dao.TuVungDAO;
import com.example.dictionary.model.TuVung;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListVocabularyActivity extends AppCompatActivity {
    List<TuVung> listVocabulary = new ArrayList<>();
    ListView lvVocabulary;
    Button btnSearch;
    EditText edtInput;
    TuVungDAO tuVungDAO;
    TranslateHistoryDAO translateHistoryDAO;
    TuVungAdapter tuVungAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tra từ");
        setContentView(R.layout.activity_vocabulary);
        lvVocabulary = (ListView) findViewById(R.id.lvVocabulary);
        edtInput = (EditText) findViewById(R.id.edtSearchVocabulary);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        tuVungDAO = new TuVungDAO(ListVocabularyActivity.this);
        translateHistoryDAO = new TranslateHistoryDAO(ListVocabularyActivity.this);
        Log.d("ListVocabularyActivity", "database = " +tuVungDAO.getAll().size());
        tuVungAdapter = new TuVungAdapter(this, listVocabulary);
        lvVocabulary.setAdapter(tuVungAdapter);

        
//        lvVocabulary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                TuVung t = (TuVung) adapterView.getItemAtPosition(position);
//                Intent intent = new Intent(ListVocabularyActivity.this, AddNewVocabularyActivity.class);
//                Bundle b = new Bundle();
//                b.putString("tuTA", t.getTuTA());
//                b.putString("tuTV", t.getTuTV());
//                intent.putExtras(b);
//                startActivity(intent);
//            }
//        });

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuVungAdapter.refreshList();
                if (edtInput.getText().toString() == null || edtInput.getText().toString().trim().length() == 0) {
                    Toast.makeText(ListVocabularyActivity.this, "Nhập từ cần tra", Toast.LENGTH_LONG).show();
                    edtInput.setText("");
                    edtInput.setFocusable(true);
                } else {
                    TuVung t = tuVungDAO.getTuVungByID(edtInput.getText().toString().trim());
                    if (t != null) {
                        Log.e("ListVocabularyActivity", t.toString());
                        tuVungAdapter.setResultSearch(t);
                        translateHistoryDAO.insertTuVung(t);
                    } else {
                        Toast.makeText(ListVocabularyActivity.this, "Từ cần tra không có trong danh sách", Toast.LENGTH_LONG).show();
                        edtInput.setFocusable(true);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vocabulary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ListVocabularyActivity.this, AddNewVocabularyActivity.class);
                startActivity(intent);
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }
}
