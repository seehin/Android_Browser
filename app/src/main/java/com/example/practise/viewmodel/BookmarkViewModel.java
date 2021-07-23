package com.example.practise.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practise.bean.BookmarkBean;
import com.example.practise.repository.BookmarkRepository;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {

    private BookmarkRepository bookmarkRepository;

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookmarkRepository = new BookmarkRepository(application);
    }

    public LiveData<List<BookmarkBean>> getBookmarkAll(){
        return bookmarkRepository.getBookmarkall();
    }

    //获取当前层书签
    public LiveData<List<BookmarkBean>> getUpperBookmark(int id) {
        return bookmarkRepository.getUpperBookmark(id);
    }

    //判断文件名是否已经存在
    public Integer isNewBookmarkNameExit(String name, int[] isFolders){
        return bookmarkRepository.isNewBookmarkNameExit(name, isFolders);
    }

    //判断网址是否已经存在
    public Integer isNewUrlExit(String url){
        return  bookmarkRepository.isNewUrlExit(url);
    }

    //获取upper与文件夹id相同的书签的sort的最大值
    public Integer getMaxSort(int id){
        return bookmarkRepository.getMaxSort(id);
    }

    //获取所有文件夹
    public List<BookmarkBean> getAllFolder(List<Integer> id, int upper){
        return bookmarkRepository.getAllFolder(id, upper);
    }

    //根据id拿到子书签中的所有文件夹的id值
    public List<BookmarkBean> getBookmarkOfSubfolder(int id, int[] isFolders){
        return bookmarkRepository.getBookmarkOfSubfolder(id, isFolders);
    }

    //根据输入的字段进行模糊查询
    public LiveData<List<BookmarkBean>> getBookmarkByInput(String input){
        return bookmarkRepository.getBookmarkByinput(input);
    }

    public void deleteBookmarks(List<BookmarkBean> needDelete){
        bookmarkRepository.deleteBookmarks(needDelete);
    }

    //根据URL删除数据
    public BookmarkBean getBookmarkBasisUrl(String url){
        return bookmarkRepository.getBookmarkBasisUrl(url);
    }

    public void insertBookmark(BookmarkBean... bookmarkBeans){
        bookmarkRepository.insertBookmark(bookmarkBeans);
    }

    public void updateBookmark(int id, int num, BookmarkBean... bookmarkBeans){
        bookmarkRepository.updateBookmark(id, num, bookmarkBeans);
    }

    //当移动书签到别的文件夹时，更新所选择文件夹的upper
    public void updateUpperOfBookmark(int id, List<Integer> checkedIds, BookmarkBean... bookmarkBeans){
        bookmarkRepository.updateUpperOfBookmark(id, checkedIds, bookmarkBeans);
    }

    //修改书签
    public void alterBookmark(BookmarkBean... bookmarkBeans){
        bookmarkRepository.alterBookmark(bookmarkBeans);
    }
}
