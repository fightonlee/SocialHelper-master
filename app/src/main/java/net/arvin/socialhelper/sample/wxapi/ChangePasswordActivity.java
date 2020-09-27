package net.arvin.socialhelper.sample.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.arvin.socialhelper.sample.PersonalSpaceActivity;
import net.arvin.socialhelper.sample.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.arvin.socialhelper.sample.TestLoginShareActivity;
import net.arvin.socialhelper.sample.wxapi.Code;
import net.arvin.socialhelper.sample.wxapi.DBOpenHelper;

import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mBtPasswordActivityRegister;
    private RelativeLayout mRlPasswordActivityTop;
    private ImageView mIvPasswordActivityBack;
    private LinearLayout mLlPasswordActivityBody;
    private EditText mEtPasswordActivityUsername;
    private EditText mEtPasswordActivityPassword1;
    private EditText mEtPasswordActivityPassword2;
    private EditText mEtPasswordActivityPhonecodes;
    private ImageView mIvPasswordActivityShowcode;
    private RelativeLayout mRlPasswordActivityBottom;
    private TextView mTvPasswordError;
    private TextView mTvPasswordConfirmError;
    private TextView mTvUsernameError;
    private EditText mEtPasswordActivityPassword3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvPasswordActivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }




    private void initView(){
        mBtPasswordActivityRegister = findViewById(R.id.bt_change_passwordactivity_register);
        mRlPasswordActivityTop = findViewById(R.id.rl_change_passwordactivity_top);
        mIvPasswordActivityBack = findViewById(R.id.iv_change_passwordactivity_back);
        mLlPasswordActivityBody = findViewById(R.id.ll_change_passwordactivity_body);
        mEtPasswordActivityUsername = findViewById(R.id.et_change_passwordactivity_username);
        mEtPasswordActivityPassword1 = findViewById(R.id.et_change_passwordactivity_password1);
        mEtPasswordActivityPassword2 = findViewById(R.id.et_change_passwordactivity_password2);
        mEtPasswordActivityPassword3 = findViewById(R.id.et_change_passwordactivity_password3);
        mEtPasswordActivityPhonecodes = findViewById(R.id.et_change_passwordactivity_phoneCodes);
        mIvPasswordActivityShowcode = findViewById(R.id.iv_change_passwordactivity_showCode);
        mRlPasswordActivityBottom = findViewById(R.id.rl_registeractivity_bottom);
        mTvPasswordError = findViewById(R.id.tv_change_passworderror);
        mTvPasswordConfirmError = findViewById(R.id.tv_change_password2error);
        mTvUsernameError = findViewById(R.id.tv_change_usernameerror);
        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvPasswordActivityBack.setOnClickListener(this);
        mIvPasswordActivityShowcode.setOnClickListener(this);
        mBtPasswordActivityRegister.setOnClickListener(this);
        PasswprdFormatDetect();
        PasswordConfirmDetect();
    }

    private void  PasswprdFormatDetect() {

        mEtPasswordActivityPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //如果失去焦点则进行用户名的检测
                if( mEtPasswordActivityPassword2.hasFocus()==false){
                    //如果用户名长度小于3或者大于9，则提示用户名错误且登陆不可点击
                    if( mEtPasswordActivityPassword2.getText().toString().length()>9|| mEtPasswordActivityPassword2.getText().toString().length()<3){
////                      if(isRight == false) {
                        mTvPasswordError.setText("密码不合法！");
                        mBtPasswordActivityRegister.setClickable(false);
                    }else{
                        //如果用户名合法且密码不为空，设置提示字体消失按钮可点击
                        mBtPasswordActivityRegister.setClickable(true);
                        mTvPasswordError.setText("");
                    }
                }
            }
        });
    }

    private void  PasswordConfirmDetect() {
        mEtPasswordActivityPassword3.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //如果失去焦点则进行用户名的检测
                if(mEtPasswordActivityPassword3.hasFocus()==false) {
                    //如果用户名长度小于3或者大于9，则提示用户名错误且登陆不可点击
                    if((mEtPasswordActivityPassword3.getText().toString()).equals(mEtPasswordActivityPassword2.getText().toString())){
//                      if(isRight == false) {
                        mTvPasswordConfirmError.setText("");
                        mBtPasswordActivityRegister.setClickable(true);
                    }else{
                        //如果用户名合法且密码不为空，设置提示字体消失按钮可点击
                        mBtPasswordActivityRegister.setClickable(false);
                        mTvPasswordConfirmError.setText("两次密码输入不一致");
                    }
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, PersonalSpaceActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_change_passwordactivity_showCode:    //改变随机验证码的生成
                mIvPasswordActivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_change_passwordactivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
//                String regex = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$";
                String username = mEtPasswordActivityUsername.getText().toString().trim();
                String password1 = mEtPasswordActivityPassword1.getText().toString().trim();
                String password2 = mEtPasswordActivityPassword2.getText().toString().trim();
                String password3 = mEtPasswordActivityPassword3.getText().toString().trim();
                String phoneCode = mEtPasswordActivityPhonecodes.getText().toString().toLowerCase();
//                boolean isRight =  password1.matches(regex);
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2)&& !TextUtils.isEmpty(password3)&& !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)  ) {
                        if (password2.equals(password3)) {//将用户名和密码加入到数据库中
                            ArrayList<User> data = mDBOpenHelper.getAllData();
                            boolean match = false;
                            for (int i = 0; i < data.size(); i++) {
                                User user = data.get(i);
                                if (username.equals(user.getName()) && password1.equals(user.getPassword())) {
                                    match = true;
                                    break;
                                } else {
                                    match = false;
                                }
                            }
                            if (match) {
                                mDBOpenHelper.update(password2,username);
                                Intent intent2 = new Intent(this, PersonalSpaceActivity.class);
                                startActivity(intent2);
                                finish();
                                Toast.makeText(this, "验证通过，修改密码成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "两次密码输入不一致,修改密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                            Toast.makeText(this, "验证码错误,修改密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                            Toast.makeText(this, "未完善信息，修改密码失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
    }

}

