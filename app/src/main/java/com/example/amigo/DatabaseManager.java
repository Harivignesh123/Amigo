package com.example.amigo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="friendsmemory.db";

    String SQL_STATEMENT_FOR_CREATING_A_TABLE;
    String SQL_STATEMENT_FOR_CREATING_A_TABLE2;

    private static final int DATABASE_VERSION=1;
    @Override
    public void onCreate(SQLiteDatabase db) {

        for(int i=1;i<=Integer.valueOf(SemesterselectionActivity.no_of_sem);i++){
            if (i==1){
                SQL_STATEMENT_FOR_CREATING_A_TABLE="CREATE TABLE "+Contract.TABLE_NAME+"("
                        +Contract._ID_FOR_TABLE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +Contract.Name+" TEXT NOT NULL,"
                        +Contract.IMAGE+" BLOB);";
            }
            else {
                SQL_STATEMENT_FOR_CREATING_A_TABLE="CREATE TABLE "+Contract.TABLE_NAME+String.valueOf(i)+"("
                        +Contract._ID_FOR_TABLE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +Contract.Name+" TEXT NOT NULL,"
                        +Contract.IMAGE+" BLOB);";
            }
            db.execSQL(SQL_STATEMENT_FOR_CREATING_A_TABLE);
        }



    }

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
