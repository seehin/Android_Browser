package com.example.practise.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.database.BrowserDatabase;
import com.example.practise.utils.DataConversionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollecter.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollecter.removeActivity(this);
    }

    //增加history
    public void insertHistoryRecord(String hname, String hurl, String hicon, Date hdate) {
        List<HistoryRecordBean> historyRecordList = new ArrayList<>();
        String dateString = DataConversionFactory.fromDateToString(hdate);
        historyRecordList.add(new HistoryRecordBean(hname, hurl, hicon, dateString));
        BrowserDatabase.getDefault(getApplicationContext()).getHistoryRecordDao().insertAll(historyRecordList);
    }

    //查询所有history
    public List<HistoryRecordBean> queryHistoryRecord1() {
        List<HistoryRecordBean> historyRecordAll = BrowserDatabase.getDefault(getApplicationContext()).getHistoryRecordDao().getHistoryRecordAll();
        return historyRecordAll;
    }

    //模糊查询history，根据名称或网址进行模糊查询
    public List<HistoryRecordBean> queryHistoryRecord2(String input) {
        List<HistoryRecordBean> historyRecordList = BrowserDatabase.getDefault(getApplicationContext()).getHistoryRecordDao().loadHistoryRecordByinput(input);
        return historyRecordList;
    }

    //模糊查询history，根据名称或网址进行模糊查询
    public List<HistoryRecordBean> queryHistoryRecord3(Date hdate) {
        String dateString = DataConversionFactory.fromDateToString(hdate);
        List<HistoryRecordBean> historyRecordList = BrowserDatabase.getDefault(getApplicationContext()).getHistoryRecordDao().loadHistoryRecordByDate(dateString);
        return historyRecordList;
    }

    //删除history(单条)，输入数据为new HistoryRecordBean(hname, hurl, hicon, hdate)
    public void deleteHistoryRecord(HistoryRecordBean historyRecordBean) {
        BrowserDatabase.getDefault(getApplicationContext()).getHistoryRecordDao().delete(historyRecordBean);
    }

    //增加bookmark
    public void insertBookmark(List<BookmarkBean> bookmarkList) {
        BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().insertAll(bookmarkList);
    }

    //查询所有bookmark
    public List<BookmarkBean> queryBookmark1() {
        List<BookmarkBean> bookmarkAll = BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().getBookmarkAll();
        return bookmarkAll;
    }

    //模糊查询bookmark，根据名称或网址进行模糊查询
    public List<BookmarkBean> queryBookmark2(String input) {
        List<BookmarkBean> bookmarkList = BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().loadBookmarkByinput(input);
        return bookmarkList;
    }

    //删除bookmark(单条)，输入数据为new BookmarkBean(bname, burl, bicon, bdate)
    public void deleteBookmark(BookmarkBean bookmarkBean) {
        BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().delete(bookmarkBean);
    }

//    private void updatePhone(String name, String phone) {
//        BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().update(new BookmarkBean(phone, name));
//    }
}