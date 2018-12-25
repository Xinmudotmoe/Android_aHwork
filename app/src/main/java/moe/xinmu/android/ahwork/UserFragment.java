package moe.xinmu.android.ahwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class UserFragment extends Fragment {
    LinearLayout ll;
    ConstraintLayout constraintLayout;
    ImageView setting;
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
        setting=view.findViewById(R.id.setting);
        setting.setOnClickListener(v -> startActivity(new Intent(UserFragment.this.getContext(),AllSettingActivity.class)));
        return view;
    }
}
