package com.example.practise.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.dao.BookmarkDao;
import com.example.practise.dao.HistoryRecordDao;
import com.example.practise.utils.DataConversionFactory;

@Database(entities = {HistoryRecordBean.class, BookmarkBean.class}, version = 1, exportSchema = false)
public abstract class BrowserDatabase extends RoomDatabase {

    public static BrowserDatabase getDefault(Context context) {
        return buildDatabase(context);
    }

    private static BrowserDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), BrowserDatabase.class, "browser.db")
                .allowMainThreadQueries()
                .build();
    }

    public abstract HistoryRecordDao getHistoryRecordDao();

    public abstract BookmarkDao getBookmarkDao();
}
