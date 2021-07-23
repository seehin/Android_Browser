package com.example.practise.repository;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.practise.bean.HomeBean;
import com.example.practise.dao.HomeDao;
import com.example.practise.database.BrowserDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeRepository {
    private LiveData<List<HomeBean>> homeall;
    private HomeDao homeDao;
    private LiveData<List<HomeBean>> homeByinput;
    public HomeRepository(Context context){
        BrowserDatabase browserDatabase = BrowserDatabase.getDatabase(context.getApplicationContext());
        homeDao = browserDatabase.getHomeDao();
        //查询所有bookmark
        homeall = homeDao.gethomeAll();
    }

    public void insertAll (List<HomeBean> lists){
        new InsertAsyncTask(homeDao).insertAll(lists);
    }

    public LiveData<List<HomeBean>> gethomeall() {
        return homeall;
    }

    public LiveData<List<HomeBean>> getHomeByinput(String input) {
        homeByinput = homeDao.loadHomeByinput(input);
        return homeByinput;
    }

    //增加bookmark
    public void insertHome(HomeBean... homeList) {
        new InsertAsyncTask(homeDao).execute(homeList);
    }
    public void insert1Home(String vname,String vurl,String vicon){

        new InsertAsyncTask(homeDao).insert1Home(vname,vurl,vicon);
    }



    //删除bookmark(单条)，输入数据为new BookmarkBean(bname, burl, bicon, bdate)
    public void deleteHome(HomeBean... homeBeans){
        new DeleteAsyncTask(homeDao).execute(homeBeans);
    }


//    public void insertBookmark(List<BookmarkBean> bookmarkList) {
//        BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().insertAll(bookmarkList);
//    }
//
//
//    public List<BookmarkBean> queryBookmark1() {
//        List<BookmarkBean> bookmarkAll = BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().getBookmarkAll();
//        return bookmarkAll;
//    }
//
//    //模糊查询bookmark，根据名称或网址进行模糊查询
//    public List<BookmarkBean> queryBookmark2(String input) {
//        List<BookmarkBean> bookmarkList = BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().loadBookmarkByinput(input);
//        return bookmarkList;
//    }

    //删除bookmark(单条)，输入数据为new BookmarkBean(bname, burl, bicon, bdate)
    public void delete1Home(HomeBean homeBean) {
        homeDao.delete(homeBean);
    }

    public List<HomeBean> selectALL(){
        return homeDao.selectALL();
    }

    public List<HomeBean> search_url(String url){
        return homeDao.selectByUrl(url);
    }


    static class InsertAsyncTask extends AsyncTask<HomeBean,Void,Void> {
        private HomeDao homeDao;

        public InsertAsyncTask(HomeDao homeDao) {
            this.homeDao = homeDao;
        }
        @Override
        protected Void doInBackground(HomeBean... homeBeans) {
            List<HomeBean> homeBeanList = new ArrayList<>(Arrays.asList(homeBeans));
            homeDao.insertAll(homeBeanList);
            return null;
        }
        public void insert1Home(String vname,String vurl,String vicon)
        {
            this.homeDao.insert1home(vname,vurl,vicon);
        };

        public void insertAll(List<HomeBean> lists){
            homeDao.insertAll(lists);
        }

    }

    static class DeleteAsyncTask extends AsyncTask<HomeBean,Void,Void> {
        private HomeDao homeDao;

        public DeleteAsyncTask(HomeDao homeDao) {
            this.homeDao =  homeDao;
        }
        @Override
        protected Void doInBackground(HomeBean... homeBean) {
            HomeBean bean = homeBean[0];
            homeDao.delete(bean);
            return null;
        }

    }
}
