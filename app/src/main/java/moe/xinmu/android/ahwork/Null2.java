package moe.xinmu.android.ahwork;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.system.ErrnoException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Null2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View a=inflater.inflate(R.layout.fragment_null2, null);
        Button ass=a.findViewById(R.id.ass);
        ass.setOnClickListener(v -> {
            MasterDatabaseUtils mdu=new MasterDatabaseUtils(a.getContext());
            //MasterDatabaseUtils.create(mdu);
            Log.e("ASSSS",mdu.checkcancreateuser("ass","ass","ass","ass"));
        });
        return a;

    }

}
