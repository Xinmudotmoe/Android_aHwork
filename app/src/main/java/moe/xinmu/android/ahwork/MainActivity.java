package moe.xinmu.android.ahwork;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    FragmentTabHost fth;
    Class[] fragments={
            Null.class,
            Null1.class,
            Null2.class,
            Null3.class,
                };
    String[] tabname={
            "d","b","a","v"
    };
    int[] noselectimage={
            R.drawable.icon_detail_cache_disable,
            R.drawable.icon_dynamic,
            R.drawable.icon_mine,
            R.drawable.icon_videos
    };
    int[] selectimage={
            R.drawable.icon_detail_cache,
            R.drawable.icon_dynamic_selected,
            R.drawable.icon_mine_selected,
            R.drawable.icon_videos_selected
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fth=findViewById(android.R.id.tabhost);
        fth.setup(this,getSupportFragmentManager(),R.id.main_llf);
        initTab();
    }

    private void initTab() {
        for (int i=0;i<4;i++){
            TabHost.TabSpec tabSpec=fth.newTabSpec(tabname[i]).setIndicator(getTabView(i));
            fth.addTab(tabSpec,fragments[i],null);
        }
        fth.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                TabWidget tw=fth.getTabWidget();
                for (int i=0;i<fragments.length;i++){
                    View view=tw.getChildTabViewAt(i);
                    TextView tv=view.findViewById(R.id.text);
                    ImageView iv=view.findViewById(R.id.iv);
                    tv.setText(tabname[i]);
                    if(i==fth.getCurrentTab()){
                        tv.setTextColor(Color.BLUE);
                        iv.setImageResource(selectimage[i]);
                    }
                    else{
                        tv.setTextColor(Color.GRAY);
                        iv.setImageResource(noselectimage[i]);
                    }
                }
            }
        });
    }

    private View getTabView(int i) {
        View view=LayoutInflater.from(this).inflate(R.layout.tab_s,null);
        TextView tv=view.findViewById(R.id.text);
        ImageView iv=view.findViewById(R.id.iv);
        tv.setText(tabname[i]);
        if(i==0){
            tv.setTextColor(Color.BLUE);
            iv.setImageResource(selectimage[i]);
        }
        else{
            tv.setTextColor(Color.GRAY);
            iv.setImageResource(noselectimage[i]);
        }
        return view;

    }

}
