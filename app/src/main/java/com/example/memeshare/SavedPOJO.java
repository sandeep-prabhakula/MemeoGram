package com.example.memeshare;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_memes")
public class SavedPOJO {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "meme_Link")
    private String link;

    public SavedPOJO( String link) {
        this.link = link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }
}
