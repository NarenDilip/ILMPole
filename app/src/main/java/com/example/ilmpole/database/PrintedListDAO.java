package com.example.ilmpole.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PrintedListDAO {

    @Insert
    public void insert(PrintedList... deviceListDetails);

    @Update
    public void update(PrintedList... deviceListDetails);

    @Delete
    public void delete(PrintedList deviceListDetails);

    @Query("SELECT * FROM PrintedList")
    public List<PrintedList> getDevices();

    @Query("SELECT * FROM PrintedList where id= :type")
    public List<PrintedList> Devices(String type);


    @Query("DELETE FROM PrintedList")
    void DeleteListdevice();

}
