package com.example.practise.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HistoryListBean implements Serializable,Comparable<HistoryListBean> {

    /**
     * 该属性用于通过重写CompareTo方法来做集合排序，且该属性是时间的的int的形式，
     * 例如time=2021-09-01，则sort=20210901
     */
    private int sort;

    private String time;

    private List<HistoryRecordBean> listOfDay = new LinkedList<>();

    public void setTimeAndSort(String time){
        this.time = time;
        //在定义时间时就定义了sort属性
        this.sort = Integer.parseInt(time.replace("-",""));
    }

    public void setTimeToday(String time){
        this.time = "今天 - "+time;
        this.sort = Integer.parseInt(time.replace("-",""));
    }

    public void setListOfDay(List<HistoryRecordBean> listOfDay){
        this.listOfDay = listOfDay;
    }

    public int getSort(){
        return this.sort;
    }

    public String getTime(){
        return this.time;
    }

    public List<HistoryRecordBean> getListOfDay(){
        return this.listOfDay;
    }

    @Override
    public int compareTo(HistoryListBean h) {
        return h.getSort() - this.sort;
    }

    @Override
    public String toString() {
        return "HistoryListBean{" +
                "sort=" + sort +
                ", time='" + time + '\'' +
                ", listOfDay=" + listOfDay +
                '}';
    }
}
