package com.example.practise.module_history.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practise.R;
import com.example.practise.module_history.adapter.HistorySearchAdapter;
import com.example.practise.module_navigation.NavigationActivity;
import com.example.practise.viewmodel.HistoryRecordViewModel;

import java.util.Collections;

public class History_Search_Activity extends AppCompatActivity {

    HistoryRecordViewModel historyRecordViewModel;

    EditText historySearch;

    TextView cancelSearch;

    RelativeLayout historyPage;

    HistorySearchAdapter historySearchAdapter;

    RecyclerView recordOfSearch;

    String content;

    //ImageView delete;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);
        historyRecordViewModel = new ViewModelProvider(this).get(HistoryRecordViewModel.class);



        historySearch = findViewById(R.id.history_page_search);
        cancelSearch = findViewById(R.id.history_page_cancel);
        historyPage = findViewById(R.id.history_search_page);
        recordOfSearch = findViewById(R.id.history_search_page_list);
        //delete = findViewById(R.id.search_record_delete);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recordOfSearch.setLayoutManager(linearLayoutManager);


        historySearchAdapter = new HistorySearchAdapter(this);
        recordOfSearch.setAdapter(historySearchAdapter);

        historySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("--------------"+s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("+++++++++++++++++++++++++++++++++:"+s.toString());
                if (s.toString().equals("")||s.toString()==null){
                    historySearchAdapter.clearHistoryRecordBeans();
                }else{
                    historyRecordViewModel.getFuzzySearchInfo(s.toString()).observe(History_Search_Activity.this,historyRecordBeans -> {
                        Collections.sort(historyRecordBeans);
                        historySearchAdapter.setHistoryRecordBeans(historyRecordBeans);
                    });
                }
                //刷新视图
                historySearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                historySearch.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
                        disableShowInput(historySearch);
                    }
                    return false;
                });
            }


        });

        cancelSearch.setOnClickListener(v->{
            disableShowInput(historySearch);
            finish();
        });


        //点击跳转
        historySearchAdapter.setOnItemClickListener((view, position) -> {
            if (view.getId() == R.id.search_record_delete){
                TextView textView = view.findViewById(R.id.Search_list_history_id);
                int id = Integer.parseInt(textView.getText().toString());
                historyRecordViewModel.deleteOneHistoryRecord(id);
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }else {
                TextView url = view.findViewById(R.id.Search_list_history_url);
                Intent intent = new Intent(History_Search_Activity.this, NavigationActivity.class);
                intent.putExtra("history_url",url.getText().toString());
                startActivity(intent);
            }
        });

        //点击非输入框部分隐藏键盘
        historyPage.setOnClickListener(v -> {
            if (v.getId() == R.id.history_page) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        showInput(historySearch);

        //显示出搜索内容
        content = historySearch.getText().toString();

        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = v.findViewById(R.id.Search_list_history_id);
                int id = Integer.parseInt(textView.getText().toString());
                historyRecordViewModel.deleteOneHistoryRecord(id);
            }
        });*/

    }

    public static void showInput(final EditText et) {
        et.post(() -> {
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        });
    }

    public static void disableShowInput(EditText view) {
        //关闭输入法键盘，如果需要
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}