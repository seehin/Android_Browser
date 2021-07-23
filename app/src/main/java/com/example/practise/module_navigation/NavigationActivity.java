package com.example.practise.module_navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.practise.R;
import com.example.practise.base.BaseActivity;
import com.example.practise.bean.BookmarkBean;
import com.example.practise.bean.QuickWayBean;
import com.example.practise.databinding.BookmarkDeleteAffirmDialogBinding;
import com.example.practise.databinding.NavigationActivityBinding;
import com.example.practise.module_bookmark.BookmarkActivity;
import com.example.practise.module_history.ui.History_Activity;
import com.example.practise.module_home.UI.Home_Activity;
import com.example.practise.module_windows.PagerActivity;
import com.example.practise.utils.UrlUtil;
import com.example.practise.utils.WebViewFragment;
import com.example.practise.utils.WebViewHelper;
import com.example.practise.viewmodel.BookmarkViewModel;
import com.example.practise.viewmodel.HistoryRecordViewModel;
import com.example.practise.viewmodel.QuickWayViewModel;
import com.example.practise.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NavigationActivity extends BaseActivity {

    private WebView webView;

    private HistoryRecordViewModel historyRecordViewModel;

    private BookmarkViewModel bookmarkViewModel;

    private QuickWayViewModel quickWayViewModel;

    private Intent intentOfHistory;

    private Intent intentOfBookmark;

    private NavigationActivityBinding binding;

    private boolean isCollect = false;

    private boolean flag;

    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        historyRecordViewModel = new ViewModelProvider(this).get(HistoryRecordViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        quickWayViewModel = new ViewModelProvider(this).get(QuickWayViewModel.class);

        intentOfHistory = getIntent();
        String historyUrl = intentOfHistory.getStringExtra("history_url");

        String homeUrl = intentOfHistory.getStringExtra("home_url");

        binding = DataBindingUtil.setContentView(this, R.layout.navigation_activity);
        binding.winnum.setText(WebViewHelper.webList.size()+1+"");
        MenuItem starItem = binding.navigationToolbar.getMenu().findItem(R.id.star);//拿到收藏item
//        LinearLayout web_failure = binding.webFailure;

        if (WebViewHelper.currentWebView != null) {
            webView = binding.webView;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            if(WebViewHelper.currentBundle!=null){
                webView.restoreState(WebViewHelper.currentBundle);
                binding.searchText.setText(WebViewHelper.currentWebView.getUrl());
            }else {
                webView.loadUrl(WebViewHelper.currentWebView.getUrl());
            }

        }

        WebViewHelper.headWebView = WebViewHelper.currentWebView;

        binding.opwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("多窗口");
                WebViewHelper.headWebView = webView;
                if (!WebViewHelper.isExist) {
                    if (webView == null) {
                        Log.e("houxl", "生成空窗口");
                        WebViewHelper.webList.add(new WebViewFragment(null, myShot(NavigationActivity.this)));
                        WebViewHelper.currentWebView = null;
                    } else {

                        Log.e("houxl", "生成实时窗口");
                        WebViewHelper.currentWebView = webView;
                        webView.setDrawingCacheEnabled(true);
                        WebViewHelper.webList.add(new WebViewFragment(webView, webView.getDrawingCache()));
                        if (webView.getDrawingCache() == null) {
                            Log.e("houxl", "图片为空");
                        }

                    }
                    WebViewHelper.isExist = true;
                }
                Intent i = new Intent(NavigationActivity.this, PagerActivity.class);

                startActivity(i);
            }
        });

        //返回主页功能
        binding.navigationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hintent = new Intent(NavigationActivity.this, Home_Activity.class);
                startActivity(hintent);
            }
        });

        if (historyUrl != null) {
            webView = binding.webView;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.loadUrl(historyUrl);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!(historyUrl.startsWith("http") || historyUrl.startsWith("https"))) {
                        view.loadUrl(historyUrl);
                        return super.shouldOverrideUrlLoading(webView, historyUrl);
                    }
                    return false;
                }
            });
        }else if(homeUrl != null){
            webView = binding.webView;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            if (!UrlUtil.isTopURL(homeUrl) || !(homeUrl.startsWith("http") || homeUrl.startsWith("https"))) {
                //不是网址，直接用百度搜索
                homeUrl = "https://www.baidu.com/s?wd=" + homeUrl;
            }
            webView.loadUrl(homeUrl);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);

                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    flag = false;
                    binding.searchText.setText(view.getUrl());

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (flag) {
                        Toast.makeText(NavigationActivity.this, "网络异常，请确认网络后刷新", Toast.LENGTH_SHORT).show();
                    }
