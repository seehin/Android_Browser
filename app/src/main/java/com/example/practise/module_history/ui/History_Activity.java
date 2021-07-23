package com.example.practise.module_history.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practise.R;
import com.example.practise.bean.HistoryListBean;
import com.example.practise.bean.HistoryRecordBean;
import com.example.practise.module_history.adapter.HistoryAdapter;
import com.example.practise.module_navigation.NavigationActivity;
import com.example.practise.viewmodel.HistoryRecordViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class History_Activity extends AppCompatActivity {

    HistoryRecordViewModel historyRecordViewModel;

    TextView buttonOfCheckBox;

    Button buttonOfHistoryEdit;

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;    /*集合显示控件recyclerview的管理器*/

    HistoryAdapter historyAdapter;  /*控件recyclerview的适配器*/

    ImageView selectAll;

    ImageView deleteHistoryOfChoice;

    ImageView cancelAllSelect;

    RelativeLayout historyPage;

    EditText historySearch;

    Toolbar toolbar;

    ImageView noHistory;

    TextView noHistoryTip;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongConstant", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyRecordViewModel = new ViewModelProvider(this).get(HistoryRecordViewModel.class);
        setContentView(R.layout.activity_history);


        //控件映射到对象
        buttonOfCheckBox = findViewById(R.id.history_edit);
        buttonOfHistoryEdit = findViewById(R.id.button_clear_history);
        recyclerView = findViewById(R.id.list_history);
        selectAll = findViewById(R.id.history_select_all);
        deleteHistoryOfChoice = findViewById(R.id.history_delete);
        cancelAllSelect = findViewById(R.id.history_no_select_all);
        historyPage = findViewById(R.id.history_page);
        historySearch = findViewById(R.id.history_search);
        toolbar = findViewById(R.id.history_toolbar);
        noHistory = findViewById(R.id.no_history_background);
        noHistoryTip = findViewById(R.id.no_history_tip);



         /*----------------------------------------------------------------
         -                   插入初始数据用于测试                          -
        ---------------------------------------------------------------*/
        //initData();

        //添加RecyclerView管理器
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //添加适配器
        historyAdapter = new HistoryAdapter(this,sortForList(historyRecordViewModel.getAll()));
        recyclerView.setAdapter(historyAdapter);


        //实时更新recycler view的状态并展示出来
        historyRecordViewModel.getHistoryRecordAll().observe(this, historyRecordBeans -> {
            if (historyRecordBeans.size() > 0){
                noHistory.setVisibility(View.GONE);
                noHistoryTip.setVisibility(View.GONE);
            }else {
                noHistory.setVisibility(View.VISIBLE);
                noHistoryTip.setVisibility(View.VISIBLE);
            }
            Collections.sort(historyRecordBeans);
            historyAdapter.setHistoryRecordBeans(historyRecordBeans);
            historyAdapter.setAllRecords(sortForList(historyRecordBeans));

            //刷新视图
            historyAdapter.notifyDataSetChanged();
        });

        //长按事件
        historyAdapter.setOnItemLongClickListener((view, position) -> {
            if (historyAdapter.getIsClickAllow()) {
                onLongClickShow(view,position);
            }else{
                Toast.makeText(this,"复选框下不可以长按",Toast.LENGTH_SHORT).show();
            }
        });

        //点击事件
        historyAdapter.setOnItemClickListener((view, section, position) -> {
            if (historyAdapter.getIsClickAllow()){
                onClickShow(view);
            }else{
                Toast.makeText(this,"复选框下不可以点击",Toast.LENGTH_SHORT).show();
            }
        });

        //点击编辑按钮
        buttonOfCheckBox.setOnClickListener(v -> showCheckBox());

        //点击清除历史记录...按钮
        buttonOfHistoryEdit.setOnClickListener(this::clickHistoryEdit);

        //点击删除按钮
        selectAll.setOnClickListener(v -> {
            selectAll();
        });

        //点击删除按钮
        deleteHistoryOfChoice.setOnClickListener(v->{
            deleteHistoryOfChoice();
        });

        //点击取消全选按钮
        cancelAllSelect.setOnClickListener(v->cancelSelectAll());

        //点击非输入框部分隐藏键盘
        historyPage.setOnClickListener(v -> {
            if (v.getId() == R.id.history_page) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        //点击输入框
        historySearch.setOnClickListener(v -> {
            Intent intent = new Intent(History_Activity.this, History_Search_Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        });

        //关闭历史记录
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.close_history_page:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 点击清除历史记录按键触发事件
     * @param view 视图
     * @param position item标识
     */
    @SuppressLint("NonConstantResourceId")
    private void onLongClickShow(View view, int position){
        PopupMenu popupMenu = new PopupMenu(History_Activity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.history_delete, popupMenu.getMenu());

        //弹出式菜单的菜单项点击事件
        popupMenu.setOnMenuItemClickListener(item -> {
            historyAdapter.notifyItemRemoved(position);
            TextView textView;
            switch (item.getItemId()) {
                case R.id.removeItem:
                    historyAdapter.notifyItemRemoved(position);
                    textView = view.findViewById(R.id.list_history_id);
                    historyRecordViewModel.deleteOneHistoryRecord(
                            Integer.parseInt(textView.getText().toString())
                    );
                    break;
                case R.id.historyCopyItem:
                    historyAdapter.notifyDataSetChanged();
                    ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    textView = view.findViewById(R.id.list_history_url);
                    ClipData clipData = ClipData.newPlainText(null, textView.getText().toString());
                    clip.setPrimaryClip(clipData);
                    Toast.makeText(History_Activity.this,"网址已复制到粘贴板",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    /**
     * 单击item触发事件
     * @param view 视图
     */
    private void onClickShow(View view){
        //添加点击事件
        /*TextView url = view.findViewById(R.id.list_history_url);
        Uri uri = Uri.parse(url.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);*/
        TextView url = view.findViewById(R.id.list_history_url);
        Intent intent = new Intent(History_Activity.this, NavigationActivity.class);
        intent.putExtra("history_url",url.getText().toString());
        startActivity(intent);
    }

    /**
     * 点击历史记录页面的右下角的”编辑“按键
     * @param view 视图
     */
    @SuppressLint("NonConstantResourceId")
    private void clickHistoryEdit(View view) {
        System.out.println("========================");
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.history_edit, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(item -> {
            //点击逻辑
            switch (item.getItemId()){
                case R.id.remove_today_history:
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String time = simpleDateFormat.format(date);
                    historyRecordViewModel.deleteTodayHistory(time);
                    break;
                case R.id.remove_all_history:
                    historyRecordViewModel.deleteAll();
                    break;
                default:
                    break;
            }
            return false;
        });

        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(menu -> {
            //关闭后的逻辑
            /*Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT).show();*/
        });
        popupMenu.show();
    }

    /**
     * 编辑 <--------->取消
     */
    private void showEditOrCancel(){
        if (historyAdapter.getIsShowCheckBox()){
            //复选框出现了，则将<编辑>变成<取消>
            buttonOfCheckBox.setText("取消");
        }else {
            buttonOfCheckBox.setText("编辑");
        }
    }

    /**
     * 全选 <----------> 全不选  <---------> 全不显示
     */
    private void showAllSelectOrCancel(){
        if (historyAdapter.getIsShowNoSelect() && historyAdapter.getIsShowCheckBox()){
            //点击多选后显示取消全选按钮
            cancelAllSelect.setVisibility(View.VISIBLE);
            selectAll.setVisibility(View.GONE);
        }else if (!historyAdapter.getIsShowNoSelect() && historyAdapter.getIsShowCheckBox()) {
            //点击取消全选后显示全选按钮
            cancelAllSelect.setVisibility(View.GONE);
            selectAll.setVisibility(View.VISIBLE);
        }else {
            //点击取消按钮后都不显示多选图标
            cancelAllSelect.setVisibility(View.GONE);
            selectAll.setVisibility(View.GONE);
        }
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 打开复选框处理逻辑
     */
    private void showCheckBox(){
        //初始化复选框
        historyAdapter.initCheckBox();
        //打开复选框
        historyAdapter.setIsShowCheckBox();
        //复选框点击
        selectItem();
        //关闭其他触控事件
        historyAdapter.setIsClickAllow();
        //改变该按钮的文字
        showEditOrCancel();
        //将一些按钮显示出来
        showEnable();
        //
        showAllSelectOrCancel();
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 点击了"编辑"之后的一些控件是否显示
     */
    private void showEnable(){
        if (!historyAdapter.getIsClickAllow()){
            recyclerView.setEnabled(false);
            buttonOfHistoryEdit.setEnabled(false);
            buttonOfHistoryEdit.setText("");
            selectAll.setVisibility(View.VISIBLE);
            deleteHistoryOfChoice.setVisibility(View.VISIBLE);

        }else{
            recyclerView.setEnabled(true);
            buttonOfHistoryEdit.setEnabled(true);
            buttonOfHistoryEdit.setText(R.string.clear_history);
            selectAll.setVisibility(View.GONE);
            deleteHistoryOfChoice.setVisibility(View.GONE);
        }
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 点击了复选框则将map中对应的boolean设为true，否则默认为false
     */
    public void selectItem(){
        historyAdapter.setOnItemClickListener((view, section, position) -> {
            historyAdapter.setSelectItem(section, position);
        });
    }

    /**
     * 点击全选按钮
     */
    private void selectAll(){
        historyAdapter.setListOfDelete(historyRecordViewModel.getAllIdOfHistory());
        historyAdapter.selectAll();
        //点击了全选后，该按钮变成全不选按钮
        historyAdapter.setIsShowNoSelect();
        showAllSelectOrCancel();
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 点击取消全选的逻辑
     */
    public void cancelSelectAll(){
        historyAdapter.initCheckBox();
        showAllSelectOrCancel();
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 点击删除后触发的逻辑
     */
    public void deleteHistoryOfChoice(){
        if (historyAdapter.getListOfDelete().size() != 0){
            for (Integer id : historyAdapter.getListOfDelete()) {
                historyRecordViewModel.deleteOneHistoryRecord(id);
            }
            showCheckBox();
        }else{
            Toast.makeText(this,"请选择历史记录",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将拿到的数据进行排序，排好序后进行分组，再对分组进行排序，最后返回一个有序的分组
     * @param beans
     * @return
     */
    private List<HistoryListBean> sortForList(List<HistoryRecordBean> beans){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);

        List<HistoryListBean> groups = new LinkedList<>();      /*排好序的分组*/
        if (beans != null && beans.size()!=0) {
            HistoryListBean recordOfGroup = new HistoryListBean();    /*某一天的分组*/
            String time = beans.get(0).getHdate();      /*某一天的历史时间*/
            //对每个排好序的记录进行分组
            for (HistoryRecordBean h : beans) {
                if (time.equals(h.getHdate())){
                    //时间同一天，则放入同一组中
                    recordOfGroup.getListOfDay().add(h);
                }else {
                    if (!time.equals("")){
                        //时间不同，则将上一组存入groups中
                        if (time.equals(today)){
                            //加上今天的标识
                            recordOfGroup.setTimeToday(time);
                            groups.add(recordOfGroup);
                        }else {
                            recordOfGroup.setTimeAndSort(time);
                            groups.add(recordOfGroup);
                        }
                    }
                    //新建一组来存另一天的历史记录
                    recordOfGroup = new HistoryListBean();
                    //将该历史记录添入新的分组
                    time = h.getHdate();
                    recordOfGroup.getListOfDay().add(h);
                }
            }
            if (!time.equals("")){
                //时间不同，则将上一组存入groups中
                System.out.println("<<<<<<"+time);
                if (time.equals(today)){
                    //加上今天的标识
                    recordOfGroup.setTimeToday(time);
                }else {
                    recordOfGroup.setTimeAndSort(time);
                }
                groups.add(recordOfGroup);
            }
        }
        //将分组排序好
        Collections.sort(groups);
        return groups;
    }

    /**
     * 初始化一些测试数据
     */
    private void initData(){
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            historyRecordViewModel.insertHistoryRecord("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico",simpleDateFormat.parse("2021-07-18"));
            historyRecordViewModel.insertHistoryRecord("墨刀 - 百度","https://www.baidu.com/s?wd=%E5%A2%A8%E5%88%80","https://www.baidu.com/favicon.ico",simpleDateFormat.parse("2021-07-18"));
            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-18"));
            historyRecordViewModel.insertHistoryRecord("Github","https://github.com/","https://github.com/favicon.ico",simpleDateFormat.parse("2021-07-18"));

            historyRecordViewModel.insertHistoryRecord("Github","https://github.com/","https://github.com/favicon.ico",simpleDateFormat.parse("2021-07-19"));
            historyRecordViewModel.insertHistoryRecord("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-19"));
            historyRecordViewModel.insertHistoryRecord("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico",simpleDateFormat.parse("2021-07-19"));
            historyRecordViewModel.insertHistoryRecord("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-19"));

            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-20"));
            historyRecordViewModel.insertHistoryRecord("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-20"));
            historyRecordViewModel.insertHistoryRecord("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico",simpleDateFormat.parse("2021-07-20"));
            historyRecordViewModel.insertHistoryRecord("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-20"));

            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-21"));
            historyRecordViewModel.insertHistoryRecord("Github","https://github.com/","https://github.com/favicon.ico",simpleDateFormat.parse("2021-07-21"));
            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-21"));
            historyRecordViewModel.insertHistoryRecord("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico",simpleDateFormat.parse("2021-07-21"));
            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse("2021-07-21"));

            historyRecordViewModel.insertHistoryRecord("墨刀 - 百度","https://www.baidu.com/s?wd=%E5%A2%A8%E5%88%80","https://www.baidu.com/favicon.ico",simpleDateFormat.parse(simpleDateFormat.format(date)));
            historyRecordViewModel.insertHistoryRecord("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico",simpleDateFormat.parse(simpleDateFormat.format(date)));
            historyRecordViewModel.insertHistoryRecord("Github","https://github.com/","https://github.com/favicon.ico",simpleDateFormat.parse(simpleDateFormat.format(date)));

        }catch (ParseException e){
            System.out.println("插入测试数据失败");
        }
    }

    /*class GameThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // 使用postInvalidate可以直接在线程中更新界面
                recyclerView.postInvalidate();
            }
        }
    }*/

}