package com.example.practise.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.repository.HistoryRecordRepository;

import java.util.Date;
import java.util.List;

public class HistoryRecordViewModel extends AndroidViewModel {

    private final HistoryRecordRepository historyRecordRepository;

    public HistoryRecordViewModel(@NonNull Application application) {
        super(application);
        historyRecordRepository = new HistoryRecordRepository(application);
    }

    //获取所有历史记录
    public LiveData<List<HistoryRecordBean>> getHistoryRecordAll(){
        return historyRecordRepository.getHistoryRecordAll();
    }

    public LiveData<List<HistoryRecordBean>> getHistoryRecordByInput(String input){
        return historyRecordRepository.getHistoryByInput(input);
    }

    public LiveData<List<HistoryRecordBean>> getHistoryRecordByInput(Date hdate){
        return historyRecordRepository.getHistoryByTime(hdate);
    }

    public void insertHistoryRecord(String hname, String hurl, String hicon, Date hdate){
        historyRecordRepository.insertHistoryRecord(hname,hurl,hicon,hdate);
    }

    public void deleteHistoryRecord(HistoryRecordBean... historyRecordBeans){
        historyRecordRepository.deleteHistoryRecord(historyRecordBeans);
    }

    public void deleteOneHistoryRecord(int id){
        historyRecordRepository.deleteHistoryRecordById(id);
    }

    public void deleteAll(){
        historyRecordRepository.deleteAll();
    }

    public void deleteTodayHistory(String date){
        historyRecordRepository.deleteTodayHistory(date);
    }

    public List<Integer> getAllIdOfHistory(){
        return historyRecordRepository.getAllId();
    }

    public LiveData<List<HistoryRecordBean>> getFuzzySearchInfo(String content){
        return historyRecordRepository.getFuzzySearch(content);
    }

    public List<HistoryRecordBean> getFuzzySearchInfoToList(String content){
        return historyRecordRepository.getFuzzySearchToList(content);
    }

    public List<HistoryRecordBean> getAll(){
        return historyRecordRepository.getAll();
    }
}
