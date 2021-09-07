package com.example.memeshare;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SavedPOJO.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public static RoomDB instance;
    public abstract SavedDao savedDao();
    public static synchronized RoomDB getInstance(Context context){
      if(instance==null){
          instance = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,"saved_memes_table")
                  .fallbackToDestructiveMigration()
                  .addCallback(roomCallbacks)
                  .build();
      }
      return instance;
    }
    private static final RoomDatabase.Callback roomCallbacks = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsync(instance).execute();
        }
    };
    private static class PopulateDBAsync extends AsyncTask<Void,Void,Void> {
        private SavedDao noteDao;
        private PopulateDBAsync(RoomDB db){
            noteDao = db.savedDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
