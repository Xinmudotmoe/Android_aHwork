package moe.xinmu.android.ahwork.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseUtil extends SQLiteOpenHelper {
    public Context context;

    public DatabaseUtil( @Nullable Context context,  @Nullable String name,  @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;//我也不知道存这个有啥用，总之先存着了
    }
    public boolean needcreate=false;
    @Override
    public void onCreate(SQLiteDatabase db) {
        needcreate=true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
