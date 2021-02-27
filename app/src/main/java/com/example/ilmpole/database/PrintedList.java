package com.example.ilmpole.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "PrintedList", indices = {@Index(value = "Id", unique = true)})
public class PrintedList {

    @PrimaryKey(autoGenerate = true)
    private int Sno;
    private String Id;


    public int getSno() {
        return Sno;
    }

    public void setSno(int sno) {
        Sno = sno;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
