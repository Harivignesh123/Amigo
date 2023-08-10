package com.example.amigo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter extends CursorAdapter {
    public DataAdapter(Context context, Cursor cursor)
    {

        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.mainlist,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView v1=(TextView) view.findViewById(R.id.view1);
        v1.setText(cursor.getString(cursor.getColumnIndex(Contract.Name)));

        ImageView image=(ImageView) view.findViewById(R.id.imageview);
        byte[] bytearray=cursor.getBlob(cursor.getColumnIndex(Contract.IMAGE));

        if(bytearray!=null){
            Bitmap b= BitmapFactory.decodeByteArray(bytearray,0,bytearray.length);
            image.setImageBitmap(b);
        }
        else {
            image.setImageResource(R.drawable.emptyimage);
        }

        return;
    }
}