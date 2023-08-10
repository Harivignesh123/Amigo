package com.example.amigo;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract implements BaseColumns {

    public static String SEMESTER_NUMBER;

    public static final String TABLE_NAME="tablename";

    public static String _ID_FOR_TABLE=BaseColumns._ID;

    public static final String Name="Name";

    public static final String IMAGE ="Profile_Pic";

    public static final String[] projectons={_ID_FOR_TABLE,Name,IMAGE};

    public static final String AUTHORITY="com.example.amigo";

    public static final Uri BASE_URI=Uri.parse("content://"+AUTHORITY);

    public static final Uri FINAL_URI= Uri.withAppendedPath(BASE_URI,TABLE_NAME);

    public static Boolean deleteall=true;

    public static final int PICK_IMAGE=100;
}
