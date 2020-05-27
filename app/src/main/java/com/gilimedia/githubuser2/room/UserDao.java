package com.gilimedia.githubuser2.room;


import android.database.Cursor;
import android.provider.BaseColumns;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDao {


    @Query("SELECT COUNT(*) FROM " + Usergithub.TABLE_NAME)
    int count();

    @Insert
    long insert(Usergithub usergithub);


    @Query("SELECT * FROM " + Usergithub.TABLE_NAME + " ORDER BY " + BaseColumns._ID)
    Cursor selectAll();


    @Query("SELECT * FROM " + Usergithub.TABLE_NAME + " WHERE " + Usergithub.COLUMN_ID + " = :id")
    Cursor selectById(long id);


    @Query("DELETE FROM " + Usergithub.TABLE_NAME + " WHERE " + Usergithub.COLUMN_ID + " = :id")
    int deleteById(long id);


    @Update
    int update(Usergithub usergithub);

}
