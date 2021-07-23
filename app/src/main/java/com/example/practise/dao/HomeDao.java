package com.example.practise.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practise.bean.HomeBean;
import com.example.practise.bean.QuickWayBean;

import java.util.List;

@Dao
public interface HomeDao {

    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM home")
    LiveData<List<HomeBean>> gethomeAll();

    /**
     * 根据指定字段模糊查询，根据输入的搜索内容搜索网址的名称name和网址url
     *
     * @return
     */
    @Query("SELECT * FROM home WHERE vname = :input or vurl = :input")
    LiveData<List<HomeBean>> loadHomeByinput(String input);

    /**
     * 项数据库添加数据
     *
     * @param homeList
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HomeBean> homeList);

    /**
     * 修改数据
     *
     * @param home
     */
    @Update()
    void update(HomeBean home);

    /**
     * 删除数据
     *
     * @param HomeBean
     */
    @Delete()
    void delete(HomeBean HomeBean);

    @Query("insert into home (vname,vurl,vicon) values (:vname,:vurl,:vicon)")
    void insert1home(String vname,String vurl,String vicon);

    @Query("select * FROM home")
    List<HomeBean> selectALL();

    @Query("select * FROM home WHERE vurl = :url")
    List<HomeBean> selectByUrl(String url);
}
