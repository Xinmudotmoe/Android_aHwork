package moe.xinmu.android.ahwork;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserDataSettingActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_setting);
        lv=findViewById(R.id.uds_lv);
        /*lv.setAdapter(new SimpleAdapter(this,
                null,R.layout.items_option_tab,new String[]{},new int[]{}));*/
    }
}
