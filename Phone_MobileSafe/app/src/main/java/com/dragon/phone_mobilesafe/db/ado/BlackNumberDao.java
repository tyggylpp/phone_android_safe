package com.dragon.phone_mobilesafe.db.ado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

import com.dragon.phone_mobilesafe.bean.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-04.
 */
public class BlackNumberDao {
    public BlackNumberDBOpenHelper helper;
    public BlackNumberDao(Context context)
    {
    helper=new BlackNumberDBOpenHelper(context);
    }
    /*
    黑名单号码
    拦截模式
     */
    public boolean   add(String number, String mode)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("number",number);
        values.put("mode",mode);

      long rowid= db.insert("blackNumber",null,values);
        if(rowid!=-1)return true;
       return false;
    }
    public boolean update(String number,String mode)
    {
        return true;
    }
    public boolean delete(String number)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        int rowNumber=  db.delete("blackNumber","number=?",new String[]{number});
        if(rowNumber==0)return false;
        return true;
    }
    public boolean  changeNumberMode(String number,String mode)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("mode",mode);
        int row=db.update("blackNumber",values,"number=?",new String[]{number});
        if(row==0)return false;
        return true;
    }
    public  String findNumber(String number)
    {
        String mode="";
        SQLiteDatabase db=helper.getReadableDatabase();
       Cursor cursor= db.query("blackNumber",new String[]{"mode"},"number=?",new String[]{number},null,null,null);
        if(cursor.moveToNext()){
            mode=cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }
    public List<BlackNumberInfo> findAll()
    {
        SQLiteDatabase db=helper.getReadableDatabase();
        List<BlackNumberInfo>list=new ArrayList<BlackNumberInfo>();

       Cursor cursor= db.query("blackNumber",new String[]{"number","mode"},null,null,null,null,null);
        while (cursor.moveToNext())
        {
            BlackNumberInfo info=new BlackNumberInfo();
           info.setNumber(cursor.getString(0));
           info.setMode(cursor.getString(1));
           list.add(info);
        }
        cursor.close();
        db.close();
    //    SystemClock.sleep(3000);//休眠3S
        return list;
    }
    public  List<BlackNumberInfo> findPar(int pageNumber,int pageSize)
    {
        SQLiteDatabase db=helper.getReadableDatabase();
       Cursor cursor= db.rawQuery("select number,mode from blackNumber limit ? offset ?",new String[]{String.valueOf(pageSize),String.valueOf(pageNumber*pageSize)});
        List<BlackNumberInfo> list=new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext())
        {
           BlackNumberInfo info=new BlackNumberInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getString(1));
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }
    public  List<BlackNumberInfo> findPar2(int startIndex,int maxcount)
    {
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor= db.rawQuery("select number,mode from blackNumber limit ? offset ?",new String[]{String.valueOf(maxcount),String.valueOf(startIndex)});
        List<BlackNumberInfo> list=new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext())
        {
            BlackNumberInfo info=new BlackNumberInfo();
            info.setNumber(cursor.getString(0));
            info.setMode(cursor.getString(1));
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getTotalNumber()
    {
        int total=0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor= db.rawQuery("select count(0) from blackNumber",null);
        cursor.moveToNext();
        total=cursor.getInt(0);
        cursor.close();
        db.close();

        return total;
    }



}
