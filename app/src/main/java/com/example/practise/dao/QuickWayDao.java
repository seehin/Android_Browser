package com.example.practise.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.practise.bean.QuickWayBean;

import java.util.List;

@Dao
public interface QuickWayDao {

        /**
         * 查询所有
         *
         * @return
         */
        @Query("SELECT * FROM quickway")
        LiveData<List<QuickWayBean>> getQuickWayAll();

        /**
         * 项数据库添加数据
         *
         * @param quickWayList
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertQuickWay(List<QuickWayBean> quickWayList);

        /**
         * 删除数据
         *
         * @param quickWayBean
         */
        @Delete()
        void delete(QuickWayBean quickWayBean);


        /**
         * 查询数据量
         *
         * @return
         */
        @Query("SELECT count(*) FROM quickway")
        int getCount();

        /**
         * 根据url搜索
         *
         * @return
         */
        @Query("SELECT * FROM quickway WHERE hurl = :url")
        List<QuickWayBean> selectByUrl(String url);
}
