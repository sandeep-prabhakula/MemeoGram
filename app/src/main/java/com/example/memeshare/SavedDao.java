package com.example.memeshare;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedDao {
    @Insert
    void insert(SavedPOJO savedPOJO);

    @Delete
    void delete(SavedPOJO savedPOJO);

    @Query("SELECT *FROM saved_memes")
    LiveData<List<SavedPOJO>>getAll();

    @Query("DELETE FROM saved_memes")
    void deleteAll();
}
