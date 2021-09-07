package com.example.memeshare;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedViewModel extends AndroidViewModel {
    private final SavedRepository repository;
    private final LiveData<List<SavedPOJO>> allNotes;
    public SavedViewModel(@NonNull Application application) {
        super(application);
        repository = new SavedRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void insert (SavedPOJO note){
        repository.insert(note);
    }
    public void  delete(SavedPOJO note){
        repository.delete(note);
    }
    public void deleteAllNote(){
        repository.deleteAllNotes();
    }
    public LiveData<List<SavedPOJO>> getAllNotes() {
        return allNotes;
    }
}
