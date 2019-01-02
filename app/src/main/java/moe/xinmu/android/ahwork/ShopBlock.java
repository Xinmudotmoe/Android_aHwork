package moe.xinmu.android.ahwork;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

public class ShopBlock {
    Context context;
    View target;
    ImageView iv;
    TextView title, price;
    int id;
    HashMap<String,Object> ho;
    public ShopBlock(Context context,int id) {
        this.context=context;
        this.id=id;
        target=View.inflate(context,R.layout.block_shop_0,null);
        iv=target.findViewById(R.id.imageView);
        title=target.findViewById(R.id.textView);
        price=target.findViewById(R.id.textView3);
        refresh();
    }
    private void refresh(){
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(context);
        ho=mdu.getCommodityByid(id);
        if(!(boolean)ho.get("get")){
            throw new Error("TODO");//TODO
        }
        String img=(String)ho.get("img");
        try {
            iv.setImageDrawable(Drawable.createFromStream(context.getAssets().open(img),img));
        } catch (IOException e) {
            Log.e("Drawable","io",e);
        }
        title.setText((String)ho.get("title"));
        price.setText("￥ "+ho.get("priceGold"));
    }
    public View getView(){
        return target;
    }
}
