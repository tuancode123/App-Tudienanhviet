package com.example.dictionary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.dao.TuVungDAO;
import com.example.dictionary.model.TuVung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class InfoAppActivity extends AppCompatActivity {
    Handler mHandler;
    TextView tvTest, tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app);
        setTitle("Thông tin ứng dụng");
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvCount = (TextView) findViewById(R.id.tvCount);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    Toast.makeText(InfoAppActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                    tvTest.setText(msg.obj.toString());
                }
            }
        };

        tvCount.setText(new TuVungDAO(InfoAppActivity.this).getAll().size() + "");
    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<String> arrList = new ArrayList<>();
            List<TuVung> listTv = new ArrayList<>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("anhviet.txt"), "UTF-8"));
                // do reading, usually loop until end of file reading
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    arrList.add(mLine);
                }

                int i = 0;
                TuVung t = null;
                StringBuffer a = null;
                String[] x;
                while (i < arrList.size()) {
                    if (arrList.get(i).matches("^@.+")) {
                        t = new TuVung();
                        a = new StringBuffer();
                        x = arrList.get(i).split("/");
                        if (x.length >= 2) {
                            t.setTuTA(x[0].substring(1).trim());
                            t.setPhienAm(x[1]);
                        } else if (x.length == 1) {
                            t.setTuTA(x[0].substring(1).trim());
                        }
                        for (int k = i + 1; k < arrList.size(); k++) {
                            if (!arrList.get(k).matches("^@.+")) {
                                a.append(arrList.get(k));
                                a.append("\n");
                            } else {
                                i = k - 1;
                                break;
                            }
                        }
                        t.setTuTV(a.toString());
//                    Log.d("InfoAppActivity", t.toString());
                    }
                    listTv.add(t);
                    i++;
                }

                TuVungDAO tuVungDAO = new TuVungDAO(InfoAppActivity.this);
                for (int j = 0; j < listTv.size(); j++) {
                    tuVungDAO.insertTuVung(listTv.get(j));
                }
                Log.d("InfoAppActivity", "Da nap du lieu xong = " + tuVungDAO.getAll().size());
                Message msg = new Message();
                msg.obj = "Đã nạp xong dữ liệu = " + tuVungDAO.getAll().size();
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
        }
    };


    public void viewImportDataBase(View v) {
        Toast.makeText(InfoAppActivity.this, "Bắt đầu đọc file", Toast.LENGTH_LONG).show();
        new Thread(runnable).start();
    }
}