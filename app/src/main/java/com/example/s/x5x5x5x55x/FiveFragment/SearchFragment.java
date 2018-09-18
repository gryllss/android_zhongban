package com.example.s.x5x5x5x55x.FiveFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.R;

public class SearchFragment extends Fragment {

    private ImageButton mBack;
    private ImageButton mForward;
    private ImageButton mHome;
    private ImageButton mRefresh;

    public WebView searchWebView;

    private WebSettings webSettings;

    private String url = "http://www.youjiequ.com/index.php?r=index/classify";

    private long exittime = 0;

    public SearchFragment() {

    }

    public static Fragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void changGoForwardButton(WebView view) {
        if (view.canGoBack())
            mBack.setAlpha(255);
        else
            mBack.setAlpha(100);
        if (view.canGoForward())
            mForward.setAlpha(255);
        else
            mForward.setAlpha(100);
        if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(url)) {
            mHome.setAlpha(100);
            mHome.setEnabled(false);
        } else {
            mHome.setAlpha(255);
            mHome.setEnabled(true);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchWebView = (WebView) view.findViewById(R.id.wv_search);

        mBack = (ImageButton) view.findViewById(R.id.btnBack1);
        mForward = (ImageButton) view.findViewById(R.id.btnForward1);
        mRefresh = (ImageButton) view.findViewById(R.id.btnrefresh1);
        mHome = (ImageButton) view.findViewById(R.id.btnHome1);

        webSettings = searchWebView.getSettings();
        searchWebView.getSettings().setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//自适应屏幕大小
        webSettings.setLoadWithOverviewMode(true);
        searchWebView.getSettings().setDomStorageEnabled(true);//部分网页可能加载不完全，需要打开DOM储存
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(getActivity().getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(getActivity().getDir("databases", 0).getPath());


        searchWebView.loadUrl(url);
        mBack.setAlpha(100);
        mForward.setAlpha(100);
        mHome.setAlpha(100);
        mRefresh.setAlpha(255);
        mHome.setEnabled(false);

        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (searchWebView != null && searchWebView.canGoBack())
                    searchWebView.goBack();
            }
        });

        mForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (searchWebView != null && searchWebView.canGoForward())
                    searchWebView.goForward();
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (searchWebView != null)
                    searchWebView.loadUrl(url);
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWebView.reload();
            }
        });

        searchWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                changGoForwardButton(view);
            }
        });

        searchWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK ) {
                        //这里处理返回键事件
                        if (searchWebView.canGoBack()){
                            searchWebView.goBack();
//                            Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                            return true;
                        }else {
                            if (System.currentTimeMillis() - exittime <2000){
                                getActivity().finish();
                            }else {
                                exittime = System.currentTimeMillis();
                                Toast.makeText(getActivity(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    return super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            //     super.onReceivedError(view, errorCode, description, failingUrl);
            //  Toast.makeText(this,"网页加载错误！",0).show();
        }
    }


}
