package moe.xinmu.android.ahwork;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;
import moe.xinmu.android.ahwork.backend.UserDatabaseUtils;

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
        target.findViewById(R.id.bs_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MasterDatabaseUtils.isLogin(ShopBlock.this.context))
                {
                    new UserDatabaseUtils(target.getContext(),MasterDatabaseUtils.getusername(ShopBlock.this.context)).addtocart(id);
                    Toast.makeText(target.getContext(),"成功增加到购物车",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(target.getContext(),"您未登陆",Toast.LENGTH_SHORT).show();
                }
            }
        });
        refresh();
    }
    private void refresh(){
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(context);
        ho=mdu.getCommodityByid(id);
        if(!(boolean)ho.get("get")){
            throw new Error("数据获取失败");//TODO
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
