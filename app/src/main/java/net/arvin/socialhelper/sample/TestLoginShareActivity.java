package net.arvin.socialhelper.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

import net.arvin.socialhelper.SocialHelper;
import net.arvin.socialhelper.callback.SocialLoginCallback;
import net.arvin.socialhelper.callback.SocialShareCallback;
import net.arvin.socialhelper.entities.ThirdInfoEntity;
import net.arvin.socialhelper.sample.utils.SocialUtil;
import net.arvin.socialhelper.sample.wxapi.DBOpenHelper;
import net.arvin.socialhelper.sample.wxapi.FindPasswordActivity;
import net.arvin.socialhelper.sample.wxapi.MainActivity;
import net.arvin.socialhelper.sample.wxapi.User;

import java.util.ArrayList;

public class TestLoginShareActivity extends AppCompatActivity implements View.OnClickListener, SocialLoginCallback, SocialShareCallback {

    private SocialHelper socialHelper;
    private TextView tvLoginInfo;
    private DBOpenHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private Button mBtFindPassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login_share);
        setTitle("测试SocialHelper");
        socialHelper = SocialUtil.INSTANCE.socialHelper;
        mDBOpenHelper = new DBOpenHelper(this);


        initView();
        initEvent();
        DebugDB.getAddressLog();
        mDBOpenHelper.isUsernameExist("jialang");

    }


    private void initView() {
        tvLoginInfo = findViewById(R.id.tv_login_info);
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mEtLoginactivityUsername = findViewById(R.id.tv_loginactivity_login);
        mEtLoginactivityPassword = findViewById(R.id.tv_loginactivity_password);
        mBtFindPassword = findViewById(R.id.find_password);

    }

    private void initEvent() {
        findViewById(R.id.img_qq).setOnClickListener(this);
        findViewById(R.id.img_wx).setOnClickListener(this);
        findViewById(R.id.img_wb).setOnClickListener(this);
        findViewById(R.id.tv_loginactivity_login).setOnClickListener(this);
        findViewById(R.id.tv_loginactivity_password).setOnClickListener(this);
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
        mBtFindPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_qq:
                socialHelper.loginQQ(this, this);
                break;
            case R.id.img_wx:
                socialHelper.loginWX(this, this);
                break;
            case R.id.img_wb:
                socialHelper.loginWB(this, this);
                break;
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(TestLoginShareActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.find_password:
                startActivity(new Intent(TestLoginShareActivity.this, FindPasswordActivity.class));
                break;

        }
    }



    //用处：qq登录和分享回调，以及微博登录回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (socialHelper != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            socialHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void loginSuccess(ThirdInfoEntity info) {
        tvLoginInfo.setText(toString(info));
    }

    @Override
    public void socialError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareSuccess(int type) {
        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    private String toString(ThirdInfoEntity info) {
        return "登录信息 = {" +
                "unionId='" + info.getUnionId() + '\'' +
                ", openId='" + info.getOpenId() + '\'' +
                ", nickname='" + info.getNickname() + '\'' +
                ", sex='" + info.getSex() + '\'' +
                ", avatar='" + info.getAvatar() + '\'' +
                ", platform='" + info.getPlatform() + '\'' +
                '}';
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socialHelper != null) {
            socialHelper.clear();
        }
    }
}
