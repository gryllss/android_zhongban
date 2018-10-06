package com.example.s.x5x5x5x55x;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.UserReadOrACL;
import com.example.s.x5x5x5x55x.FiveFragment.BaoyouFragment;
import com.example.s.x5x5x5x55x.FiveFragment.MallFragment;
import com.example.s.x5x5x5x55x.FiveFragment.MyFragment;
import com.example.s.x5x5x5x55x.FiveFragment.SearchFragment;
import com.example.s.x5x5x5x55x.FiveFragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//import android.support.v4.app.FragmentActivity;

public class MainActivity extends AppCompatActivity {


    private RadioGroup mRgTab;
    private List<Fragment> mFragmentList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }




        mRgTab = (RadioGroup) findViewById(R.id.rg_main);
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_video:
                        changeFragment(VideoFragment.class.getName());
                        break;
                    case R.id.rb_tv:
                        changeFragment(BaoyouFragment.class.getName());
                        break;
                    case R.id.rb_mall:
                        changeFragment(MallFragment.class.getName());
                        break;
                    case R.id.rb_coupon:
                        changeFragment(SearchFragment.class.getName());
                        break;
                    case R.id.rb_my:
                        changeFragment(MyFragment.class.getName());

//                        CleanMessageUtil.clearAllCache(getApplicationContext());//这个是可以用于清楚缓存的
                        break;
                }
            }
        });
        if (savedInstanceState == null) {
            changeFragment(VideoFragment.class.getName());
        }



    }

    public void changeFragment(String tag) {
        hideFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            if (tag.equals(VideoFragment.class.getName())) {
                fragment = VideoFragment.newInstance();

            } else if (tag.equals(BaoyouFragment.class.getName())) {
                fragment = BaoyouFragment.newInstance();
            } else if (tag.equals(MallFragment.class.getName())) {
                fragment = MallFragment.newInstance();
            } else if (tag.equals(SearchFragment.class.getName())) {
                fragment = SearchFragment.newInstance();
            } else if (tag.equals(MyFragment.class.getName())) {
                fragment = MyFragment.newInstance();
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_container, fragment, fragment.getClass().getName());
        }
        transaction.commitAllowingStateLoss();

    }


    private void hideFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            ft.hide(f);
        }
        ft.commit();
    }


    private long exitTime = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (View.VISIBLE == findViewById(R.id.proLoading).getVisibility()){
                findViewById(R.id.proLoading).setVisibility(View.INVISIBLE);
            }else {
                exit();
            }

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }


    }







}

