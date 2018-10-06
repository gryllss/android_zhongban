package com.example.s.x5x5x5x55x;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.ActivationCode;
import com.example.s.x5x5x5x55x.Bmob.MyUser;
import com.example.s.x5x5x5x55x.utils.DateAndString;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TimeExchangeActivity extends AppCompatActivity {

    private TextView mDuihuanPhone;
    private EditText mDuihuanma;
    private Button mDuihuan;
    private String userPhone = "";
    private String duiHuanMa = "";
    private LinearLayout linearLayout;


    private Boolean isUsedCode ;
    private String objectId ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(TimeExchangeActivity.this, "2a654d2984b42dffb0a329dcc7189b4d");
        setContentView(R.layout.activity_time_exchange);
        linearLayout = (LinearLayout)findViewById(R.id.layout_timechange);
        mDuihuanPhone = (TextView) findViewById(R.id.tv_duihuan_phonenumber);
        mDuihuanma = (EditText) findViewById(R.id.et_duihuanma);
        mDuihuan = (Button) findViewById(R.id.duihuan);
        MyUser myUser = BmobUser.getCurrentUser( MyUser.class);
        if (myUser!=null){
            userPhone = myUser.getUsername();
            StringBuilder sb = new StringBuilder(userPhone);
            if (sb.length() >= 7){
                sb.replace(3, 7, "****");
                mDuihuanPhone.setText(sb.toString());
            }else {
                mDuihuanPhone.setText(sb.toString());
            }

        }

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linearLayout.setFocusable(true);
                linearLayout.setFocusableInTouchMode(true);
                linearLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.hideSoftInputFromWindow(mDuihuanma.getWindowToken(), 0);


                return false;
            }
        });



        mDuihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUsedCode = false;
                duiHuanMa = mDuihuanma.getText().toString();
                BmobQuery<ActivationCode> query = new BmobQuery<ActivationCode>();
                query.addWhereEqualTo("activationCode", duiHuanMa);
                query.findObjects(new FindListener<ActivationCode>() {
                    @Override
                    public void done(List<ActivationCode> list, BmobException e) {
                        if (e==null){
                            String str = "";
                            for (ActivationCode activationCode1 : list) {
                                str = activationCode1.getObjectId();
                                isUsedCode = activationCode1.getUsed();


                            }
                            objectId = str;
//                        Toast.makeText(TimeExchangeActivity.this, objectId, Toast.LENGTH_SHORT).show();
                            if (!objectId.equals("") && !isUsedCode) {

                                ActivationCode activationCode = new ActivationCode();
                                MyUser newUser = new MyUser();
                                newUser = BmobUser.getCurrentUser(MyUser.class);
                                activationCode.setUsed(true);
                                activationCode.setUsederPhone(newUser.getUsername());
                                activationCode.setJiHuoTime(DateAndString.date2Str(DateAndString.millis2Date(System.currentTimeMillis())));
                                activationCode.update( objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            MyUser newUser = new MyUser();
                                            newUser = BmobUser.getCurrentUser(MyUser.class);
                                            newUser.setOutTime(DateAndString.dateAddYear(newUser.getOutTime()));
                                            newUser.update(newUser.getObjectId(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e==null){
                                                        Toast.makeText(TimeExchangeActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }else {
                                                        Toast.makeText(TimeExchangeActivity.this, "激活失败，当前账号可能已在其他设备登录", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            });

                                        }
                                    }




                                });


                            } else {
                                if (isUsedCode) {
                                    Toast.makeText(TimeExchangeActivity.this, "激活码已被使用", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(TimeExchangeActivity.this, "激活码不存在，请输入正确的激活码", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }else {
                            Toast.makeText(TimeExchangeActivity.this, "账号未登录", Toast.LENGTH_SHORT).show();
                        }


                    }




                });


            }
        });


    }


}
