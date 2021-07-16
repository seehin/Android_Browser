package com.example.practise.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.dao.HistoryRecordDao;
import com.example.practise.database.BrowserDatabase;
import com.example.practise.utils.DataConversionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HistoryRecordRepository {
    private LiveData<List<HistoryRecordBean>> historyRecordAll;
    private HistoryRecordDao historyRecordDao;
    private LiveData<List<HistoryRecordBean>> historyByInput;
    private LiveData<List<HistoryRecordBean>> historyByTime;

    public HistoryRecordRepository(Context context){
        BrowserDatabase browserDatabase = BrowserDatabase.getDatabase(context.getApplicationContext());
        historyRecordDao = browserDatabase.getHistoryRecordDao();
        //查询所有bookmark
        historyRecordAll = historyRecordDao.getHistoryRecordAll();
    }

    public LiveData<List<HistoryRecordBean>> getHistoryRecordAll() {
        return historyRecordAll;
    }

    public LiveData<List<HistoryRecordBean>> getHistoryByInput(String input) {
        historyByInput = historyRecordDao.loadHistoryRecordByinput(input);
        return historyByInput;
    }

    public LiveData<List<HistoryRecordBean>> getHistoryByTime(Date hdate) {
        String dateString = DataConversionFactory.fromDateToString(hdate);
        historyByTime = historyRecordDao.loadHistoryRecordByDate(dateString);
        return historyByTime;
    }

    //增加history(单条)，输入数据为new HistoryRecordBean(hname, hurl, hicon, hdate)
    public void insertHistoryRecord(String hname, String hurl, String hicon, Date hdate) {
        String dateString = DataConversionFactory.fromDateToString(hdate);
        new InsertAsyncTask(historyRecordDao).execute(new HistoryRecordBean(hname, hurl, hicon, dateString));
    }

    //删除history(单条)
    public void deleteHistoryRecord(HistoryRecordBean... historyRecordBeans){
        new DeleteAsyncTask(historyRecordDao).execute(historyRecordBeans);
    }

    static class InsertAsyncTask extends AsyncTask<HistoryRecordBean,Void,Void> {
        private HistoryRecordDao historyRecordDao;

        public InsertAsyncTask(HistoryRecordDao historyRecordDao) {
            this.historyRecordDao = historyRecordDao;
        }
        @Override
        protected Void doInBackground(HistoryRecordBean... historyRecordBeans) {
            List<HistoryRecordBean> historyRecordBeans1 = new ArrayList<>(Arrays.asList(historyRecordBeans));

            historyRecordDao.insertAll(historyRecordBeans1);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<HistoryRecordBean,Void,Void> {
        private HistoryRecordDao historyRecordDao;

        public DeleteAsyncTask(HistoryRecordDao historyRecordDao) {
            this.historyRecordDao = historyRecordDao;
        }
        @Override
        protected Void doInBackground(HistoryRecordBean... historyRecordBeans) {
            HistoryRecordBean bean = historyRecordBeans[0];
            historyRecordDao.delete(bean);
            return null;
        }
    }
}
