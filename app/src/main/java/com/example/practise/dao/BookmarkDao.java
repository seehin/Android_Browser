package com.example.practise.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practise.bean.BookmarkBean;

import java.util.List;

@Dao
public interface BookmarkDao {

    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM bookmark")
    List<BookmarkBean> getBookmarkAll();

    /**
     * 根据指定字段模糊查询，根据输入的搜索内容搜索网址的名称name和网址url
     *
     * @return
     */
    @Query("SELECT * FROM bookmark WHERE bname = :input or burl = :input")
    List<BookmarkBean> loadBookmarkByinput(String input);

    /**
     * 项数据库添加数据
     *
     * @param bookmarkList
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BookmarkBean> bookmarkList);

    /**
     * 修改数据
     *
     * @param bookmark
     */
    @Update()
    void update(BookmarkBean bookmark);

    /**
     * 删除数据
     *
     * @param bookmarkBean
     */
    @Delete()
    void delete(BookmarkBean bookmarkBean);
}
