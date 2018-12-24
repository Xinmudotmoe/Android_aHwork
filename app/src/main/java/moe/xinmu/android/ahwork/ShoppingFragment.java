package moe.xinmu.android.ahwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShoppingFragment extends Fragment {
    private List<Map<String, Object>> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ass= inflater.inflate(R.layout.fragment_shoping, null);
        ListView lv=ass.findViewById(R.id.assdf);
        lv.setAdapter(new SimpleAdapter(ass.getContext(),getData(),
                R.layout.block_shop_0,new String[]{"img","title","price","shopitemid"},
                new int[]{R.id.imageView,R.id.textView,R.id.textView3,R.id.textView4}));

        lv.setOnItemClickListener((parent, view, position, id) -> Log.d("click",Long.toString(id)));
        return ass;
    }

    private List<Map<String,Object>> getData() {
        list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img",R.drawable.icon_videos);
        map.put("title","video");
        map.put("price","$22.00");
        map.put("shopitemid",Long.toString(1234156));
        list.add(map);
        return list;
    }
}
