package com.example.practise.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practise.bean.HomeBean;
import com.example.practise.repository.HomeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel{
    private HomeRepository homeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
    }
    public LiveData<List<HomeBean>> gethomeAll(){
        return homeRepository.gethomeall();
    }
    public LiveData<List<HomeBean>> getHomeByInput(String input){
        return homeRepository.getHomeByinput(input);
    }

    public List<HomeBean> search_url(String url){
        return homeRepository.search_url(url);
    }

    public void insertHome(HomeBean... homeBeans){
        homeRepository.insertHome(homeBeans);
    }

    public void insert1Home(String vname,String vurl,String vicon){
        homeRepository.insert1Home(vname,vurl,vicon);
    }

    public void insertAll(List<HomeBean> list){
        homeRepository.insertAll(list);
    }

    public void delete1Home(HomeBean homeBean){homeRepository.delete1Home(homeBean);}

    public  List<HomeBean> selectALL(){
        return homeRepository.selectALL();
    }
}
