package net.arvin.socialhelper.sample;

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

import net.arvin.socialhelper.sample.wxapi.Code;
import net.arvin.socialhelper.sample.wxapi.DBOpenHelper;
/**
 * Created by littlecurl 2018/6/24
 */
/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;
    private TextView mTvPasswordError;
    private TextView mTvPasswordConfirmError;
    private TextView mTvUsernameError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(RegisterActivity.this, TestLoginShareActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);
        mTvPasswordError = findViewById(R.id.tv_passworderror);
        mTvPasswordConfirmError = findViewById(R.id.tv_password2error);
        mTvUsernameError = findViewById(R.id.tv_usernameerror);
        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
//        UsernameExistDetect();
        UsernammeFormatDetect();
        PasswprdFormatDetect();
        PasswordConfirmDetect();
    }
    private void  UsernammeFormatDetect() {
        mEtRegisteractivityUsername.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //如果失去焦点则进行用户名的检测
                if(mEtRegisteractivityUsername.hasFocus()==false ){
                    //如果用户名长度小于3或者大于9，则提示用户名错误且登陆不可点击
                    if(mEtRegisteractivityUsername.getText().toString().length()>9||mEtRegisteractivityUsername.getText().toString().length()<3){
                        mTvUsernameError.setText("账户名称不合法！");
                        mBtRegisteractivityRegister.setClickable(false);
                    }else{
                        //如果用户名合法且密码不为空，设置提示字体消失按钮可点击
                        mBtRegisteractivityRegister.setClickable(true);
                        mTvUsernameError.setText("");
                        if(!mDBOpenHelper.isUsernameExist(mEtRegisteractivityUsername.getText().toString())){
                            mTvUsernameError.setText("该账号已存在！");
                            mBtRegisteractivityRegister.setClickable(false);
                        }else{
                            mTvUsernameError.setText("");
                            mBtRegisteractivityRegister.setClickable(true);
                        }
                    }
                }
            }
        });
    }

    private void  PasswprdFormatDetect() {
        mEtRegisteractivityPassword1.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //如果失去焦点则进行用户名的检测
                if(mEtRegisteractivityPassword1.hasFocus()==false){
                    //如果用户名长度小于3或者大于9，则提示用户名错误且登陆不可点击
                    if(mEtRegisteractivityPassword1.getText().toString().length()>9||mEtRegisteractivityPassword1.getText().toString().length()<3){
                          mTvPasswordError.setText("密码不合法！");
                          mBtRegisteractivityRegister.setClickable(false);
                    }else{
                        //如果用户名合法且密码不为空，设置提示字体消失按钮可点击
                        mBtRegisteractivityRegister.setClickable(true);
                        mTvPasswordError.setText("");
                        }
                    }
                }


            });
        }

    private void  PasswordConfirmDetect() {
        mEtRegisteractivityPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //如果失去焦点则进行用户名的检测
                if(mEtRegisteractivityPassword1.hasFocus()==false) {
                    //如果用户名长度小于3或者大于9，则提示用户名错误且登陆不可点击
                    if((mEtRegisteractivityPassword2.getText().toString()).equals(mEtRegisteractivityPassword1.getText().toString())){
                        mTvPasswordConfirmError.setText("");
                        mBtRegisteractivityRegister.setClickable(true);
                    }else{
                        //如果用户名合法且密码不为空，设置提示字体消失按钮可点击
                        mBtRegisteractivityRegister.setClickable(false);
                        mTvPasswordConfirmError.setText("两次密码输入不一致");
                    }
                }

            }


        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, TestLoginShareActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
//                String regex = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$";
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
//                boolean isRight =  password1.matches(regex);
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(phoneCode) ) {
                        if (phoneCode.equals(realCode)) {
                            if (password1.equals(password2)) {//将用户名和密码加入到数据库中
                                mDBOpenHelper.add(username, password1);
                                Intent intent2 = new Intent(this, TestLoginShareActivity.class);
                                startActivity(intent2);
                                finish();
                                Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(this, "两次密码输入不一致,注册失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                        }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}

