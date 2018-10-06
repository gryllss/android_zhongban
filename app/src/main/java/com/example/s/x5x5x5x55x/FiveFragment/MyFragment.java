package com.example.s.x5x5x5x55x.FiveFragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s.x5x5x5x55x.Bmob.MyUser;
import com.example.s.x5x5x5x55x.ContactMeActivity;
import com.example.s.x5x5x5x55x.LoginActivity;
import com.example.s.x5x5x5x55x.QuestionActivity;
import com.example.s.x5x5x5x55x.R;
import com.example.s.x5x5x5x55x.TimeExchangeActivity;
import com.example.s.x5x5x5x55x.utils.CleanMessageUtil;
import com.example.s.x5x5x5x55x.utils.MyOneLineView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment implements MyOneLineView.OnRootClickListener, MyOneLineView.OnArrowClickListener {

    private CircleImageView mMineAvatar;
    private TextView mSignUpLogin, mMineTime;
    LinearLayout ll_mine_item;

    public MyFragment() {

    }

    public static Fragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RightFragment", "onCreate");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("RightFragment", "onActivityCreated");
        initView();
    }


    public void initView() {
        ll_mine_item = (LinearLayout) getActivity().findViewById(R.id.ll_mine_item);
        mMineAvatar = (CircleImageView) getActivity().findViewById(R.id.iv_mine_avatar);
        mSignUpLogin = (TextView) getActivity().findViewById(R.id.tv_singup_login);
        mMineTime = (TextView) getActivity().findViewById(R.id.tv_mine_time);


        mMineAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
//
                startActivity(intent);
            }
        });


        //使用示例，通过Java代码来创建MyOnelineView
        //icon + 文字 + 箭头
        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_timeexchange, "时长兑换", "", true)
                .setOnRootClickListener(this, 1));

        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_contact, "联系我们", "", true)
                .setOnRootClickListener(this, 2));

        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_question, "常见问题", "", true)
                .setOnRootClickListener(this, 3));

        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_update, "检查更新", "", true)
                .setDividerTopColor(R.color.gray2)
                .showDivider(true, true)
                .setDividerTopHigiht(10)
                .setOnRootClickListener(this, 4));

        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_clear, "清除缓存", "", true)
                .setOnRootClickListener(this, 5));
        ll_mine_item.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.mine_exit, "退出登陆", "", true)
                .setOnRootClickListener(this, 6));
        ll_mine_item.getChildAt(5).setVisibility(View.INVISIBLE);


//        ll_mine_item.removeViewAt(5);


//        //icon + 文字 + 文字 + 箭头
//        ll_mine_item.addView(new MyOneLineView(getActivity())
//                .initMine(R.mipmap.ic_launcher, "第二行", "第二行", true)
//                .setOnArrowClickListener(this, 2));
//        //icon + 文字 + 输入框
//        ll_mine_item.addView(new MyOneLineView(getActivity())
//                .initItemWidthEdit(R.mipmap.ic_launcher, "第三行", "这是一个输入框")
//                .setRootPaddingTopBottom(20, 20));
    }

    @Override
    public void onRootClick(View view) {
        switch ((int) (view.getTag())) {
            case 1:
                Intent intent = new Intent(getActivity(), TimeExchangeActivity.class);
                startActivity(intent);

                break;
            case 2:
                Intent intent2 = new Intent(getActivity(), ContactMeActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent3);
                break;

            case 4:
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        // TODO Auto-generated method stub
                        if (updateStatus == UpdateStatus.Yes) {//版本有更新

                        } else if (updateStatus == UpdateStatus.No) {
                            Toast.makeText(getActivity(), "版本无更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                            Toast.makeText(getActivity(), "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.IGNORED) {
                            Toast.makeText(getActivity(), "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                            Toast.makeText(getActivity(), "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.TimeOut) {
                            Toast.makeText(getActivity(), "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                BmobUpdateAgent.forceUpdate(getActivity());


                break;

            case 5:
                CleanMessageUtil.clearAllCache(getActivity().getApplicationContext());//这个是可以用于清楚缓存的

                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                TextView title = new TextView(getActivity());
//                title.setText("提示");
//                title.setTextSize(20);
//                builder.setCustomTitle(title);
//                TextView message = new TextView(getActivity());
//                message.setText("是否退出登陆");
//                message.setGravity(Gravity.CENTER);
//                builder.setView(message);
                builder.setTitle("提示").setMessage("是否退出登陆");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut();//这个是可以用于清楚缓存的
                        Toast.makeText(getActivity(), "已退出", Toast.LENGTH_SHORT).show();
                        ll_mine_item.getChildAt(5).setVisibility(View.INVISIBLE);
                        mSignUpLogin.setText("登陆/注册");
                        mMineTime.setText("");
                        mMineAvatar.setClickable(true);
                        mSignUpLogin.setClickable(true);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            default:
                break;

        }

    }

    @Override
    public void onArrowClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();

        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null) {
            ll_mine_item.getChildAt(5).setVisibility(View.VISIBLE);
            mMineAvatar.setClickable(false);
            mSignUpLogin.setClickable(false);
            String phone = myUser.getUsername();
            String time = myUser.getOutTime();
            StringBuilder sb = new StringBuilder(phone);
            if (sb.length() >= 7) {
                sb.replace(3, 7, "****");
                mSignUpLogin.setText(sb.toString());
            }else {
                mSignUpLogin.setText(sb.toString());
            }


            StringBuilder sb2 = new StringBuilder(time);
            sb2.replace(10, 19, "");
            mMineTime.setText("体验时长：" + sb2 + "到期");

        } else {
            ll_mine_item.getChildAt(5).setVisibility(View.INVISIBLE);
        }


    }


}
