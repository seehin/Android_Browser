package com.example.practise.module_windows;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.practise.R;
import com.example.practise.base.BaseActivity;
import com.example.practise.databinding.WindowPagerBinding;
import com.example.practise.module_home.UI.Home_Activity;
import com.example.practise.module_navigation.NavigationActivity;
import com.example.practise.utils.WebViewFragment;
import com.example.practise.utils.WebViewHelper;

import java.util.ArrayList;
import java.util.List;


public class PagerActivity extends BaseActivity {
    int num,cur=0;
    List<View> listview;
    List<WebViewFragment> wl ;
    PagerAdapter pagerAdapter;
    ImageButton ib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_pager);

        init();
    }
    public void init(){
        WindowPagerBinding windowPagerBinding = DataBindingUtil.setContentView(this, R.layout.window_pager);
        listview = new ArrayList<>();
        Log.e("houxl","一共有"+WebViewHelper.webList.size()+"页面");
        for(int i=0;i<WebViewHelper.webList.size();i++){
            num = i;
            if (WebViewHelper.headWebView == WebViewHelper.webList.get(i).getWebView()) {
                cur = i;
                Log.e("houxl",i+"");
            }
            View view = LayoutInflater.from(this).inflate(R.layout.pager_item,null);
            ImageView imageView =  view.findViewById(R.id.siv_pic);
            imageView.setImageBitmap(WebViewHelper.webList.get(i).getwBitmap());
            listview.add(view);
        }
        windowPagerBinding.viewPager.setAdapter(pagerAdapter = new PagerAdapter() {


            @Override
            public int getCount() {
                return listview.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view ==  o;
//                return false;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.pager_item,null);
                ImageView imageView =  view.findViewById(R.id.siv_pic);
                imageView.setImageBitmap(WebViewHelper.webList.get(position).getwBitmap());
                ib = view.findViewById(R.id.close);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if(WebViewHelper.webList.get(position).getWebView()==null){
                            intent = new Intent(PagerActivity.this, Home_Activity.class);
                            WebViewHelper.currentWebView = null;
                            WebViewHelper.currentBundle = null;
                        }else {
                            intent = new Intent(PagerActivity.this, NavigationActivity.class);
                            WebViewHelper.currentWebView = WebViewHelper.webList.get(position).getWebView();
                            WebViewHelper.currentWebView.saveState(WebViewHelper.currentBundle);
                        }
                        WebViewHelper.webList.remove(position);
                        Log.e("houxl","进入第"+position+"页面");
                        WebViewHelper.isExist = false;
                        startActivity(intent);
                    }
                });

                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebViewHelper.webList.remove(position);
                        listview.remove(position);
                        if(listview.size()==0){
                            Intent i = new Intent(PagerActivity.this, Home_Activity.class);
                            WebViewHelper.currentWebView = null;
                            WebViewHelper.isExist = false;
                            startActivity(i);
                        }
                        pagerAdapter.notifyDataSetChanged();
                        Toast.makeText(PagerActivity.this,"关闭",Toast.LENGTH_SHORT).show();

                    }
                });
                container.addView(view);
                return view;
//                return super.instantiateItem(container, position)
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                super.destroyItem(container, position, object);
                container.removeView((View)object);

            }
        });
        windowPagerBinding.viewPager.setCurrentItem(cur);
    }
    public void addWindow(View view){

        Intent intent = new Intent(PagerActivity.this, Home_Activity.class);
        WebViewHelper.isExist = false;
        Toast.makeText(PagerActivity.this,"新建成功",Toast.LENGTH_SHORT).show();
        WebViewHelper.currentWebView = null;
        startActivity(intent);
    }

}