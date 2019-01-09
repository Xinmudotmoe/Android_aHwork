package moe.xinmu.android.ahwork.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDatabaseUtils {
    private Context context;
    private String uuid;
    private DatabaseUtil dbu;
    private SQLiteDatabase db;
    public UserDatabaseUtils(Context context,String username){
        this.context=context;
        uuid=new MasterDatabaseUtils(context).getUUID(username);
        dbu=new DatabaseUtil(context,uuid,null,1);
        db=dbu.getWritableDatabase();
        if(dbu.needcreate)
            create();
    }
    private void create() {
        for(String str:userDBcreate)
            db.execSQL(str);
    }
    private static String[] userDBcreate={
            "CREATE TABLE cart( productId INTEGER UNIQUE PRIMARY KEY, count INTEGER);",
            "insert into cart(productId,count) values(1,1);"
    };
    public Integer[] getcartIds(){
        Cursor cursor=db.rawQuery("select productId from cart;",null);
        ArrayList<Integer> ali=new ArrayList<>();
        while (cursor.moveToNext())
            ali.add(cursor.getInt(0));
        cursor.close();
        return ali.toArray(new Integer[0]);
    }
    public int getcartcount(int id){
        Cursor cursor=db.rawQuery("select count from cart where productId=?;",new String[]{Integer.toString(id)});
        int count=0;
        if(cursor.moveToFirst())
            count=cursor.getInt(0);
        cursor.close();
        return count;
    }
    public void setcartcount(int id,int count){
        db.execSQL("update cart set count=? where productId=?;",new String[]{Integer.toString(count),Integer.toString(id)});
    }
    public void removeacart(int id){
        db.execSQL("delete from cart where productId=?;",new String[]{Integer.toString(id)});
    }
    public void addtocart(int id){
        Cursor cursor=db.rawQuery("select count from cart where productId=?",new String[]{Integer.toString(id)});
        boolean flag=false;
        if(cursor.moveToFirst())
            flag=true;
        if (flag) {
            int count=cursor.getInt(0);
            db.execSQL("update cart set count=?+1 where productId=? AND count<=?+1;", new String[]{ Integer.toString(count),Integer.toString(id), Integer.toString(count)});
        }else
            db.execSQL("insert into cart values(?,1)",new String[]{Integer.toString(id)});
        cursor.close();
    }
    public void close(){
        db.close();
        dbu.close();
    }
}
