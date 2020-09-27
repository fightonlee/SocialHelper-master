package net.arvin.socialhelper.sample.wxapi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import net.arvin.socialhelper.sample.PersonalSpaceActivity;
import net.arvin.socialhelper.sample.R;
import net.arvin.socialhelper.sample.TestLoginShareActivity;

import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
/**
 * Created by littlecurl 2018/6/24
 */

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // 初始化控件对象
        Button mBtMainLogout = findViewById(R.id.bt_main_logout);
        Button mBtnshareinterface = findViewById(R.id.bt_share);
        Button mBtnpersonalspace = findViewById(R.id.bt_personal_space);

        // 绑定点击监听器
        mBtMainLogout.setOnClickListener(this);
        mBtnshareinterface.setOnClickListener(this);
        mBtnpersonalspace.setOnClickListener(this);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_main_logout:
                Intent intent = new Intent(this, TestLoginShareActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_share:
                Intent intent1 = new Intent(this, ShareActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_personal_space:
                Intent intent2 = new Intent(this, PersonalSpaceActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }
}