package com.gilimedia.githubuser2.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "userlist")
public class Usergithub {
    @PrimaryKey(autoGenerate = true)
    int id;
    String username;
    String url;
    String avatar;

    @Ignore
    public Usergithub(String username, String url, String avatar) {
        this.username = username;
        this.url = url;
        this.avatar = avatar;
    }

    public Usergithub(int id, String username, String url, String avatar) {
        this.id = id;
        this.username = username;
        this.url = url;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
