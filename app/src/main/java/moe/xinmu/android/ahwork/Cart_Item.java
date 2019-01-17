package moe.xinmu.android.ahwork;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import moe.xinmu.android.ahwork.backend.MasterDatabaseUtils;
import moe.xinmu.android.ahwork.backend.UserDatabaseUtils;

public class Cart_Item {
    private Cart_Layout cl;
    private Context context;
    private int id;
    private String username;
    View view;
    CheckBox check;
    TextView name,
            price;
    Button delete;
    ImageView iv;
    EditText item_number;
    UserDatabaseUtils udu;
    Button sub,add;
    MasterDatabaseUtils mdu;
    public Cart_Item(Cart_Layout cl, Context context, LinearLayout layout, int id, String username){
        view=View.inflate(context,R.layout.cart_item,null);
        check=view.findViewById(R.id.item_check);
        name=view.findViewById(R.id.item_name);
        price=view.findViewById(R.id.item_price);
        iv=view.findViewById(R.id.item_image);
        delete=view.findViewById(R.id.item_delete);
        item_number=view.findViewById(R.id.item_number);
        sub=view.findViewById(R.id.item_sub);
        add=view.findViewById(R.id.item_add);
        this.cl=cl;
        this.id=id;
        this.username=username;
        mdu=new MasterDatabaseUtils(context);
        HashMap<String,Object> data=mdu.getCommodityByid(id);
        udu=new UserDatabaseUtils(context,username);
        name.setText((String)data.get("title"));
        try {
            iv.setImageDrawable(Drawable.createFromStream(context.getResources().getAssets().open((String)data.get("img")),(String)data.get("img")));
        } catch (IOException e) {
            Log.e("image","can`t open",e);
        }
        price.setText("￥"+data.get("priceGold"));
        item_number.setText(Integer.toString(udu.getcartcount(id)));
        check.setOnCheckedChangeListener((buttonView, isChecked) -> {

            cl.update();
        });
        sub.setOnClickListener(v -> {
            int count=new Integer(item_number.getText().toString())-1;
            if (count>0){
                item_number.setText(Integer.toString(count));
                udu.setcartcount(id,count);
                cl.update();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=new Integer(item_number.getText().toString())+1;
                if(mdu.getitemquantity(id)>=count){
                    item_number.setText(Integer.toString(count));
                    udu.setcartcount(id,count);
                    cl.update();
                }
            }
        });
        item_number.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                int num = new Integer(((EditText) v).getText().toString());
                if(num<0){
                    ((EditText) v).setText("0");
                    udu.setcartcount(id,num);
                    cl.update();
                }
                int nummax=mdu.getitemquantity(id);
                if(num>nummax){
                    ((EditText) v).setText(Integer.toString(nummax));
                    udu.setcartcount(id,nummax);
                    cl.update();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.removeItem(Cart_Item.this);
                cl.update();
            }
        });
        layout.addView(getView());
    }
    public View getView(){
        return view;//TODO
    }

    public int getId() {
        return id;
    }
    public boolean getischeck(){
        return check.isChecked();
    }
}
