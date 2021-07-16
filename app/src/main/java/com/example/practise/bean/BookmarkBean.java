package com.example.practise.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark")
public class BookmarkBean extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "id") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "bname")
    private String bname;

    @ColumnInfo(name = "burl")
    private String burl;//文件夹没有网址，记为none

    @ColumnInfo(name = "bicon")
    private String bicon;//文件夹使用的是默认图标，指定路径

    @ColumnInfo(name = "bnum")
    private int bnum;//记录文件夹中有多少个已收藏的网址，文件夹不记录在内，如果没有，记为0

    //每次添加书签时，默认present=0，isFolder=0，upper=-1，sort=0层的书签数+1
    @ColumnInfo(name = "isFolder")
    private int isFolder;//0代表网址，1代表文件夹

    @ColumnInfo(name = "upper")
    private int upper;//上一层文件夹的id，第一层的书签由于没有上一层，记-1

    @ColumnInfo(name = "present")
    private int present;//该书签（网址或者文件夹）位于第几层，从0开始计数

    @ColumnInfo(name = "sort")
    private int sort ;//排序码，present一样的情况下，从0开始计数

    public BookmarkBean(String bname, String burl, String bicon, int bnum, int isFolder, int upper, int present, int sort) {
        this.bname = bname;
        this.burl = burl;
        this.bicon = bicon;
        this.bnum = bnum;
        this.isFolder = isFolder;
        this.upper = upper;
        this.present = present;
        this.sort = sort;
    }

    @Ignore
    public BookmarkBean(int id, String bname, String burl, String bicon, int bnum, int isFolder, int upper, int present, int sort) {
        this.id = id;
        this.bname = bname;
        this.burl = burl;
        this.bicon = bicon;
        this.bnum = bnum;
        this.isFolder = isFolder;
        this.upper = upper;
        this.present = present;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
        notifyPropertyChanged(BR.bname);
    }

    @Bindable
    public int getBnum() {
        return bnum;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
        notifyPropertyChanged(BR.bnum);
    }

    @Bindable
    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
        notifyPropertyChanged(BR.burl);
    }

    @Bindable
    public String getBicon() {
        return bicon;
    }

    public void setBicon(String bicon) {
        this.bicon = bicon;
        notifyPropertyChanged(BR.bicon);
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    protected BookmarkBean(Parcel in) {
        this.id = in.readInt();
        this.bname = in.readString();
        this.burl = in.readString();
        this.bicon = in.readString();
        this.isFolder = in.readInt();
        this.upper = in.readInt();
        this.present = in.readInt();
        this.sort = in.readInt();
    }

    public static final Creator<BookmarkBean> CREATOR = new Creator<BookmarkBean>() {
        @Override
        public BookmarkBean createFromParcel(Parcel in) {
            return new BookmarkBean(in);
        }

        @Override
        public BookmarkBean[] newArray(int size) {
            return new BookmarkBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.bname);
        dest.writeString(this.burl);
        dest.writeString(this.bicon);
        dest.writeInt(this.isFolder);
        dest.writeInt(this.upper);
        dest.writeInt(this.present);
        dest.writeInt(this.sort);
    }
}
