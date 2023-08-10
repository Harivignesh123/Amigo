package com.example.amigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayadapterforSemesters extends ArrayAdapter<String> {
    TextView textView1;
    View wordview;
    public ArrayadapterforSemesters(@NonNull Context context, ArrayList<String> list) {
        super(context,0,list);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        wordview=convertView;

        if(wordview==null){
            wordview=LayoutInflater.from(getContext()).inflate(R.layout.semesterlist,parent,false);
        }

        textView1=(TextView) wordview.findViewById(R.id.textview1);
        textView1.setText(String.valueOf(getItem(position)));

        return wordview;
    }
}
