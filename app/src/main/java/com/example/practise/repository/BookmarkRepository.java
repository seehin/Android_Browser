package com.example.practise.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

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
    private LiveData<List<BookmarkBean>> upperBookmark;
    private Integer isexit;
    private Integer maxsort;
    private List<BookmarkBean> folderIdOfSubfolder;

    public BookmarkRepository(Context context){
        BrowserDatabase browserDatabase = BrowserDatabase.getDatabase(context.getApplicationContext());
        bookmarkDao = browserDatabase.getBookmarkDao();
        //查询所有bookmark
        bookmarkall = bookmarkDao.getBookmarkAll();
    }


    public LiveData<List<BookmarkBean>> getBookmarkall() {
        return bookmarkall;
    }

    public LiveData<List<BookmarkBean>> getBookmarkByinput(String input) {
        bookmarkByinput = bookmarkDao.loadBookmarkByinput(input);
        return bookmarkByinput;
    }

    //打开文件夹，获取upper值与文件夹id值一样的书签
    public LiveData<List<BookmarkBean>> getUpperBookmark(int id) {
        upperBookmark = bookmarkDao.getUpperBookmark(id);
        return upperBookmark;
    }

    //根据id拿到子书签中的文件夹或者网址
    public List<BookmarkBean> getBookmarkOfSubfolder(int id, int[] isFolders){
        folderIdOfSubfolder = bookmarkDao.getBookmarkOfSubfolder(id, isFolders);
        return folderIdOfSubfolder;
    }

    //获取所有文件夹
    public List<BookmarkBean> getAllFolder(List<Integer> id, int upper){
        return bookmarkDao.getAllFolder(id, upper);
    }

    //判断文件名是否存在，返回0或1
    public Integer isNewBookmarkNameExit(String name, int[] isFolders){
        isexit = bookmarkDao.isNewBookmarkNameExit(name, isFolders);
        return isexit;
    }

    //判断网址是否已经存在，返回0或者1
    public Integer isNewUrlExit(String url){
        return bookmarkDao.isNewUrlExit(url);
    }

    //获取upper与文件夹id相同的书签的sort的最大值
    public Integer getMaxSort(int id){
        maxsort = bookmarkDao.getMaxSort(id);
        return maxsort;
    }

    //插入bookmark
    public void insertBookmark(BookmarkBean... bookmarkList) {
        new InsertAsyncTask(bookmarkDao).execute(bookmarkList);
    }

    //删除bookmark(单条)
    public void deleteBookmarks( List<BookmarkBean> needDelete){
        for (BookmarkBean bookmarkBean : needDelete){
            new DeleteAsyncTask(bookmarkDao).execute(bookmarkBean);
        }
    }

    //根据URL删除数据
    public BookmarkBean getBookmarkBasisUrl(String url){
        return bookmarkDao.getBookmarkBasisUrl(url);
    }

    //当新建了文件之后，要更新上一级的bnum的数据
    public void updateBookmark(int id, int num, BookmarkBean... bookmarkBeans){
        new UpdateAsyncTask(id, num, bookmarkDao, 0).execute(bookmarkBeans);
    }

    //当移动书签到别的文件夹时，更新所选择文件夹的upper
    public void updateUpperOfBookmark(int id, List<Integer> checkedIds, BookmarkBean... bookmarkBeans){
        new UpdateAsyncTask(id, checkedIds, bookmarkDao, 1).execute(bookmarkBeans);
    }

    //修改书签
    public void alterBookmark(BookmarkBean... bookmarkBeans){
        new UpdateAsyncTask(bookmarkDao, 2).execute(bookmarkBeans);
    }

    //插入任务
    static class InsertAsyncTask extends AsyncTask<BookmarkBean,Void,Void> {
        private BookmarkDao bookmarkDao;

        public InsertAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }
        @Override
        protected Void doInBackground(BookmarkBean... bookmarkBeans) {
            bookmarkDao.insertBookmark(bookmarkBeans);
            return null;
        }
    }

    //删除任务
    static class DeleteAsyncTask extends AsyncTask<BookmarkBean,Void,Void> {
        private BookmarkDao bookmarkDao;

        public DeleteAsyncTask(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }
        @Override
        protected Void doInBackground(BookmarkBean... bookmarkBean) {
            bookmarkDao.deleteBookmarks(bookmarkBean);
            return null;
        }
    }

    //更新任务
    static class UpdateAsyncTask extends AsyncTask<BookmarkBean,Void,Void> {
        private BookmarkDao bookmarkDao;
        private int id;
        private int tag;
        private int num;
        private List<Integer> checkedIds;

        public UpdateAsyncTask(int id, int num, BookmarkDao bookmarkDao, int tag) {
            this.bookmarkDao = bookmarkDao;
            this.id = id;
            this.num = num;
            this.tag = tag;
        }

        public UpdateAsyncTask(int id, List<Integer> checkedIds, BookmarkDao bookmarkDao, int tag) {
            this.bookmarkDao = bookmarkDao;
            this.id = id;
            this.checkedIds = checkedIds;
            this.tag = tag;
        }

        public UpdateAsyncTask(BookmarkDao bookmarkDao, int tag){
            this.bookmarkDao = bookmarkDao;
            this.tag = tag;
        }

        @Override
        protected Void doInBackground(BookmarkBean... bookmarkBean) {
            if (tag == 0){
                bookmarkDao.updateBookmark(id, num);
            }else if (tag == 1){
                bookmarkDao.updateUpperOfBookmark(id, checkedIds);
            }else if(tag == 2){
                bookmarkDao.alterBookmark(bookmarkBean);
            }
            return null;
        }
    }
}
