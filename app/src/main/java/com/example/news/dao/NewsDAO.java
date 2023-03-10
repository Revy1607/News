package com.example.news.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.news.data.DbHelper;
import com.example.news.enity.News;

import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    private static final String TABLE_NAME = "News";
    private SQLiteDatabase db;
    public static List<News> listNews = new ArrayList<>();

    public NewsDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(News news){
        ContentValues values = new ContentValues();
        values.put("name", news.getName());
        values.put("link", news.getLink());
        try {
            if (db.insert(TABLE_NAME, null, values) != -1);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean update(News news){
        ContentValues values = new ContentValues(); //nơi chứa dữ liệu
        values.put("id", news.getId());   //thêm id vào dữ liệu
        values.put("name", news.getName()); //thêm tên vào dữ liệu
        values.put("link", news.getLink()); //thêm link vào dữ liệu
        //thực hiện cập nhật
        String strID = String.valueOf(news.getId());
        //kiểm tra kết quả
        try {
            if (db.update(TABLE_NAME, values, "id = ?", new String[]{strID}) != -1);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean delete(int id){
        String strID = String.valueOf(id);
        if (db.delete(TABLE_NAME, "id = ?", new String[]{strID}) >= 0){
            return true;
        }
        return false;
    }
    public List<News> getALL(){
        String sql = "SELECT * FROM "+TABLE_NAME;
        return getData(sql);
    }

    public News getNews(int id){
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=?";
        List<News> list = getData(sql, String.valueOf(id));
        if (list == null){
            return null;
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<News> getData(String sql , String...selectionArgs){
        List<News> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            News obj = new News();
            obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
            obj.setName(cursor.getString(cursor.getColumnIndex("name")));
            obj.setLink(cursor.getString(cursor.getColumnIndex("link")));
            list.add(obj);
        }
        return list;
    }
}
