package com.example.ilmpole.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PrintedList.class}, version = 1)
@TypeConverters({DateTypeConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract PrintedListDAO getPrint();

}
