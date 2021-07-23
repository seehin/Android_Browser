package com.example.practise.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.practise.bean.HistoryRecordBean;

import java.util.List;

@Dao
public interface HistoryRecordDao {

        /**
         * 查询所有
         *
         * @return
         */
        @Query("SELECT * FROM historyrecord")
        LiveData<List<HistoryRecordBean>> getHistoryRecordAll();

        /**
         * 根据指定字段模糊查询，根据输入的搜索内容搜索网址的名称name和网址url
         *
         * @return
         */
        @Query("SELECT * FROM historyrecord WHERE hname = :input or hurl = :input")
        LiveData<List<HistoryRecordBean>> loadHistoryRecordByinput(String input);

        /**
         * 根据时间进行查询，查找出同一天的所有历史记录
         *
         * @return
         */
        @Query("SELECT * FROM historyrecord WHERE hdate = :dateString")
        LiveData<List<HistoryRecordBean>> loadHistoryRecordByDate(String dateString);

        /**
         * 模糊搜素
         * @param content 输入内容
         * @return 符合的历史记录
         */
        @Query("select * from historyrecord where hname like '%'||:content||'%' or hurl like '%'||:content||'%'")
        LiveData<List<HistoryRecordBean>> fuzzySearch(String content);

        /**
         * 模糊搜素
         * @param content 输入内容
         * @return 符合的历史记录
         */
        @Query("select * from historyrecord where hname like '%'||:content||'%' or hurl like '%'||:content||'%'")
        List<HistoryRecordBean> fuzzySearchToList(String content);

        @Query("select * from historyrecord")
        List<HistoryRecordBean> getAll();

        /**
         * 项数据库添加数据
         *
         * @param historyRecordList
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(List<HistoryRecordBean> historyRecordList);

        /**
         * 修改数据
         *
         * @param phone
         */
//        @Update()
//        void update(HistoryRecordBean phone);

        /**
         * 删除数据
         *
         * @param historyRecordBean
         */
        @Delete()
        void delete(HistoryRecordBean historyRecordBean);

        /**
         * 根据id删除指定记录
         * @param id
         */
        @Query("delete from historyrecord where id = :id")
        void deleteOne(int id);

        @Query("delete from historyrecord")
        void deleteAll();

        @Query("delete from historyrecord where hdate = :date")
        void deleteTodayHistory(String date);

        @Query("select id from historyrecord")
        List<Integer> getAllId();
}
