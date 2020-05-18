package com.gilimedia.githubuser2.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM userlist ORDER BY id")
    List<Usergithub> loadAllUsers();

    @Insert
    void insertPerson(Usergithub usergithub);

    @Update
    void updatePerson(Usergithub usergithub);

    @Delete
    void delete(Usergithub usergithub);

    @Query("SELECT * FROM userlist WHERE username = :username")
    Usergithub loadPersonById(String username);
}
