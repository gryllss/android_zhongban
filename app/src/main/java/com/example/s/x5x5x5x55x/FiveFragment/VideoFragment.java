package com.example.s.x5x5x5x55x.FiveFragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.MyUser;
import com.example.s.x5x5x5x55x.Bmob.UserReadOrACL;
import com.example.s.x5x5x5x55x.BrowserActivity;
import com.example.s.x5x5x5x55x.MainActivity;
import com.example.s.x5x5x5x55x.R;
import com.example.s.x5x5x5x55x.SignUpActivity;
import com.example.s.x5x5x5x55x.VideoFragment_LoopView.LoopViewPager;
import com.example.s.x5x5x5x55x.utils.DateAndString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import static android.view.KeyEvent.KEYCODE_BACK;

public class VideoFragment extends Fragment {
    ImageView image_aiyiqi;
    ImageView image_tengxun;
    ImageView image_souhu;
    ImageView image_youku;
    ImageView image_mangguo;
    ImageView image_tudou;
    ImageView image_m1905;
    ImageView image_leshi;
    ImageView image_ppshipin;
    ImageView image_lishipin;
    ImageView image_dongman;
    ImageView image_dianshi;
    private LoopViewPager viewPager;
    private ProgressBar progressBar;


    private List<String> images = new ArrayList<>();

    private String bmobCurrentTime = "";
    private String localCurrentTime = "";
    private Date localOutTime;
    private String[] jiexiUrlList = null;

    private Boolean isVer = false;

    public VideoFragment() {

    }

    public static Fragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        Bmob.initialize(getActivity(), "2a654d2984b42dffb0a329dcc7189b4d");
        BmobUpdateAgent.update(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        viewPager = (LoopViewPager) view.findViewById(R.id.viewpager);

        images.add("https://wx2.sinaimg.cn/mw690/b0653590gy1fv0zcugl7ij20hs0bv0t5.jpg");
        images.add("https://wx2.sinaimg.cn/mw690/b0653590gy1fv0zcuob7jj20k008cae7.jpg");
        images.add("https://wx2.sinaimg.cn/mw690/b0653590gy1fv0zcuvuzsj20k008cn1q.jpg");
        images.add("https://wx1.sinaimg.cn/mw690/b0653590gy1fv0zcv2qpmj20u00itwkt.jpg");
        images.add("https://wx2.sinaimg.cn/mw690/b0653590gy1fv0zcvgxw4j20jt078q5c.jpg");
        viewPager.setData(images);

        image_aiyiqi = (ImageView) view.findViewById(R.id.imageview_aiqiyi);
        image_tengxun = (ImageView) view.findViewById(R.id.imageview_tengxun);
        image_souhu = (ImageView) view.findViewById(R.id.imageview_souhu);
        image_youku = (ImageView) view.findViewById(R.id.imageview_youku);
        image_mangguo = (ImageView) view.findViewById(R.id.imageview_mangguo);
        image_tudou = (ImageView) view.findViewById(R.id.imageview_tudou);
        image_m1905 = (ImageView) view.findViewById(R.id.imageview_m1905);
        image_leshi = (ImageView) view.findViewById(R.id.imageview_leshi);
        image_ppshipin = (ImageView) view.findViewById(R.id.imageview_ppshipin);
        image_lishipin = (ImageView) view.findViewById(R.id.imageview_lishipin);
        image_dongman = (ImageView) view.findViewById(R.id.imageview_dongman);
        image_dianshi = (ImageView) view.findViewById(R.id.imageview_dianshi);
        progressBar = (ProgressBar) view.findViewById(R.id.proLoading);
        progressBar.setVisibility(View.INVISIBLE);
        openVideo();
        return view;
    }

    public void urlEvent(ImageView imageView, final String dataurl) {
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                if (myUser != null) {
                    localCurrentTime = myUser.getCurrentTimeMillisVer();
                    localOutTime = DateAndString.str2Date(myUser.getOutTime());

                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    query.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                bmobCurrentTime = myUser.getCurrentTimeMillisVer();

                                isVer = localCurrentTime.equals(bmobCurrentTime) && localOutTime.getTime()
                                        >= DateAndString.millis2Date(System.currentTimeMillis()).getTime() ? true : false;
                                if (isVer){
                                    BmobQuery<UserReadOrACL> query = new BmobQuery<UserReadOrACL>();
                                    query.getObject("0c5cb1e7a1", new QueryListener<UserReadOrACL>() {
                                        @Override
                                        public void done(UserReadOrACL userReadOrACL, BmobException e) {
                                            String jiexiUrl = new String();
                                            jiexiUrl = userReadOrACL.getUserReadOrACL();
                                            jiexiUrlList = jiexiUrl.split("\\*{4}");
                                            progressBar.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(getActivity(), BrowserActivity.class);
                                            intent.putExtra("url", dataurl);
                                            intent.putExtra("jiexiUrl", jiexiUrlList);
                                            getActivity().startActivity(intent);
                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(), "账号已在别处登录或账号身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(), "服务器可能出错了，请稍后再试", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "账号未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public void openVideo() {
        urlEvent(image_aiyiqi, "http://m.iqiyi.com/");
        urlEvent(image_tengxun, "http://m.v.qq.com");
        urlEvent(image_souhu, "https://m.tv.sohu.com/");
        urlEvent(image_youku, "https://www.youku.com/");
        urlEvent(image_mangguo, "https://m.mgtv.com/");
        urlEvent(image_tudou, "http://compaign.tudou.com/?");
        urlEvent(image_m1905, "http://m.1905.com/");
        urlEvent(image_leshi, "http://m.le.com/");
        urlEvent(image_ppshipin, "http://m.pptv.com/?f=pptv");
        urlEvent(image_lishipin, "http://www.pearvideo.com/?from=intro");
        urlEvent(image_dongman, "http://m.iqiyi.com/dongman/");
        urlEvent(image_dianshi, "http://wx.iptv789.com/?act=home");
    }


    @Override
    public void onStop() {
        super.onStop();
        progressBar.setVisibility(View.INVISIBLE);
    }
}



