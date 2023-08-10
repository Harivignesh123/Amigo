package com.example.amigo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SemesterselectionActivity extends AppCompatActivity {

    ListView listView;

    Intent i;

    ArrayadapterforSemesters arrayadapter;

    public static String no_of_sem;

    private ArrayList<String> semesterlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesterselection);

        setTitle("My Semesters");

        no_of_sem=""+OneTimeuserPreferenceActivity.sharedPreferences.getString(String.valueOf(R.string.numberkey),"");

        int n;
        n=(int)Integer.valueOf(no_of_sem);
        semesterlist=new ArrayList<String>();

        for(int i=1;i<=n;i++){
            semesterlist.add("Semester "+i);
        }
        listView=(ListView) findViewById(R.id.listview);
        arrayadapter=new ArrayadapterforSemesters(this,semesterlist);


        listView.setAdapter(arrayadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Contract.SEMESTER_NUMBER="";
                }
                else {
                    Contract.SEMESTER_NUMBER=String.valueOf(position+1);
                }
                i=new Intent(SemesterselectionActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }



    private DialogInterface.OnClickListener exitdialogueListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            OneTimeuserPreferenceActivity.fa.finish();
            finish();
        }
    };

    private DialogInterface.OnClickListener continuedialogueListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(dialog!=null){
                dialog.dismiss();
            }
        }
    };

    public void displayExitdialogueinterface(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes",exitdialogueListener);
        builder.setNegativeButton("No",continuedialogueListener);

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        displayExitdialogueinterface();
    }

}