//                            web_failure.setVisibility(View.GONE);
//                    webView.setVisibility(View.VISIBLE);
                    //记录浏览的历史记录
                    historyRecordViewModel.insertHistoryRecord(view.getTitle(), view.getUrl(), UrlUtil.getIconUrl(view.getUrl()), new Date());
                    loadStar(url);
                }

                // 加载错误的时候会回调，在其中可做错误处理
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    flag = true;
//                            MenuItem starItem = binding.navigationToolbar.getMenu().findItem(R.id.star);
//                            starItem.setVisible(false);
//                            web_failure.setVisibility(View.VISIBLE);
//                            webView.setVisibility(View.GONE);
                }
            });
        }

        //接收书签url
        intentOfBookmark = getIntent();
        String bookmarkUrl = intentOfBookmark.getStringExtra("bookmark_url");
        if (bookmarkUrl != null){
            webView = binding.webView;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.loadUrl(bookmarkUrl);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!(bookmarkUrl.startsWith("http") || bookmarkUrl.startsWith("https"))) {
                        view.loadUrl(bookmarkUrl);
                        return super.shouldOverrideUrlLoading(webView, bookmarkUrl);
                    }
                    return false;
                }
            });
        }





        binding.navigationToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bookmark:
                        Intent bintent = new Intent(NavigationActivity.this, BookmarkActivity.class);
                        startActivity(bintent);
                        break;
                    case R.id.history:
                        Intent intent = new Intent(NavigationActivity.this, History_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.star:
                        webView = binding.webView;
                        if (!isCollect) {
                            int maxSort = bookmarkViewModel.getMaxSort(-1);
                            bookmarkViewModel.insertBookmark(new BookmarkBean(webView.getTitle(), webView.getUrl(), UrlUtil.getIconUrl(webView.getUrl()), 0, 0, -1, maxSort + 1));
                            Toast.makeText(NavigationActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            starItem.setIcon(R.mipmap.been_star);
                            isCollect = true;
                        } else {
                            checkedDelete(webView.getUrl());
                            Toast.makeText(NavigationActivity.this, "收藏取消", Toast.LENGTH_SHORT).show();
                            starItem.setIcon(R.mipmap.star);
                            isCollect = false;
                        }
                        break;
                    case R.id.add_home:
                        webView = binding.webView;
                        System.out.println("aaaaaa");

                        if (homeViewModel.selectALL() == null|| homeViewModel.selectALL().size() <= 8 ) {

                            if (homeViewModel.search_url(webView.getUrl()).size() == 0) {

                                homeViewModel.insert1Home(webView.getTitle(), webView.getUrl(), UrlUtil.getIconUrl(webView.getUrl()));
                                Toast.makeText(NavigationActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NavigationActivity.this, "该网址已经添加到快速浏览", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NavigationActivity.this, "主页快速浏览已达上限", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.refresh:
                        webView = binding.webView;
                        webView.reload();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        //搜索功能
        binding.searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.searchText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.searchText.getWindowToken(), 0);
                    String url = binding.searchText.getText().toString();
                    //判断是否是网址
                    if (!UrlUtil.isTopURL(url) || !(url.startsWith("http") || url.startsWith("https"))) {
                        //不是网址，直接用百度搜索
                        url = "https://www.baidu.com/s?wd=" + url;
                    }
                    webView = binding.webView;
                    WebSettings settings = webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    webView.loadUrl(url);

                    webView.setWebViewClient(new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {

                            view.loadUrl(url);
                            return super.shouldOverrideUrlLoading(view, url);

                        }

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            flag = false;
                            binding.searchText.setText(view.getUrl());

                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            if (flag) {
                                Toast.makeText(NavigationActivity.this, "网络异常，请确认网络后刷新", Toast.LENGTH_SHORT).show();
                            }
//                            web_failure.setVisibility(View.GONE);
                            webView.setVisibility(View.VISIBLE);
                            //记录浏览的历史记录
                            historyRecordViewModel.insertHistoryRecord(view.getTitle(), view.getUrl(), UrlUtil.getIconUrl(view.getUrl()), new Date());
                            loadStar(url);
                        }

                        //                        加载错误的时候会回调，在其中可做错误处理
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);
                            flag = true;
//                            MenuItem starItem = binding.navigationToolbar.getMenu().findItem(R.id.star);
//                            starItem.setVisible(false);
//                            web_failure.setVisibility(View.VISIBLE);
//                            webView.setVisibility(View.GONE);
                        }
                    });
                }
                return true;
            }
        });

        //返回上一页
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView = binding.webView;
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    webView.reload();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(webView!=null){
            webView.saveState(outState);
        }
        WebViewHelper.currentBundle = outState;
    }

    //设置收藏按钮
    public void loadStar(String url) {
        //初始化收藏按钮，当已经收藏了
        MenuItem starItem = binding.navigationToolbar.getMenu().findItem(R.id.star);
        int isUrlExit = bookmarkViewModel.isNewUrlExit(url);
        starItem.setIcon(isUrlExit == 1 ? R.mipmap.been_star : R.mipmap.star);
        isCollect = isUrlExit == 1 ? true : false;
        starItem.setVisible(true);
    }

    //取消收藏（删除网址书签）
    public void checkedDelete(String url) {
        List<BookmarkBean> bookmarkBeans = new ArrayList<>();
        bookmarkBeans.add(bookmarkViewModel.getBookmarkBasisUrl(url));
        int upper = bookmarkBeans.get(0).getUpper();
        //执行删除操作
        bookmarkViewModel.deleteBookmarks(bookmarkBeans);
        //更新网址所属文件夹的num
        bookmarkViewModel.updateBookmark(upper, -1);
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
}