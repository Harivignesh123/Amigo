package com.example.amigo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class usereditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static ByteArrayOutputStream stream;

    Thread loadimage;

    boolean isloadimageThreadrunning=true;

    byte[] byteArray=null;

    private Bitmap bitmapimage;

    private Intent gallery;

    private boolean mPetHasChanged = false;

    private Uri uritobeupdated;

    private Intent i;

    private EditText editname;

    private ImageView image;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;

            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if((requestCode==Contract.PICK_IMAGE) && (resultCode==RESULT_OK)){
                    Log.v("usereditorActivity","bitmapimage=(Bitmap) data.getExtras().get(\"data\"); going to execute XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

                    Uri imageuri=data.getData();
                    try {
                        bitmapimage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.v("usereditorActivity","bitmapimage=(Bitmap) data.getExtras().get(\"data\"); executed XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

                    isloadimageThreadrunning=true;
                    loadimage.start();
                    isloadimageThreadrunning=true;

                }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usereditor);

        loadimage=new Thread(new Runnable() {

            Handler handler=new Handler();
            @Override
            public void run() {

                if(!isloadimageThreadrunning){
                    return;
                }

                stream = new ByteArrayOutputStream();
                bitmapimage.compress(Bitmap.CompressFormat.JPEG, 20, stream);

                byteArray = stream.toByteArray();


                bitmapimage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                mPetHasChanged=true;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmapimage);
                    }
                });


            }
        });



        image=(ImageView)findViewById(R.id.imageusereditor);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                gallery.setType("image/*");
                startActivityForResult(gallery,Contract.PICK_IMAGE);
            }
        });


        editname=(EditText)findViewById(R.id.editname);
        editname.setOnTouchListener(mTouchListener);

        i=getIntent();
        uritobeupdated=i.getData();

        if (uritobeupdated==null){
            setTitle("Add a Friend");
        }
        else {
            setTitle("Edit my Friend");
            getSupportLoaderManager().initLoader(2,null,this);
        }



    }
    private DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                    // Create a click listener to handle the user confirming that changes should be discarded.

                    // Show dialog that there are unsaved changes
                    // User clicked "Discard" button, close the current activity.
                    finish();
                }
    };
    private DialogInterface.OnClickListener keepeditingButtonClickListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // User clicked the "Keep editing" button, so dismiss the dialog
            // and continue editing the pet.
            if(dialog!=null){
                dialog.dismiss();
            }
        }
    };

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonlistener){


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard",discardButtonlistener);
        builder.setNegativeButton("Keep Editing",keepeditingButtonClickListener);

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mPetHasChanged) {
            super.onBackPressed();
            return;
        }

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        image.setImageBitmap(bitmapimage);
        return new CursorLoader(this,uritobeupdated,Contract.projectons,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        String name="";
        if (data.moveToPosition(MainActivity.positionoflist)){
            name=data.getString(data.getColumnIndex(Contract.Name));
            byteArray=data.getBlob(data.getColumnIndex(Contract.IMAGE));
            if(byteArray!=null){
                Bitmap b= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                image.setImageBitmap(b);
            }
            else {
                image.setImageResource(R.drawable.emptyimage);
            }
        }
        editname.setText(name);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader)
    {
        editname.setText("");
        image.setImageResource(R.drawable.emptyimage);
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(uritobeupdated==null){
            getMenuInflater().inflate(R.menu.createnewmenu,menu);
        }
        else {
            getMenuInflater().inflate(R.menu.editormenu,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.savebutton:
                savebutton();
                return true;

            case R.id.deletebutton:
                deletebutton();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void savebutton(){

        String name=editname.getText().toString().trim();

        if(name.matches("")||name==null){
            Toast.makeText(this,"Please fill the name field",Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values=new ContentValues();
        values.put(Contract.Name,name);
        values.put(Contract.IMAGE,byteArray);

        if (uritobeupdated==null){
            getContentResolver().insert(Uri.withAppendedPath(Contract.BASE_URI,Contract.TABLE_NAME+Contract.SEMESTER_NUMBER),values);
        }
        else {
            getContentResolver().update(uritobeupdated,values,null,null);
        }
        isloadimageThreadrunning=false;
        finish();

    }

    private void deletebutton(){

        if (uritobeupdated!=null) {
            Contract.deleteall = false;
            getContentResolver().delete(uritobeupdated, null, null);
            isloadimageThreadrunning=false;
            finish();
        }

    }



}