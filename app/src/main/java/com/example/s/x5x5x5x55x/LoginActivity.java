package com.example.s.x5x5x5x55x;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.tv.TvContentRating;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.MyUser;
import com.example.s.x5x5x5x55x.Bmob.UserReadOrACL;
import com.example.s.x5x5x5x55x.utils.DateAndString;

import java.nio.file.FileVisitOption;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends AppCompatActivity {

    private ImageButton mBack;
    private EditText mLoginPhoneNumber, mLoginPassword;
    private Button mLogin;
    private TextView mLoginToSignUp;
    private Boolean isLogin = false;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "2a654d2984b42dffb0a329dcc7189b4d");
        linearLayout = (LinearLayout)findViewById(R.id.layout_login);
        mBack = (ImageButton) findViewById(R.id.btnBack1);
        mLoginPhoneNumber = (EditText) findViewById(R.id.et_loginphonenumber);
        mLoginPassword = (EditText) findViewById(R.id.et_loginpassword);
        mLogin = (Button) findViewById(R.id.login);
        mLoginToSignUp = (TextView) findViewById(R.id.logintosignup);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLoginToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<UserReadOrACL> query = new BmobQuery<UserReadOrACL>();
                query.getObject("1016b2b7e4", new QueryListener<UserReadOrACL>() {
                    @Override
                    public void done(UserReadOrACL userReadOrACL, BmobException e) {
                        if (e == null){
                            if (userReadOrACL.getUserReadOrACL().equals("Read")){
                                Toast toast = Toast.makeText(LoginActivity.this, "抱歉，暂已关闭注册通道", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }else {
                                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                });



            }
        });
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linearLayout.setFocusable(true);
                linearLayout.setFocusableInTouchMode(true);
                linearLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(mLoginPhoneNumber.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mLoginPassword.getWindowToken(), 0);

                return false;
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final MyUser myUser = new MyUser();
                    String userPhone = mLoginPhoneNumber.getText().toString();
                    String userPassword = mLoginPassword.getText().toString();
                    myUser.setUsername(userPhone);
                    myUser.setPassword(userPassword);

                    myUser.login( new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e==null){
                                Toast toast = Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                isLogin =true;
                                finish();
                            }else {
                                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                                if (networkInfo == null || !networkInfo.isAvailable()) {
                                    Toast toast = Toast.makeText(LoginActivity.this, "登陆失败，请检查网络或稍后重试", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }else {
                                    Toast toast = Toast.makeText(LoginActivity.this, "手机号或密码错误", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }

                            }
                        }



                    });


            }
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isLogin){

            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            myUser.setCurrentTimeMillisVer(String.valueOf(System.currentTimeMillis()));
            myUser.update( myUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }

            });

//            BmobUser newUser = new BmobUser();
//            newUser = BmobUser.getCurrentUser(LoginActivity.this);
//            newUser.setEmail("12344565@163.com");
////                BmobUser bmobUser = BmobUser.getCurrentUser(getActivity());
//            newUser.update(LoginActivity.this, new UpdateListener() {
//                @Override
//                public void onSuccess() {
//                    Toast.makeText(LoginActivity.this, "yes", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(int i, String s) {
//                    Toast.makeText(LoginActivity.this, "no", Toast.LENGTH_SHORT).show();
//                }
//            });
            isLogin = false;
        }

    }
}
