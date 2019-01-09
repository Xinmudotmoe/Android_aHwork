package moe.xinmu.android.ahwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;
import moe.xinmu.android.ahwork.backend.UserDatabaseUtils;

public class CartFragment extends Fragment {
    LinearLayout ll;
    Cart_Layout cl;
    View a;
    TextView cart_price;
    Button cart_buy;
    HashMap<Integer, HashMap<String, Object>> data = new HashMap<>();
    HashMap<Integer, Integer> itemcounts = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        a = inflater.inflate(R.layout.fragment_cart, null);
        ll = a.findViewById(R.id.cartLL);
        if (MasterDatabaseUtils.isLogin(a.getContext())) {
            cl = new Cart_Layout(a.getContext(), ll, MasterDatabaseUtils.getusername(a.getContext()), CartFragment.this);
            if (cl.getcount() == 0)
                ((TextView) View.inflate(a.getContext(), R.layout.tv, ll).findViewById(android.R.id.text1)).setText("购物车为空");
            else {
                init();
                update();
            }
        } else
            ((TextView) View.inflate(a.getContext(), R.layout.tv, ll).findViewById(android.R.id.text1)).setText("您未登陆");
        return a;
    }

    private void init() {
        cart_price = a.findViewById(R.id.cart_price);
        cart_buy = a.findViewById(R.id.cart_buy);
        MasterDatabaseUtils mdu = new MasterDatabaseUtils(a.getContext());
        for (int i : mdu.getAllid())
            data.put(i, mdu.getCommodityByid(i));
        mdu.close();
        cart_buy.setOnClickListener(l->{
            MasterDatabaseUtils mddu=new MasterDatabaseUtils(a.getContext());
            UserDatabaseUtils udu = new UserDatabaseUtils(a.getContext(), MasterDatabaseUtils.getusername(a.getContext()));
            double pay=Double.parseDouble(cart_price.getText().toString());
            double balance=mddu.getUserBalance();
            if(pay==0.0d)
                Toast.makeText(getContext(),"你啥都没买",Toast.LENGTH_LONG).show();
            else if(pay>balance)
                Toast.makeText(getContext(),"你没这么多钱",Toast.LENGTH_LONG).show();
            else
            {
                Toast.makeText(getContext(),"购买成功",Toast.LENGTH_LONG).show();
                for (int i:itemcounts.keySet()) {
                    udu.removeacart(i);
                    cl.removeItem(i);
                }
                mddu.setUserBalance(balance-pay);
                update();
            }
            udu.close();
            mddu.close();

        });
    }

    public void update() {
        itemcounts.clear();
        if (MasterDatabaseUtils.isLogin(a.getContext())) {
            UserDatabaseUtils udu = new UserDatabaseUtils(a.getContext(), MasterDatabaseUtils.getusername(a.getContext()));
            for (Cart_Item ci : cl.getallCi())
                if (ci.getischeck())
                    itemcounts.put(ci.getId(), udu.getcartcount(ci.getId()));
            udu.close();
            double Y=0f;
            for (int key:itemcounts.keySet())
                Y+=(double)((Integer) data.get(key).get("priceGold") * (Integer)itemcounts.get(key));
            cart_price.setText(Double.toString(Y));
        }
    }

}