package com.example.mindfulapp_;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {mindfulData.class},version =1, exportSchema = false)
abstract class mindfulDatabase extends RoomDatabase {




    public abstract mindfulDataDao mindDao();


}

@Database(entities = tasktable.class,version =3, exportSchema = false)
abstract class taskDatabase extends RoomDatabase {




    public abstract taskDao taskDao();


}




