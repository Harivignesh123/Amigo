package com.example.amigo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OneTimeuserPreferenceActivity extends AppCompatActivity {


    Spinner spinner;

    public static Activity fa;

    private boolean conditiona;
    private boolean conditionb;

    private String n;

    public static SharedPreferences sharedPreferences;

    Button save;

    String prevStarted="";

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_timeuser_preference);

        spinner=(Spinner)findViewById(R.id.spinnerview);

        setupSpinner();

        fa=this;



        sharedPreferences=getSharedPreferences(String.valueOf(R.string.userpreference),MODE_PRIVATE);

        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putBoolean(prevStarted,Boolean.FALSE);
                editor.apply();
                
                conditiona=(!n.matches(""));
                if(conditiona){

                    conditionb=(Integer.valueOf(n)>0) && (Integer.valueOf(n)<13);
                    if (conditionb){

                        editor.putString(String.valueOf(R.string.numberkey),n);
                        editor.apply();

                        movetoSecondary();

                    }
                    else{
                        Toast.makeText(OneTimeuserPreferenceActivity.this,"NO.of semesters must be >0 and <13",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(OneTimeuserPreferenceActivity.this,"Enter a number",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        fa=this;
        if(!sharedPreferences.getBoolean(prevStarted,true)){
            movetoSecondary();
        }
    }
    private void movetoSecondary(){
        i=new Intent(this,SemesterselectionActivity.class);
        startActivity(i);
    }


    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.Array_Semesterno_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    n=selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}