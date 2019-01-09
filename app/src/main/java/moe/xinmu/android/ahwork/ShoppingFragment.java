package moe.xinmu.android.ahwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;


public class ShoppingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ass= inflater.inflate(R.layout.fragment_shoping, null);
        LinearLayout lv=ass.findViewById(R.id.assdf);
        for (int a:new MasterDatabaseUtils(ass.getContext()).getAllid()){
            View b=new ShopBlock(ass.getContext(),a).getView();
            lv.addView(b);
        }
        return ass;
    }
}
