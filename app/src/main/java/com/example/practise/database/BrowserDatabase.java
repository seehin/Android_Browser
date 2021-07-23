package com.example.practise.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.bean.HomeBean;
import com.example.practise.bean.QuickWayBean;
import com.example.practise.dao.BookmarkDao;
import com.example.practise.dao.HistoryRecordDao;
import com.example.practise.dao.HomeDao;
import com.example.practise.dao.QuickWayDao;

@Database(entities = {HistoryRecordBean.class, BookmarkBean.class, QuickWayBean.class, HomeBean.class}, version = 1, exportSchema = false)
public abstract class BrowserDatabase extends RoomDatabase {
    private static BrowserDatabase INSTANCE;

    public static synchronized BrowserDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),BrowserDatabase.class,"browser.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract HistoryRecordDao getHistoryRecordDao();

    public abstract BookmarkDao getBookmarkDao();

    public abstract QuickWayDao getQuickWayDao();

    public abstract HomeDao getHomeDao();
}
