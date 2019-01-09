package moe.xinmu.android.ahwork.backend;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;


//该类负责主DataBase操作
public class MasterDatabaseUtils {
    private static final String databaseName="master.db";
    private static final int version=1;
    private DatabaseUtil masterdu;
    private SQLiteDatabase sdb;
    private Context context;
    public MasterDatabaseUtils(Context context){
        if(context==null)
            throw new Error("need Context");
        this.context=context;
        if(masterdu==null)
            masterdu=new DatabaseUtil(context,databaseName,null,1);
        sdb=masterdu.getWritableDatabase();
        if(checkNeedInit())
            create();
    }
    public boolean checkNeedInit() {
        return masterdu.needcreate;
    }
    public void execSQL(String sql)  {
        try{
            sdb.execSQL(sql);
        }catch (SQLException ignored){
            Log.e("execSQL","sql is "+sql,ignored);
        }
    }
    public void execSQL(String sql, Object[] bindArgs) {
        try{
            sdb.execSQL(sql,bindArgs);
        }catch (SQLException ignored){
            Log.e("execSQL","sql is "+sql+", args is"+ Arrays.toString(bindArgs),ignored);
        }
    }

    private void create(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DatabaseUtils_someDatabase.master.dat_formats,Locale.CHINESE);
        for (String i :DatabaseUtils_someDatabase.master.createmaster)
            execSQL(i);
        for (String i :DatabaseUtils_someDatabase.master.dat){
            String [] as=i.split("\\|");
            Object[] o=new Object[10];
            for (int j = 0; j < as.length; j++)
                o[j]=as[j];
            o[0]=Integer.valueOf(as[0]);
            o[4]=Integer.valueOf(as[4]);
            try {
                o[5]=dateFormat.parse(as[5]).getTime();
                o[6]=dateFormat.parse(as[6]).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            o[7]=Integer.valueOf(as[7]);
            o[8]=Integer.valueOf(as[8]);
            o[9]=Integer.valueOf(as[9]);
            execSQL("insert into commodity ( productId , title , description, img , priceGold , expireTime , releaseTime , quantity , buyNum , type )\n" +
                    "values(?,?,?,?,?,?,?,?,?,?);",o);
        }
    }
    public static void create(MasterDatabaseUtils mdu){
        //用来强制刷新数据库
        mdu.create();
    }
    public String checkcancreateuser(String pickname,String name,String pass,String phone){
        if(pickname.isEmpty())
            return "昵称不能为空";
        if(name.isEmpty())
            return "用户名不能为空";
        if(pass.isEmpty())
            return "密码不能为空";
        if(phone.isEmpty())
            return "手机号不能为空";
        Cursor cursor=sdb.query("user", new String[]{"name"},"name=?",new String[]{name},null,null,null);

        if(cursor.getCount()>0){
            cursor.close();
            return "用户名重复";
        }
        cursor.close();
        cursor=sdb.query("user", new String[]{"name"},"phone=?",new String[]{phone},null,null,null);
        if(cursor.getCount()>0){
            cursor.close();
            return "手机号重复";
        }
        return "";
    }
    public boolean createUser(String pickname,String name,String pass,String phone){
        try {
            sdb.execSQL("insert into user(pickname,name,phone,pass,uuid,balance)values(?,?,?,?,?,?)",new Object[]{pickname,name,phone,pass,UUID.randomUUID().toString(),0.0f});
            return true;
        }catch (SQLException ignored){
            return false;
        }
    }
    public boolean login(String name,String pass){
        Cursor cursor=sdb.rawQuery("select name,pass from user where name=? or phone=?",new String[]{name,name});
        Log.e("SQL","i");
        if(!cursor.moveToFirst()){
            Log.e("SQL","e");
            cursor.close();
            return false;
        }
        String uname=cursor.getString(0);
        String upass=cursor.getString(1);
        cursor.close();
        if(pass.equals(upass)){
            SharedPreferences.Editor editor=context.getSharedPreferences("user_login_information",Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.putBoolean("isLogin",true);
            editor.putString("name",uname);
            editor.putString("pass",upass);
            editor.apply();
            return true;
        }
        return false;
    }
    public HashMap<String,Object> getCommodityByid(int id){
        HashMap<String,Object> so= new HashMap<>();
        Cursor cursor=sdb.rawQuery("select productId, title, description, img, priceGold, expireTime, releaseTime, quantity, buyNum, type from commodity where productId=?;",new String[]{Integer.toString(id)});
        if(!cursor.moveToFirst())
            so.put("get",false);
        else {
            so.put("get",true);
            so.put("id",cursor.getInt(0));
            so.put("title",cursor.getString(1));
            so.put("description",cursor.getString(2));
            String[] $1=cursor.getString(3).split("/");
            String[] $2=new String[$1.length-3];
            System.arraycopy($1,3,$2,0,$2.length);
            so.put("img",moe.xinmu.android.ahwork.utils.StringUtil.join("/",$2));
            so.put("priceGold",cursor.getInt(4));
            so.put("expireTime",new Date(cursor.getInt(5)));
            so.put("releaseTime",new Date(cursor.getInt(6)));
            so.put("quantity",cursor.getInt(7));
            so.put("buyNum",cursor.getInt(8));
            so.put("type",cursor.getInt(9));
        }
        cursor.close();
        return so;
    }
    public int[] getAllid(){
        ArrayList<Integer> al=new ArrayList<>();
        Cursor cursor=sdb.rawQuery("select productId from commodity",new String[0]);
        while (cursor.moveToNext())
            al.add(cursor.getInt(0));
        int[] a=new int[al.size()];
        for (int i = 0; i < al.size(); i++)
            a[i]=al.get(i);

        return a;

    }
    public String getUUID(String name){
        Cursor cursor=sdb.rawQuery("select uuid from user where name=?",new String[]{name});
        String uuid;
        if(cursor.moveToNext())
            uuid=cursor.getString(0);
        else
            uuid="";
        cursor.close();
        return uuid;
    }
    public String getuserpickname(String name){
        Cursor cursor=sdb.rawQuery("select pickname from user where name=?",new String[]{name});
        String data="";
        if(cursor.moveToNext())
            data=cursor.getString(0);
        cursor.close();
        return data;
    }
    public double getuserbalance(String name){
        Cursor cursor=sdb.rawQuery("select balance from user where name=?",new String[]{name});
        double data=0f;
        if(cursor.moveToNext())
            data=cursor.getDouble(0);
        cursor.close();
        return data;
    }
    public int getitemquantity(int id){
        Cursor cursor=sdb.rawQuery("select quantity from commodity where productId=?",new String[]{Integer.toString(id)});
        int i=0;
        if(cursor.moveToFirst())
            i=cursor.getInt(0);
        return i;
    }
    public boolean isLogin(){
        return context.getSharedPreferences("user_login_information",Context.MODE_PRIVATE).getBoolean("isLogin",false);
    }
    public static boolean isLogin(Context context){
        return context.getSharedPreferences("user_login_information",Context.MODE_PRIVATE).getBoolean("isLogin",false);
    }
    public static String getusername(Context context){
        if(isLogin(context))
            return context.getSharedPreferences("user_login_information",Context.MODE_PRIVATE).getString("name","");
        else
            return "";
    }
    public static String getuseruuid(Context context,String name){
        MasterDatabaseUtils mdu=new MasterDatabaseUtils(context);
        String uuid=mdu.getUUID(name);
        mdu.close();
        return uuid;
    }
    public void close(){
        sdb.close();
        masterdu.close();
    }
    public double getUserBalance() {
        if(!isLogin())
            return 0;
        Cursor cursor=sdb.rawQuery("select balance from user where name=?",new String[]{getusername(context)});
        double balance=0f;
        if(cursor.moveToFirst())
            balance=cursor.getDouble(0);
        cursor.close();
        return balance;
    }
    public void setUserBalance(double balance){
        if(!isLogin())
            return ;
        sdb.execSQL("update user set balance=? where name=?",new String[]{Double.toString(balance),getusername(context)});

    }
}
