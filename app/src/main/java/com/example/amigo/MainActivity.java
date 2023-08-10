package com.example.amigo;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private DialogInterface.OnClickListener Deleteallclcicked;

    private DialogInterface.OnClickListener Dontclicked;

    public static int positionoflist;


    Uri Final_Uri;

    private ListView display;

    private ImageView newbutton;

    private DataAdapter dataAdapter;

    private Cursor cursor;

    private View emptyview;

    ProgressBar progressBar;

    Intent i;

    // OnTouchListener that listens for any user touches on a View, implying that they are modifying
    // the view, and we change the mPetHasChanged boolean to true.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("My Friends");

        progressBar=(ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.VISIBLE);


        Final_Uri=Uri.withAppendedPath(Contract.BASE_URI,Contract.TABLE_NAME+Contract.SEMESTER_NUMBER);


        display=(ListView) findViewById(R.id.displayview);

        emptyview=(View) findViewById(R.id.emptyview);
        display.setEmptyView(emptyview);

        dataAdapter=new DataAdapter(this,cursor);
        display.setAdapter(dataAdapter);

        getSupportLoaderManager().initLoader(1,null,this);

        newbutton=(ImageView) findViewById(R.id.new_button);


        i=new Intent(MainActivity.this,usereditorActivity.class);


        newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setData(null);
                startActivity(i);
            }
        });

        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionoflist=(int)position;
                Uri uritpbeupdated= ContentUris.withAppendedId(Final_Uri,id);
                i.setData(uritpbeupdated);
                startActivity(i);
            }
        });

        Deleteallclcicked=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteallbuttonclicked();
                dialog.dismiss();
                return;
            }
        };

        Dontclicked=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };


    }




    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,Final_Uri,Contract.projectons,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        progressBar.setVisibility(View.GONE);
        dataAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        dataAdapter.changeCursor(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.deleteall,menu);
         return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( dataAdapter.getCount()!=0){
            showdialogbox();
        }
        return true;
    }

     private void deleteallbuttonclicked(){

         Contract.deleteall=true;
         getContentResolver().delete(Uri.withAppendedPath(Contract.BASE_URI,Contract.TABLE_NAME+Contract.SEMESTER_NUMBER),null,null);
     }

     private void showdialogbox(){

         AlertDialog.Builder builder=new AlertDialog.Builder(this);
         builder.setMessage("Do you want to delete all the names?");
         builder.setPositiveButton("Delete All",Deleteallclcicked);
         builder.setNegativeButton("Don't",Dontclicked);

         AlertDialog alertDialog=builder.create();
         alertDialog.show();
     }


}
