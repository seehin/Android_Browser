package com.example.practise.module_home.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.practise.R;
import com.example.practise.base.ActivityCollecter;
import com.example.practise.base.BaseActivity;
import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.HomeBean;
import com.example.practise.databinding.NavigationActivityBinding;
import com.example.practise.generated.callback.OnClickListener;
import com.example.practise.module_bookmark.BookmarkActivity;
import com.example.practise.module_history.ui.History_Activity;
import com.example.practise.module_navigation.NavigationActivity;
import com.example.practise.module_windows.PagerActivity;
import com.example.practise.utils.UrlUtil;
import com.example.practise.utils.WebViewFragment;
import com.example.practise.utils.WebViewHelper;
import com.example.practise.viewmodel.HomeViewModel;
import com.example.practise.module_navigation.NavigationActivity;
import com.example.practise.module_history.ui.History_Activity;
import com.example.practise.viewmodel.HistoryRecordViewModel;


import java.util.Date;
import java.util.List;


public class Home_Activity extends BaseActivity {


    HomeViewModel homeViewModel;

    Context context = this;

    TextView winNum;

    ImageView opWin;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        winNum = findViewById(R.id.winnum);
        winNum.setText(WebViewHelper.webList.size()+1+"");
        Toolbar toolbar = findViewById(R.id.toolbar);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setSupportActionBar(toolbar);

        opWin = findViewById(R.id.opwin);
        opWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("多窗口");
                WebViewHelper.headWebView = null;
                if (!WebViewHelper.isExist) {

                        Log.e("houxl", "生成空窗口");
                        WebViewHelper.webList.add(new WebViewFragment(null, myShot(Home_Activity.this)));
                        WebViewHelper.isExist = true;
                }

                Intent i = new Intent(Home_Activity.this, PagerActivity.class);
                WebViewHelper.currentBundle = null;
                startActivity(i);
            }
        });
        EditText searchText;
        searchText = findViewById(R.id.homesearch_text);
        createEditorActionListener(searchText);
        /*----------------------------------------------------------------
         -                   插入初始数据用于测试                          -
        ---------------------------------------------------------------*/
//            homeViewModel.insert1Home("墨刀 - 百度","https://www.baidu.com/s?wd=%E5%A2%A8%E5%88%80","https://www.baidu.com/favicon.ico");
//            homeViewModel.insert1Home("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico");
//            homeViewModel.insert1Home("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico");
//            homeViewModel.insert1Home("Github","https://github.com/","https://github.com/favicon.ico");
//            homeViewModel.insert1Home("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico");

//        List<HomeBean> list = new ArrayList<>();
//        HomeBean homebean1 =new HomeBean("墨刀 - 百度","https://www.baidu.com/s?wd=%E5%A2%A8%E5%88%80","https://www.baidu.com/favicon.ico");
//        HomeBean homebean2 =new HomeBean("墨刀-是一款在线原型设计与远程协作平台","https://modao.cc/","https://modao.cc/favicon.ico");
//        HomeBean homebean3 =new HomeBean("墨刀 - 远程办公好帮手 在线产品原型设计与协作平台","https://modao.cc/embed/auth_box?type=signup","https://modao.cc/favicon.ico");
//        HomeBean homebean4 =new HomeBean("Github","https://github.com/","https://github.com/favicon.ico");
//        HomeBean homebean5 =new HomeBean("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili官方","https://www.bilibili.com/","https://www.bilibili.com/favicon.ico");
//
//        list.add(homebean1 );
//        list.add(homebean2 );
//        list.add(homebean3 );
//        list.add(homebean4 );
//        list.add(homebean5 );
//
//        homeViewModel.insertAll(list);


