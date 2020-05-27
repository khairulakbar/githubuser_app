package com.gilimedia.githubuser2.room;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Usergithub.TABLE_NAME)
public class Usergithub {

    public static final String AUTHORITY = "com.gilimedia.githubuser2";

    public static final Uri URI_USER = Uri.parse(
            "content://" + AUTHORITY + "/" + Usergithub.TABLE_NAME);

    public static final String TABLE_NAME = "userlist";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_URL = "url";
    public static final String AVATAR = "avatar";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    String username;
    @ColumnInfo(name = COLUMN_URL)
    String url;
    @ColumnInfo(name = AVATAR)
    String avatar;

    @NonNull
    public static Usergithub fromContentValues(@Nullable ContentValues values) {
        final Usergithub usergithub = new Usergithub();
        if (values != null && values.containsKey(COLUMN_ID)) {
            usergithub.id = values.getAsLong(COLUMN_ID);
        }
        if (values != null && values.containsKey(COLUMN_NAME)) {
            usergithub.username = values.getAsString(COLUMN_NAME);
        }
        if (values != null && values.containsKey(COLUMN_URL)) {
            usergithub.url = values.getAsString(COLUMN_URL);
        }
        if (values != null && values.containsKey(AVATAR)) {
            usergithub.avatar = values.getAsString(AVATAR);
        }
        return usergithub;
    }


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

    public Usergithub() {

    }



    public long getId() {
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
