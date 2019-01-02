package moe.xinmu.android.ahwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
public class UserDatabaseUtils {
    private Context context;
    private String uuid;
    private DatabaseUtil dbu;
    private SQLiteDatabase db;
    public UserDatabaseUtils(Context context,String username){
        this.context=context;
        uuid=new MasterDatabaseUtils(context).getUUID(username);
        dbu=new DatabaseUtil(context,uuid,null,1);
        if(dbu.needcreate){
            create();
        }
        db=dbu.getWritableDatabase();
    }
    private void create() {
        for(String str:userDBcreate)
            db.execSQL(str);
    }
    private static String[] userDBcreate={
            "CREATE TABLE cart(productId INTEGER REFERENCES user (id) UNIQUE PRIMARY KEY, count INTEGER); "
    };
}
