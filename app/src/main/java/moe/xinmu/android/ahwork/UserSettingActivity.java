package moe.xinmu.android.ahwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;

public class UserSettingActivity extends AppCompatActivity {
    LinearLayout ll;
    HashMap<View,View.OnClickListener> vsh=new HashMap<>();
    HashMap<String,View.OnClickListener> ss=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersetting);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        ll=findViewById(R.id.main2_usersettings);
        dataget();
        createOptionTab("Test","0.11");
        createOptionTab("给我一百万","");
    }

    private void give1Million(View view) {
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(getApplicationContext());
        mdu.setUserBalance(mdu.getuserbalance(MasterDatabaseUtils.getusername(getApplicationContext()))+(int)1e6);
        Toast.makeText(getApplicationContext(),"给你一百万",Toast.LENGTH_SHORT).show();
        mdu.close();
    }

    private void dataget() {
        ss.put("Test",this::toLogin);
        ss.put("给我一百万",this::give1Million);
    }
    View createOptionTab(String name,String value){
        return createOptionTab(name,value,true);
    }
    View createOptionTab(String name,String value,boolean cansetting){
        View a=ll.inflate(this,R.layout.items_option_tab,null);
        ((TextView)a.findViewById(R.id.iot_setting_name)).setText(name);
        TextView tv=a.findViewById(R.id.iot_setting_value);
        LinearLayout bt=a.findViewById(R.id.tab_ll);
        if(!cansetting)
            a.findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
        else{
            vsh.put(bt,ss.get(name));
            bt.setOnClickListener(this::tab_onClick);
        }
        tv.setText(value);
        ll.addView(a);
        return a;
    }
    void tab_onClick(View v){
        try{
            vsh.get(v).onClick(v);
        } catch (NullPointerException ignored){ }
    }
    void toLogin(View v){
        startActivity(new Intent(this,LoginActivity.class));
    }
}
