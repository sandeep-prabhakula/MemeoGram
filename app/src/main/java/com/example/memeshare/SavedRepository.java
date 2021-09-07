package com.example.memeshare;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedRepository {
    private SavedDao noteDao;
    private LiveData<List<SavedPOJO>> allNotes;

    public SavedRepository(Application application){
        RoomDB database = RoomDB.getInstance(application);
        noteDao = database.savedDao();
        allNotes = noteDao.getAll();
    }
    public void insert(SavedPOJO note){
        new InsertAsync(noteDao).execute(note);
    }
    public void delete(SavedPOJO note){
        new DeleteAsync(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllAsync(noteDao).execute();
    }
    public LiveData<List<SavedPOJO>> getAllNotes(){
        return allNotes;
    }

    private static class InsertAsync extends AsyncTask<SavedPOJO,Void,Void> {
        private SavedDao noteDao;
        private InsertAsync(SavedDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(SavedPOJO... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class DeleteAsync extends AsyncTask<SavedPOJO,Void,Void>{
        private SavedDao noteDao;
        private DeleteAsync(SavedDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(SavedPOJO... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllAsync extends AsyncTask<Void,Void,Void>{
        private SavedDao noteDao;
        private DeleteAllAsync(SavedDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}
