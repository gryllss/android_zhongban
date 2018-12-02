package com.example.s.x5x5x5x55x;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.utils.X5WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayVideoActivity extends AppCompatActivity {

    private long exittime = 0;
    private TextView mLastGoBack;
    private TextView mLastRegresh;
    private TextView textViewVideoTitle;
    private TextView mChangeLine;
    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private String[] jiexiURL = null;
    private String mLine1Url = "https://api.97kn.com/?url=";

//    http://vip.jlsprh.com/index.php?url=
//    http://yun.ckmov.com/ckmov/index.php?url=
//    http://yun.baiyug.cn/vip/?url=
    private String mLine1Ur2 = "https://jx.618g.com/?url=";
//    private String mLine1Ur3 = "https://jx.ysviptq.com/ly/?url=";
    private String mLine1Ur3 = "https://www.1717yun.com/jx/ty.php?url=";
//    private String mLine1Ur4 = "http://y.mt2t.com/lines?url=";
    private String mLine1Ur4 = "http://yun.odflv.com/?url=";
    private String mLine1Ur5 = "http://api.wlzhan.com/sudu/?url=";
    private String mLine1Ur6 = "http://y.mt2t.com/lines?url=";
//    http://5.5252e.com/jx/b.php?url=
//    private String mLine1Ur6 = "http://www.ibb6.com/jx/?url=";//思古解析，能用，还不错，暂时先废弃。
    private String mLine1Ur7 = "http://yun.baiyug.cn/vip/index.php?url=";
    private String mLine1Ur8 = "https://660e.com/?url=";

//    https://yun.jxegc.com/vip.php?url=
//    private String mLine1Ur9 = "http://jx.598110.com/index.php?url=";
//    private String mLine1Ur10 = "";

    private static String mHomeUrl;
    private static String mVideoTitle;

    private static final String TAG = "SdkDemo";
    private static final int MAX_LENGTH = 14;
    private boolean mNeedTestPage = false;


    private ValueCallback<Uri> uploadFile;

    private URL mIntentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarvideo);
        setSupportActionBar(toolbar);
        mLastGoBack = (TextView) findViewById(R.id.lastgoback);
        mLastRegresh = (TextView) findViewById(R.id.lastrefresh);
        mViewParent = (ViewGroup) findViewById(R.id.webView1);
        textViewVideoTitle = (TextView) findViewById(R.id.videotitle);
        mChangeLine = (TextView)findViewById(R.id.changeline);

        mChangeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openOptionsMenu();
            }
        });
        final Intent intent = getIntent();
        mHomeUrl = intent.getStringExtra("videourl");
        mVideoTitle = intent.getStringExtra("videotitle");
        jiexiURL = intent.getStringArrayExtra("jiexiURL");
        textViewVideoTitle.setText(mVideoTitle);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mLastGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLastRegresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });


        if (intent != null) {
            try {
                mIntentUrl = new URL(intent.getData().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (NullPointerException e) {

            } catch (Exception e) {
            }
        }
        //
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

        mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);
    }

    private void init() {

        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

//		initProgressBar();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?


                /* mWebView.showLog("test Log"); */
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                TbsLog.d(TAG, "url: " + arg0);
                new AlertDialog.Builder(PlayVideoActivity.this)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                PlayVideoActivity.this,
                                                "fake message: i'll download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                PlayVideoActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                PlayVideoActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//一开始是这个
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);

        webSetting.setLoadWithOverviewMode(true);  //可以让网页适配屏幕大小

        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                String js = "javascript: function upTitle(){" +
                        "document.title = '返回'; " +
//                            "var a = document.getElementsByTagName('span');\n" +
//                            "\t\tfor(var i = 0; i < a.length; i++) {\n" +
//                            "\t\t\tif(a[i].getAttribute(\"class\") == 'ui-btn-inner') {\n" +
//                            "\t\t\t\ta[i].style.display = \"none\"\n" +
//                            "\t\t\t}\n" +
//                            "\n" +
//                            "\t\t}"+
                        "}";
//
//                String js1 = "javascript: function hideTop(){" +
//                        "var a = document.getElementsByTagName('obqcfiu');\n" +
//                        "\t\tfor(var i = 0; i < a.length; i++) {\n" +
//                        "\t\t\tif(a[i].getAttribute(\"id\") == '45361') {\n" +
//                        "\t\t\t\ta[i].style.display = \"none\"\n" +
//                        "\t\t\t}\n" +
//                        "\n" +
//                        "\t\t}" +
//                        "}";
//
//
//                mWebView.loadUrl(js1); //加载js方法代码
//                mWebView.loadUrl("javascript:hideTop();");


                mWebView.loadUrl(js); //加载js方法代码
                mWebView.loadUrl("javascript:upTitle();"); //调用js方法
                super.onPageFinished(webView, s);
            }
        });

        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            if (jiexiURL != null){
                mWebView.loadUrl(jiexiURL[0] + mHomeUrl);
            }else {
                mWebView.loadUrl(mLine1Url + mHomeUrl);
            }



        } else {
            mWebView.loadUrl(mIntentUrl.toString());
        }
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();


    }


    boolean[] m_selected = new boolean[]{true, true, true, true, false,
            false, true};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null ) {
                if (System.currentTimeMillis() - exittime <2000){
                    finish();
                }else {
                    exittime = System.currentTimeMillis();
                    Toast.makeText(this, "再按一次退出当前页面", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mTestHandler != null)
            mTestHandler.removeCallbacksAndMessages(null);
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    public static final int MSG_OPEN_TEST_URL = 0;
    public static final int MSG_INIT_UI = 1;
    private final int mUrlStartNum = 0;
    private int mCurrentUrl = mUrlStartNum;
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OPEN_TEST_URL:
                    if (!mNeedTestPage) {
                        return;
                    }

//				String testUrl = "file:///sdcard/outputHtml/html/"
//						+ Integer.toString(mCurrentUrl) + ".html";
//				if (mWebView != null) {
//					mWebView.loadUrl(testUrl);
//				}

                    mCurrentUrl++;
                    break;
                case MSG_INIT_UI:
                    init();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarvideo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.videoline1:
                if (jiexiURL != null){

                    mWebView.loadUrl(jiexiURL[0]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Url + mHomeUrl);
                }
                break;
            case R.id.videoline2:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[1]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur2 + mHomeUrl);
                }
                break;
            case R.id.videoline3:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[2]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur3 + mHomeUrl);
                }
                break;
            case R.id.videoline4:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[3]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur4 + mHomeUrl);
                }
                break;
            case R.id.videoline5:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[4]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur5 + mHomeUrl);
                }
                break;
            case R.id.videoline6:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[5]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur6 + mHomeUrl);
                }
                break;
            case R.id.videoline7:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[6]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur7 + mHomeUrl);
                }
                break;
            case R.id.videoline8:
                if (jiexiURL != null){
                    mWebView.loadUrl(jiexiURL[7]+mHomeUrl);
                }else {
                    mWebView.loadUrl(mLine1Ur8 + mHomeUrl);
                }
                break;
            default:
                break;

        }
        return true;
    }


}
