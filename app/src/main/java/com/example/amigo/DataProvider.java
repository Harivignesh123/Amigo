package com.example.amigo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataProvider extends ContentProvider {


    private DatabaseManager database;

    private static final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);

    private static final int PETSTABLE=100;

    private static final int PETS_ID=101;

    private String whichsem;


    /*static {
        matcher.addURI(Contract.AUTHORITY, Contract.TABLE_NAME, PETSTABLE);
        matcher.addURI(Contract.AUTHORITY,  Contract.TABLE_NAME+"/*", PETS_ID);
    }*/

    @Override
    public boolean onCreate() {
        database=new DatabaseManager(this.getContext());
        return false;
    }

    Cursor cursor;

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match=matcher.match(uri);
        SQLiteDatabase db=database.getReadableDatabase();



        cursor= db.query(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER, projection, selection, selectionArgs,null,null,sortOrder);

        /*switch (match){
            case PETSTABLE:
                Log.v("DataProvider","db.query is going to execute XXXXXXXXXXXXXXXXX");
                cursor= db.query(Contract.TABLE_NAME+SemesterselectionActivity.whichsemester, projection, selection, selectionArgs,null,null,sortOrder);
                Log.v("DataProvider","db.query finished executing XXXXXXXXXXXXXXXXX");
                return cursor;
            case PETS_ID:
                selection=Contract._ID_FOR_TABLE?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                cursor= db.query(Contract.TABLE_NAME+SemesterselectionActivity.whichsemester, projection, selection, selectionArgs,null,null,sortOrder);
                return cursor;
            default:
                Toast.makeText(this.getContext(),"Problem with Uri for Querying",Toast.LENGTH_SHORT);
        }*/

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match=matcher.match(uri);
        SQLiteDatabase db=database.getWritableDatabase();
        db.insert(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER,null,values);
        /*switch (match){
            case PETSTABLE:
                Log.v("DataProvider","db.insert() going to execute XXXXXXXXXXXXXXXXX");
                long id= db.insert(Contract.TABLE_NAME+SemesterselectionActivity.whichsemester,null,values);
                Log.v("DataProvider","db.query Finished executing XXXXXXXXXXXXXXXXX");
                return uri;
            default:
                Toast.makeText(this.getContext(),"Problem with URI for Inserting",Toast.LENGTH_SHORT);
        }*/
            getContext().getContentResolver().notifyChange(uri,null);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int n=0;

        int match=matcher.match(uri);
        SQLiteDatabase db=database.getWritableDatabase();
        /*switch (match){
            case PETSTABLE:
                 n=db.delete(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER,selection,selectionArgs);
            case PETS_ID:
                selection=Contract._ID_FOR_TABLE+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                n= db.delete(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER, selection, selectionArgs);
            default:
                Toast.makeText(this.getContext(),"Problem with Uri for deleting",Toast.LENGTH_SHORT);
        }*/
        if(Contract.deleteall){
            n=db.delete(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER,selection,selectionArgs);
        }
        else {selection=Contract._ID_FOR_TABLE+"=?";
            selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri)) };
            n= db.delete(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER, selection, selectionArgs);
        }
        if(n!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return n;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int n=0;

        int match=matcher.match(uri);
        SQLiteDatabase db=database.getWritableDatabase();

        selection=Contract._ID_FOR_TABLE+"=?";
        selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri)) };

        n=db.update(Contract.TABLE_NAME+Contract.SEMESTER_NUMBER,values,selection,selectionArgs);

        if(n!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return n;
    }
}
