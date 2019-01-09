package moe.xinmu.android.ahwork;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;
import moe.xinmu.android.ahwork.backend.UserDatabaseUtils;

public class Cart_Layout   {
    Context context;
    LinearLayout layout;
    UserDatabaseUtils udu;
    MasterDatabaseUtils mdu;
    ArrayList<Cart_Item> aci=new ArrayList<>();
    String username;
    CartFragment cf;
    int count;
    public Cart_Layout(Context context, LinearLayout layout,String username,CartFragment cf){
        this.context=context;
        this.layout=layout;
        this.cf=cf;
        this.username=username;
        udu=new UserDatabaseUtils(context,username);
        mdu=new MasterDatabaseUtils(context);
        getchilditems();
    }

    private void getchilditems() {
        Integer[] aid = udu.getcartIds();
        count=aid.length;
        for (int i : aid)
            aci.add(new Cart_Item(this, context,layout, i, username));
    }
    public Cart_Item[] getallCi(){
        return aci.toArray(new Cart_Item[0]);
    }
    public void removeItem(Cart_Item cart_item){
        aci.remove(cart_item);
        layout.removeView(cart_item.getView());
        udu.removeacart(cart_item.getId());
        //TODO
    }
    public void removeItem(int id){
        Cart_Item ci=null;
        for (Cart_Item cart_item:aci)
            if(cart_item.getId()==id)
                ci=cart_item;
        if(ci!=null)
            removeItem(ci);


    }

    public int getcount(){
        return count;
    }
    public void update(){
        cf.update();
    }
}
