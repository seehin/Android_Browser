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

    public List<HistoryRecordBean> getAll(){
        return historyRecordDao.getAll();
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

    //删除所有的记录
    public void deleteAll(){
        new DeleteAsyncTask(historyRecordDao).deleteAll();
    }

    //删除今天的记录
    public void deleteTodayHistory(String date){
        new DeleteAsyncTask(historyRecordDao).deleteTodayHistory(date);
    }

    public void deleteHistoryRecordById(int id){
        new DeleteAsyncTask(historyRecordDao).deleteOneHistoryRecord(id);
    }

    public List<HistoryRecordBean> getFuzzySearchToList(String content){
        return historyRecordDao.fuzzySearchToList(content);
    }

    public LiveData<List<HistoryRecordBean>> getFuzzySearch(String content){
        return new InsertAsyncTask(historyRecordDao).fuzzySearch(content);
    }

    public List<Integer> getAllId(){
        return historyRecordDao.getAllId();
    };

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

        public LiveData<List<HistoryRecordBean>> fuzzySearch(String content){
            return historyRecordDao.fuzzySearch(content);
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

        public void deleteOneHistoryRecord(int id){
            this.historyRecordDao.deleteOne(id);
        }

        public void deleteAll(){
            this.historyRecordDao.deleteAll();
        }

        public void deleteTodayHistory(String date){
            this.historyRecordDao.deleteTodayHistory(date);
        }

    }
}
