package moe.xinmu.android.ahwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AllSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsetting);
        ActionBar ab=getSupportActionBar();

        findViewById(R.id.as_exit).setOnClickListener((l)->{
            SharedPreferences.Editor editor=getApplicationContext().getSharedPreferences("user_login_information",Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.putBoolean("isLogin",false);
            editor.apply();
            Toast.makeText(getApplicationContext(),"已成功退出登陆",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AllSettingActivity.this,LoginActivity.class));
        });

    }
}
