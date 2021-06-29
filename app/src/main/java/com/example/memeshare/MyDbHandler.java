package com.example.memeshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context){
        super(context,Params.DB_NAME,null,Params.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+Params.TABLE_NAME+"("+Params.KEY_ID+" INTEGER PRIMARY KEY, "+Params.KEY_MEME_IMAGE+" TEXT )";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addTodo(SavedMemes meme){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_MEME_IMAGE,meme.getImageURL());
        db.insert(Params.TABLE_NAME,null,values);
        db.close();
    }

    public List<SavedMemes> getAllTodos(){
        List<SavedMemes>memesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                SavedMemes meme = new SavedMemes();
                meme.setId(Integer.parseInt(cursor.getString(0)));
                meme.setImageURL(cursor.getString(1));
                memesList.add(meme);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return memesList;
    }
    public void deleteTodo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
