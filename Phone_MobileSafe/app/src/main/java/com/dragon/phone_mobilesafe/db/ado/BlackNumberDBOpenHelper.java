package com.dragon.phone_mobilesafe.db.ado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by pc on 2016-08-04.
 */
public class BlackNumberDBOpenHelper extends SQLiteOpenHelper {


    public BlackNumberDBOpenHelper(Context context)
    {
        super(context,"safe.db",null,1);

    }
//    public BlackNumberDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        //super(context, "safe.db", null, 1);
//        super(context,name,factory,version);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("", "onCreate: SqliteDataBase on Create");
          sqLiteDatabase.execSQL("create table blacknumber (_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