//        RecyclerView recyclerView = findViewById(R.id.quick_visit);
//        //添加RecyclerView管理器
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
//        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        HomeAdapter homeAdapter = new HomeAdapter(this);
//        recyclerView.setAdapter(homeAdapter);
//
////        homeAdapter.setHomeBeans(list);
////        homeAdapter.notifyDataSetChanged();
//
//       homeViewModel.gethomeAll().observe(this, new Observer<List<HomeBean>>() {
//            @Override
//            public void onChanged(List<HomeBean> homeBeans) {
//                homeAdapter.setHomeBeans(homeBeans);
//               homeAdapter.notifyDataSetChanged();
//           }
//        });


        homeViewModel.gethomeAll().observe(this, new Observer<List<HomeBean>> () {
            @Override
            public void onChanged(List<HomeBean> homeBeans) {

                int num_item = 0;
                int unit_id ;

                int id_id;
                int vname_id ;
                int viconUrl_id;
                int vicon_id ;
                int vurl_id;

                String url_vicon;

                TextView id;
                TextView view_vname;
                ImageView view_vicon;
                TextView view_iconUrl;
                TextView view_vurl;

                HomeBean tempHBean;


                num_item = homeBeans.size();
                for( unit_id = 1 ; unit_id <= 8; unit_id++) {

                    if(unit_id <= num_item){
                        id_id = getResources().
                                getIdentifier("quick_visit_id_" + unit_id,"id",context.getPackageName());
                        vname_id = getResources().
                                getIdentifier("quick_visit_name_" + unit_id,"id",context.getPackageName());
                        vicon_id = getResources().
                                getIdentifier("quick_visit_icon_" + unit_id,"id",context.getPackageName());
                        viconUrl_id = getResources().
                                getIdentifier("quick_visit_iconUrl_" + unit_id,"id",context.getPackageName());
                        vurl_id = getResources().
                                getIdentifier("quick_visit_url_" + unit_id,"id",context.getPackageName());

                        id = findViewById(id_id);
                        view_vname = findViewById(vname_id);
                        view_iconUrl = findViewById(viconUrl_id);
                        view_vicon = findViewById(vicon_id);
                        view_vurl = findViewById(vurl_id);

                        tempHBean = homeBeans.get(unit_id - 1);

                        id.setText(String.valueOf(tempHBean.getId()));
                        view_vname.setText(tempHBean.getVname());
                        view_vurl.setText(tempHBean.getVurl());
                        url_vicon = tempHBean.getVicon();
                        view_iconUrl.setText(url_vicon);

                        Glide.with(context)
                                .load(url_vicon)
                                .into(view_vicon);
                    } else {
                        id_id = getResources().
                                getIdentifier("quick_visit_id_" + unit_id,"id",context.getPackageName());
                        vname_id = getResources().
                                getIdentifier("quick_visit_name_" + unit_id,"id",context.getPackageName());
                        vicon_id = getResources().
                                getIdentifier("quick_visit_icon_" + unit_id,"id",context.getPackageName());
                        viconUrl_id = getResources().
                                getIdentifier("quick_visit_iconUrl_" + unit_id,"id",context.getPackageName());
                        vurl_id = getResources().
                                getIdentifier("quick_visit_url_" + unit_id,"id",context.getPackageName());

                        id = findViewById(id_id);
                        view_vname = findViewById(vname_id);
                        view_iconUrl = findViewById(viconUrl_id);
                        view_vicon = findViewById(vicon_id);
                        view_vurl = findViewById(vurl_id);

                        id.setText(String.valueOf(0));
                        view_vname.setText(R.string.null_quickvisit);
                        view_vurl.setText(R.string.NULL);
                        view_iconUrl.setText(R.string.NULL);
                        view_vicon.setImageResource(R.drawable.home_add);
                    }
                }
           }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        WebViewHelper.currentBundle = outState;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_bookmark:
                Intent bintent = new Intent(Home_Activity.this, BookmarkActivity.class);
                startActivity(bintent);
                break;

            case R.id.home_history:
                Intent intent = new Intent(Home_Activity.this, History_Activity.class);
                startActivity(intent);
                break;

            case R.id.home_refresh:
                Intent slefintent = new Intent(Home_Activity.this, Home_Activity.class);
                break;
            default:
                break;
        }
        return true;

    }

    public void startPage(String url){
        Intent intent = new Intent(Home_Activity.this, NavigationActivity.class);
        intent.putExtra("home_url",url);
        startActivity(intent);
    }

    public void createEditorActionListener(EditText searchText){
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent sintent = new Intent(Home_Activity.this, NavigationActivity.class);
                    sintent.putExtra("home_url", searchText.getText().toString());
                    startActivity(sintent);
                }
                return true;
            }
        });
    }



    public void createLongClick( View view ,TextView id , TextView name ,TextView iconUrl,ImageView icon,TextView url){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder( context );
                builder.setTitle( "您确定删除这个快捷访问网址吗？" );//标题
                builder.setNegativeButton( "取消",null );//null代表不作任何操作，只是消失对话框
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeBean homeBean = new HomeBean();

                        int value_id = Integer.parseInt(id.getText().toString());
                        String value_name = name.getText().toString();
                        String value_iconUrl = iconUrl.getText().toString();
                        String value_url = url.getText().toString();

                        homeBean.setId(value_id);
                        homeBean.setVname(value_name);
                        homeBean.setVicon(value_iconUrl);
                        homeBean.setVurl(value_url);
                        System.out.println(homeBean.toString());

                        homeViewModel.delete1Home(homeBean);


                        //homeViewModel.notify();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    public void createClick(String url ,View view ){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPage(url);
            }
        });
    }

    public void unit_click(View view){

        TextView id;
        TextView name ;
        ImageView icon;
        TextView iconUrl;
        TextView url;
        String temp_url;


        switch (view.getId()){
            case R.id.quick_visit_unit_1:{

                url = findViewById(R.id.quick_visit_url_1);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_1);
                    name = findViewById(R.id.quick_visit_name_1);
                    icon = findViewById(R.id.quick_visit_icon_1);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_1);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }

            }break;

            case R.id.quick_visit_unit_2:{
                url = findViewById(R.id.quick_visit_url_2);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {
                    id = findViewById(R.id.quick_visit_id_2);
                    name = findViewById(R.id.quick_visit_name_2);
                    icon = findViewById(R.id.quick_visit_icon_2);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_2);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_3:{
                url = findViewById(R.id.quick_visit_url_3);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_3);
                    name = findViewById(R.id.quick_visit_name_3);
                    icon = findViewById(R.id.quick_visit_icon_3);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_3);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_4:{
                url = findViewById(R.id.quick_visit_url_4);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {
                    id = findViewById(R.id.quick_visit_id_4);
                    name = findViewById(R.id.quick_visit_name_4);
                    icon = findViewById(R.id.quick_visit_icon_4);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_4);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_5:{
                url = findViewById(R.id.quick_visit_url_5);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_5);
                    name = findViewById(R.id.quick_visit_name_5);
                    icon = findViewById(R.id.quick_visit_icon_5);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_5);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_6:{

                url = findViewById(R.id.quick_visit_url_6);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_6);
                    name = findViewById(R.id.quick_visit_name_6);
                    icon = findViewById(R.id.quick_visit_icon_6);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_6);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_7:{
                url = findViewById(R.id.quick_visit_url_7);
                temp_url = url.getText().toString();

                if(!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_7);
                    name = findViewById(R.id.quick_visit_name_7);
                    icon = findViewById(R.id.quick_visit_icon_7);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_7);

                    createClick(temp_url,view);
                    createLongClick(view,id,name,iconUrl,icon,url);
                }
            }break;

            case R.id.quick_visit_unit_8: {
                url = findViewById(R.id.quick_visit_url_8);
                temp_url = url.getText().toString();

                if (!temp_url.equals("NULL")) {

                    id = findViewById(R.id.quick_visit_id_8);
                    name = findViewById(R.id.quick_visit_name_8);
                    icon = findViewById(R.id.quick_visit_icon_8);
                    iconUrl = findViewById(R.id.quick_visit_iconUrl_8);

                    createClick(temp_url, view);
                    createLongClick(view, id,name, iconUrl, icon, url);
                }
            }
        }

    }

    public Bitmap myShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollecter.finishAll();
    }

}





