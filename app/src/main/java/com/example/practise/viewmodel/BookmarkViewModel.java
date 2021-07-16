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
    public LiveData<List<BookmarkBean>> getBookmarkByInput(String input){
        return bookmarkRepository.getBookmarkByinput(input);
    }
    public void deleteBookmark(BookmarkBean... bookmarkBeans){
        bookmarkRepository.deleteBookmark(bookmarkBeans);
    }
    public void insertBookmark(BookmarkBean... bookmarkBeans){
        bookmarkRepository.insertBookmark(bookmarkBeans);
    }
}
