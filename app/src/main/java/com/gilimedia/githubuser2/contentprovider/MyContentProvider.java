/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gilimedia.githubuser2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gilimedia.githubuser2.room.AppDatabase;
import com.gilimedia.githubuser2.room.UserDao;
import com.gilimedia.githubuser2.room.Usergithub;


/**
 * A {@link ContentProvider} based on a Room database.
 *
 * <p>Note that you don't need to implement a ContentProvider unless you want to expose the data
 * outside your process or your application already uses a ContentProvider.</p>
 */
public class MyContentProvider extends ContentProvider {

    private static final int CODE_USER_DIR = 1;

    private static final int CODE_USER_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(Usergithub.AUTHORITY, Usergithub.TABLE_NAME, CODE_USER_DIR);
        MATCHER.addURI(Usergithub.AUTHORITY, Usergithub.TABLE_NAME + "/*", CODE_USER_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
            @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_USER_DIR || code == CODE_USER_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            UserDao userDao = AppDatabase.getInstance(context).userDao();
            final Cursor cursor;
            if (code == CODE_USER_DIR) {
                cursor = userDao.selectAll();
            } else {
                cursor = userDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_USER_DIR:
                return "vnd.android.cursor.dir/" + Usergithub.AUTHORITY + "." + Usergithub.TABLE_NAME;
            case CODE_USER_ITEM:
                return "vnd.android.cursor.item/" + Usergithub.AUTHORITY + "." + Usergithub.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_USER_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = AppDatabase.getInstance(context).userDao()
                        .insert(Usergithub.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_USER_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_USER_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_USER_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = AppDatabase.getInstance(context).userDao()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_USER_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_USER_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Usergithub cheese = Usergithub.fromContentValues(values);
                cheese.id = ContentUris.parseId(uri);
                final int count = AppDatabase.getInstance(context).userDao()
                        .update(cheese);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }



}
