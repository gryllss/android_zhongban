package com.example.s.x5x5x5x55x.FiveFragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.MyUser;
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
import cn.bmob.v3.update.BmobUpdateAgent;

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


    private List<String> images = new ArrayList<>();

    private String bmobCurrentTime;
    private String localCurrentTime;
    private Date localOutTime;

    private Boolean isVer;

    public VideoFragment() {

    }

    public static Fragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
        Bmob.initialize(getActivity(), "2a654d2984b42dffb0a329dcc7189b4d");
        BmobUpdateAgent.update(getActivity());
        isVer();

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
    openVideo();

//        final MyUser myUser = MyUser.getCurrentUser(getActivity(),MyUser.class);
//        final MyUser myUser = BmobUser.getCurrentUser(getActivity(),MyUser.class);
//        Toast.makeText(getActivity(),myUser.getCurrentTimeMillisVer(),Toast.LENGTH_SHORT).show();//这样获取的是本地缓存
//        query.getObject(getActivity(), myUser.getObjectId(), new GetListener<MyUser>() {
//            @Override
//            public void onSuccess(MyUser myUser) {
//                bmobCurrentTime = myUser.getCurrentTimeMillisVer();
//                Toast.makeText(getActivity(), bmobCurrentTime, Toast.LENGTH_SHORT).show();//只有BmobQuery查询语句才会读取服务器上的内容
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
//        isVer();//第一次bmobCurrentTime返回的值是null；可能是服务器需要连接的过程,需要查询两次才能返回服务器的值。


//
        return view;
    }


    public void urlEvent(ImageView imageView, final String dataurl) {
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isVer()){
                    Intent intent = new Intent(getActivity(), BrowserActivity.class);
                    intent.putExtra("url", dataurl);
                    getActivity().startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "未登录或账号已过期，请重新登录", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVer();
        openVideo();
    }

    public void openVideo(){
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



    public Boolean isVer(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null) {
            localCurrentTime =  myUser.getCurrentTimeMillisVer();
            localOutTime = DateAndString.str2Date(myUser.getOutTime());

            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                query.getObject(myUser.getObjectId(), new   QueryListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e == null){
                            bmobCurrentTime = myUser.getCurrentTimeMillisVer();

                        }

                    }
            });

            if (localCurrentTime.equals(bmobCurrentTime )&&localOutTime.getTime() >= DateAndString.millis2Date(System.currentTimeMillis()).getTime() ){

                return true;//毫秒值和日期校验

            }else {


                return false;
            }
        } else {

            return false;
        }
    }
}



