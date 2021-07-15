package com.example.practise.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.practise.R;
import com.example.practise.base.BaseActivity;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.database.BrowserDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.insertHistoryRecord("github","github.com","//", new Date());
    }

}