package com.example.gaskeun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gaskeun.db";
    public static final int DATABASE_VERSION = 1;

    // Table Users
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";

    // Table Joki
    public static final String TABLE_JOKI = "joki";
    public static final String COL_JOKI_ID = "id";
    public static final String COL_JOKI_USERNAME = "username";
    public static final String COL_JOKI_PASSWORD = "password";
    public static final String COL_JOKI_EMAIL = "email";
    public static final String COL_JOKI_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT);";

        String CREATE_JOKI = "CREATE TABLE " + TABLE_JOKI + " (" +
                COL_JOKI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_JOKI_USERNAME + " TEXT UNIQUE, " +
                COL_JOKI_PASSWORD + " TEXT, " +
                COL_JOKI_EMAIL + " TEXT, " +
                COL_JOKI_PHONE + " TEXT);";

        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_JOKI);

        ContentValues joki1 = new ContentValues();
        joki1.put(COL_JOKI_USERNAME, "Joki1");
        joki1.put(COL_JOKI_PASSWORD, "Joki123");
        joki1.put(COL_JOKI_EMAIL, "joki1@gmail.com");
        joki1.put(COL_JOKI_PHONE, "0812345678");

        db.insert(TABLE_JOKI, null, joki1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if upgrading
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOKI);
        onCreate(db);
    }

    public boolean registerUser(String username, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkJokiCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_JOKI + " WHERE " +
                COL_JOKI_USERNAME + "=? AND " + COL_JOKI_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

}

