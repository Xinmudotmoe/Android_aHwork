package moe.xinmu.android.ahwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;


public class UserFragment extends Fragment {
    LinearLayout ll;
    ConstraintLayout constraintLayout;
    ImageView setting;
    TextView userName,balance_Integer,balance_decimal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user, null);
        ll=view.findViewById(R.id.fn3_ll);
        constraintLayout=view.findViewById(R.id.user_cl);
        constraintLayout.setOnClickListener(v -> {
            Intent it=new Intent(inflater.getContext(),UserSettingActivity.class);
            inflater.getContext().startActivity(it);
        });
        userName=view.findViewById(R.id.userName);
        balance_Integer=view.findViewById(R.id.balance_Integer);
        balance_decimal=view.findViewById(R.id.balance_decimal);
        setting=view.findViewById(R.id.setting);
        setting.setOnClickListener(v -> startActivity(new Intent(UserFragment.this.getContext(),AllSettingActivity.class)));
        initialize(getUserdata(view.getContext()));
        return view;
    }
    private HashMap<String,String> getUserdata(Context context){
        SharedPreferences sp=context.getSharedPreferences("user_login_information",Context.MODE_PRIVATE);
        if(!sp.getBoolean("isLogin",false))
            return (HashMap<String,String>)raw.clone();
        HashMap<String,String> hm=new HashMap<>();
        String username=sp.getString("name","");
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(context);
        hm.put("name",mdu.getuserpickname(username));
        hm.put("balance",doubletobalancestring(mdu.getuserbalance(username)));
        return hm;
    }
    private void initialize(HashMap<String,String> hm){
        String name=hm.get("name");
        userName.setText(name);
        String bal=hm.get("balance");
        String bali=bal.split("\\.")[0];
        String bald=bal.split("\\.")[1];
        balance_Integer.setText(bali);
        balance_decimal.setText(bald);
    }
    private static String doubletobalancestring(double d){
        return new DecimalFormat("####0.00").format(d);
    }
    private static HashMap<String,String> raw=new HashMap<>();
    static {
        raw.put("name","NoLogin");
        raw.put("balance","0.00");
    }

}
