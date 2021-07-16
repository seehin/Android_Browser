package com.example.practise.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.practise.bean.BookmarkBean;
import com.example.practise.dao.BookmarkDao;
import com.example.practise.database.BrowserDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookmarkRepository {
    private LiveData<List<BookmarkBean>> bookmarkall;
    private BookmarkDao bookmarkDao;
    private LiveData<List<BookmarkBean>> bookmarkByinput;
    public BookmarkRepository(Context context){
        BrowserDatabase browserDatabase = BrowserDatabase.getDatabase(context.getApplicationContext());
        bookmarkDao = browserDatabase.getBookmarkDao();
        //查询所有bookmark
        bookmarkall = bookmarkDao.getBookmarkAll();
    }


    public LiveData<List<BookmarkBean>> getBookmarkall() {
        return bookmarkByinput;
    }

    public LiveData<List<BookmarkBean>> getBookmarkByinput(String input) {
        bookmarkByinput = bookmarkDao.loadBookmarkByinput(input);
        return bookmarkall;
    }

    //增加bookmark
    public void insertBookmark(BookmarkBean... bookmarkList) {
       new InsertAsyncTask(bookmarkDao).execute(bookmarkList);
    }

    //删除bookmark(单条)，输入数据为new BookmarkBean(bname, burl, bicon, bdate)
    public void deleteBookmark(BookmarkBean... bookmarkBeans){
        new DeleteAsyncTask(bookmarkDao).execute(bookmarkBeans);
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
//
//    //删除bookmark(单条)，输入数据为new BookmarkBean(bname, burl, bicon, bdate)
//    public void deleteBookmark(BookmarkBean bookmarkBean) {
//        BrowserDatabase.getDefault(getApplicationContext()).getBookmarkDao().delete(bookmarkBean);
//    }


    static class InsertAsyncTask extends AsyncTask<BookmarkBean,Void,Void> {
        private BookmarkDao bookmarkDao;

        public InsertAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }
        @Override
        protected Void doInBackground(BookmarkBean... bookmarkBeans) {
            List<BookmarkBean> bookmarkBeanList = new ArrayList<>(Arrays.asList(bookmarkBeans));
            bookmarkDao.insertAll(bookmarkBeanList);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<BookmarkBean,Void,Void> {
        private BookmarkDao bookmarkDao;

        public DeleteAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }
        @Override
        protected Void doInBackground(BookmarkBean... bookmarkBean) {
            BookmarkBean bean = bookmarkBean[0];
            bookmarkDao.delete(bean);
            return null;
        }
    }
}
