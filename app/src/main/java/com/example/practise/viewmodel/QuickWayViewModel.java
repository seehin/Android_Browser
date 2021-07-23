package com.example.practise.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practise.bean.QuickWayBean;
import com.example.practise.repository.QuickWayRepository;

import java.util.List;

public class QuickWayViewModel extends AndroidViewModel {
    private QuickWayRepository quickWayRepository;
    public QuickWayViewModel(@NonNull Application application) {
        super(application);
        quickWayRepository = new QuickWayRepository(application);
    }

    public LiveData<List<QuickWayBean>> getHistoryRecordAll(){
        return quickWayRepository.getQuickWayAll();
    }

    public List<QuickWayBean> selectByUrl(String url){
        return quickWayRepository.selectByUrl(url);
    }

    public void insertQuickWay(String hname, String hurl, String hicon){
        quickWayRepository.insertQuickWay(hname,hurl,hicon);
    }

    public void deleteHistoryRecord(QuickWayBean... quickWayBeans){
        quickWayRepository.deleteQuickWay(quickWayBeans);
    }

    public int getCount(){
        return quickWayRepository.getCount();
    }

}
