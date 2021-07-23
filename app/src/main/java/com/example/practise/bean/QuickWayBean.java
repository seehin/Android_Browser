package com.example.practise.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quickway")
public class QuickWayBean extends BaseObservable implements Parcelable {
    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "id") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "hname")
    private String hname;

    @ColumnInfo(name = "hurl")
    private String hurl;

    @ColumnInfo(name = "hicon")
    private String hicon;

    public QuickWayBean(String hname, String hurl, String hicon) {
        this.hname = hname;
        this.hurl = hurl;
        this.hicon = hicon;
    }

    @Override
    public String toString() {
        return "QuickWayBean{" +
                "id=" + id +
                ", hname='" + hname + '\'' +
                ", hurl='" + hurl + '\'' +
                ", hicon='" + hicon + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
    }

    public String getHicon() {
        return hicon;
    }

    public void setIcon(String hicon) {
        this.hicon = hicon;
    }


    protected QuickWayBean(Parcel in) {
        this.id = in.readInt();
        this.hname = in.readString();
        this.hurl = in.readString();
        this.hicon = in.readString();
    }

    public static final Creator<QuickWayBean> CREATOR = new Creator<QuickWayBean>() {
        @Override
        public QuickWayBean createFromParcel(Parcel in) {
            return new QuickWayBean(in);
        }

        @Override
        public QuickWayBean[] newArray(int size) {
            return new QuickWayBean[size];
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
    }
}
