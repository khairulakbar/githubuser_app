package com.gilimedia.consumergithub.room;

import android.net.Uri;

import androidx.room.Entity;

@Entity(tableName = Usergithub.TABLE_NAME)
public class Usergithub {

    public static final String AUTHORITY = "com.gilimedia.githubuser2";

    public static final Uri URI_USER = Uri.parse(
            "content://" + AUTHORITY + "/" + Usergithub.TABLE_NAME);

    public static final String TABLE_NAME = "userlist";

    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_URL = "url";
    public static final String AVATAR = "avatar";


}
