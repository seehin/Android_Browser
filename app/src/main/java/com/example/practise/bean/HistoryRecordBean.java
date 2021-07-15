package com.example.practise.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "historyrecord")
public class HistoryRecordBean extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "id") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "hname")
    private String hname;

    @ColumnInfo(name = "hurl")
    private String hurl;

    @ColumnInfo(name = "hicon")
    private String hicon;

    @ColumnInfo(name = "hdate")
    private String hdate;

    public HistoryRecordBean(String hname, String hurl, String hicon, String hdate){
        this.hname = hname;
        this.hurl = hurl;
        this.hicon = hicon;
        this.hdate = hdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
        notifyPropertyChanged(BR.hname);
    }

    @Bindable
    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
        notifyPropertyChanged(BR.hurl);
    }

    @Bindable
    public String getHicon() {
        return hicon;
    }

    public void setIcon(String hicon) {
        this.hicon = hicon;
        notifyPropertyChanged(BR.hicon);
    }

    @Bindable
    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
        notifyPropertyChanged(BR.hdate);
    }

    protected HistoryRecordBean(Parcel in) {
        this.id = in.readInt();
        this.hname = in.readString();
        this.hurl = in.readString();
        this.hicon = in.readString();
        this.hdate = in.readString();
    }

    public static final Creator<HistoryRecordBean> CREATOR = new Creator<HistoryRecordBean>() {
        @Override
        public HistoryRecordBean createFromParcel(Parcel in) {
            return new HistoryRecordBean(in);
        }

        @Override
        public HistoryRecordBean[] newArray(int size) {
            return new HistoryRecordBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.hname);
        dest.writeString(this.hurl);
        dest.writeString(this.hicon);
        dest.writeString(this.hdate);
    }
}
