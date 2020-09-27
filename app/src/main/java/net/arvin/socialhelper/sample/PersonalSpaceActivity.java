package net.arvin.socialhelper.sample;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import net.arvin.socialhelper.sample.wxapi.ChangePasswordActivity;
import net.arvin.socialhelper.sample.wxapi.MainActivity;
import net.arvin.socialhelper.sample.wxapi.ShareActivity;

public class PersonalSpaceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space);
        initView();

    }
    private void initView(){
        Button tryrecordbutton = (Button)findViewById(R.id.bt_try_record);
        Button mBtChangePassword = findViewById(R.id.bt_change_password);

        tryrecordbutton.setOnClickListener((View.OnClickListener) this);
        mBtChangePassword.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_try_record:
                Intent intent = new Intent(PersonalSpaceActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_change_password:
                Intent intent1 = new Intent(PersonalSpaceActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent Intent2 = new Intent();
            Intent2 = new Intent(PersonalSpaceActivity.this, MainActivity.class);
            startActivity(Intent2);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}