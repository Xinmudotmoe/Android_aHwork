package moe.xinmu.android.ahwork;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;



public class LoginActivity extends AppCompatActivity {
    LinearLayout ll;
    HashMap<String,View> sv=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ll=findViewById(R.id.login_ill);
        createEditer();
        findViewById(R.id.login_reg).setOnClickListener(l->startActivity(new Intent(LoginActivity.this,RegisteredActivity.class)));
        findViewById(R.id.login_log).setOnClickListener(l->login());
    }
    void login(){
        String name=((EditText)sv.get(Db.seStr[0]).findViewById(R.id.editText)).getText().toString();
        String password=((EditText)sv.get(Db.seStr[1]).findViewById(R.id.editText)).getText().toString();
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(getApplicationContext());
        if(mdu.login(name,password)){
            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"登陆失败",Toast.LENGTH_SHORT).show();
        }

        //Log.i("name",name);
        //Log.i("password",password);
    }
    void createEditer(){
        for (int i=0;i<Db.defStr.length;i++){
            View lll=View.inflate(this,R.layout.editviewd,null);
            ((ImageView)lll.findViewById(android.R.id.icon)).setImageDrawable(getResources().getDrawable(Db.image[i]));
            EditText et =lll.findViewById(R.id.editText);
            et.setHint(Db.defStr[i]);
            if(!Db.seStr[i].contains("pass"))
                et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            lll.setOnClickListener(this::EditerClick);
            sv.put(Db.seStr[i],lll);
            ll.addView(lll);
        }
    }
    void EditerClick(View v){
        EditText et=v.findViewById(R.id.editText);
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }
    private interface Db{
        int[] image={
                R.drawable.bili_ic_player_login_username_default,
                R.drawable.key
        };
        String[] defStr={
                "请输入用户名/手机号",
                "请输入密码"
        };
        String[] seStr={
                "username",
                "password"
        };
        int color = Color.GRAY;
        int colorSelect = Color.CYAN;
    }
}
