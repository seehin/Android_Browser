package com.example.practise.test;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.practise.base.BaseActivity;
import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.viewmodel.BookmarkViewModel;
import com.example.practise.viewmodel.HistoryRecordViewModel;


public class TestDataActivity extends BaseActivity {

    HistoryRecordViewModel historyRecordViewModel;
    BookmarkViewModel bookmarkViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyRecordViewModel = new ViewModelProvider(this).get(HistoryRecordViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        //HistoryRecordBean historyRecordBean = new HistoryRecordBean(1,"github","github.com","//", "2021-7-15");
//        historyRecordBean.setId(1);
//        bookmarkViewModel.insertBookmark(new BookmarkBean(1,"1","1",1,1,1,1,1));
//        historyRecordViewModel.insertHistoryRecord("www","github.com","//", new Date());
        //historyRecordViewModel.deleteHistoryRecord(historyRecordBean);
    }
}