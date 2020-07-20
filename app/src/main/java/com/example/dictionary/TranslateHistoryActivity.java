package com.example.dictionary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dictionary.adapter.TuVungAdapter;
import com.example.dictionary.dao.TranslateHistoryDAO;
import com.example.dictionary.model.TuVung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TranslateHistoryActivity extends AppCompatActivity {
    List<TuVung> listVocabulary = new ArrayList<>();
    ListView lvVocabulary;
    TextView tvNoHistory;
    TranslateHistoryDAO historyDAO;
    TuVungAdapter tuVungAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lịch sử tra từ");
        setContentView(R.layout.activity_translate_history);
        lvVocabulary = (ListView) findViewById(R.id.lvVocabulary);
        tvNoHistory = (TextView) findViewById(R.id.tvNoHistory);
        historyDAO = new TranslateHistoryDAO(TranslateHistoryActivity.this);
        listVocabulary = historyDAO.getAll();
        Collections.reverse(listVocabulary);
        if (listVocabulary.size() > 0) {
            tvNoHistory.setVisibility(View.GONE);
        } else {
            tvNoHistory.setVisibility(View.VISIBLE);
        }
        tuVungAdapter = new TuVungAdapter(this, listVocabulary);
        lvVocabulary.setAdapter(tuVungAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                if (historyDAO.getAll().size() > 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Xóa dữ liệu tìm kiếm");
                    builder.setMessage("Bạn có xóa toàn bộ dữ liệu không?");
                    builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            historyDAO.deleteAll();
                            listVocabulary = historyDAO.getAll();
                            if (listVocabulary.size() > 0) {
                                tvNoHistory.setVisibility(View.GONE);
                            } else {
                                tuVungAdapter.refreshList();
                                tvNoHistory.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                }
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }
}