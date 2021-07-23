package com.example.practise.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "home")
public class HomeBean extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "id") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "vname")
    private String vname;

    @ColumnInfo(name = "vurl")
    private String vurl;//文件夹没有网址，记为none

    @ColumnInfo(name = "vicon")
    private String vicon;//文件夹使用的是默认图标，指定路径

    @Ignore
    public HomeBean(){};



    public HomeBean(String vname, String vurl, String vicon) {
        this.vname = vname;
        this.vurl = vurl;
        this.vicon = vicon;


    }

    @Ignore
    public HomeBean(int id, String vname, String vurl, String vicon) {
        this.id = id;
        this.vname = vname;
        this.vurl = vurl;
        this.vicon = vicon;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;

    }





    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;

    }


    public String getVicon() {
        return vicon;
    }

    public void setVicon(String vicon) {
        this.vicon = vicon;

    }

    protected HomeBean(Parcel in) {
        this.id = in.readInt();
        this.vname = in.readString();
        this.vurl = in.readString();
        this.vicon = in.readString();
    }

    public static final Creator<HomeBean> CREATOR = new Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel in) {
            return new HomeBean(in);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.vname);
        dest.writeString(this.vurl);
        dest.writeString(this.vicon);
    }

    @Override
    public String toString() {
        return "HomeBean{"+this.id+this.vname+this.vurl+this.vicon+"}";
    }
}
