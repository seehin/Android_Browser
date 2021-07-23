package com.example.practise.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.practise.bean.QuickWayBean;
import com.example.practise.dao.QuickWayDao;
import com.example.practise.database.BrowserDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickWayRepository {
    private LiveData<List<QuickWayBean>> quickWayAll;
    private QuickWayDao quickWayDao;
    private List<QuickWayBean> quickWayBeans;
    private int count;

    public QuickWayRepository(Context context) {
        BrowserDatabase browserDatabase = BrowserDatabase.getDatabase(context.getApplicationContext());
        quickWayDao = browserDatabase.getQuickWayDao();
        //查询所有bookmark
        quickWayAll = quickWayDao.getQuickWayAll();

    }

    public int getCount(){
        count = quickWayDao.getCount();
        return count;
    }

    public LiveData<List<QuickWayBean>> getQuickWayAll() {
        return quickWayAll;
    }

    public List<QuickWayBean> selectByUrl(String url) {
        quickWayBeans = quickWayDao.selectByUrl(url);
        return quickWayBeans;
    }

    //增加Quick(单条)，输入数据为new HistoryRecordBean(hname, hurl, hicon)
    public void insertQuickWay(String hname, String hurl, String hicon) {
        new InsertAsyncTask(quickWayDao).execute(new QuickWayBean(hname, hurl, hicon));
    }

    //删除Quick(单条)
    public void deleteQuickWay(QuickWayBean... quickWayBeans) {
        new DeleteAsyncTask(quickWayDao).execute(quickWayBeans);
    }

    static class InsertAsyncTask extends AsyncTask<QuickWayBean, Void, Void> {
        private QuickWayDao quickWayDao;

        public InsertAsyncTask(QuickWayDao quickWayDao) {
            this.quickWayDao = quickWayDao;
        }

        @Override
        protected Void doInBackground(QuickWayBean... quickWayBeans) {
            List<QuickWayBean> quickWayBeans1 = new ArrayList<>(Arrays.asList(quickWayBeans));

            quickWayDao.insertQuickWay(quickWayBeans1);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<QuickWayBean, Void, Void> {
        private QuickWayDao quickWayDao;

        public DeleteAsyncTask(QuickWayDao quickWayDao) {
            this.quickWayDao = quickWayDao;
        }

        @Override
        protected Void doInBackground(QuickWayBean... quickWayBeans) {
            QuickWayBean bean = quickWayBeans[0];
            quickWayDao.delete(bean);
            return null;
        }
    }
}
