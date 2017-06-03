package com.example.ash.landonus;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ash.landonus.Databases.OwnerData;
import com.example.ash.landonus.Loopers.PhotoSizeReduceTask;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AddPoperty extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final int THUMBNAIL_SIZE = 800;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private ImageView imageView;
    private EditText titleEditText, addressET, nameET, phoneET, investmentEt;
    private PhotoSizeReduceTask photoSizeReduceTask;
    private String pathDirectory, filename;
    private Realm realm;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poperty);

        Realm.init(this);
        realm = Realm.getDefaultInstance();


        //init edittexts

        titleEditText = (EditText) findViewById(R.id.title1);
        addressET = (EditText) findViewById(R.id.address);
        nameET = (EditText) findViewById(R.id.name);
        phoneET = (EditText) findViewById(R.id.phone_number);
        investmentEt = (EditText) findViewById(R.id.investment);


        mToolbar = (Toolbar) findViewById(R.id.addActivity_toolbar);
        fab = (FloatingActionButton) findViewById(R.id._fab);
        imageView = (ImageView) findViewById(R.id.header);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get absolutepath
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Landonus", Context.MODE_PRIVATE);

        pathDirectory = directory.getAbsolutePath();


        //Log.d("getpath", directory.getAbsolutePath());

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Add a property");

        photoPickFromGallery();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater layoutInflater = getMenuInflater();
        layoutInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.done:
                if(filename !=null && titleEditText.getText().toString() != null && addressET.getText().toString() !=null && nameET.getText().toString() !=null
                        && phoneET.getText().toString() !=null && investmentEt.getText().toString() !=null ){
                    saveOnDatabase(titleEditText.getText().toString(), addressET.getText().toString(),
                            nameET.getText().toString(), phoneET.getText().toString() ,
                            investmentEt.getText().toString(), pathDirectory+"/"+filename);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void photoPickFromGallery(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            try {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                filename = getFileName(uri);
                //Log.d("filename", filename);
                photoSizeReduceTask = new PhotoSizeReduceTask(AddPoperty.this, imageView , bitmap, filename);
                photoSizeReduceTask.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    //get file name from uri


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void saveOnDatabase(String titleEditText, String addressET,
                               String nameET, String phoneET, String investmentEt, String file_path ){

        realm.beginTransaction();
        OwnerData data = realm.createObject(OwnerData.class);

        int key;
        try {
            key = realm.where(OwnerData.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            key = 0;
        }

        data.setId(key);
        data.setName(nameET);
        data.setAddress(addressET);
        data.setPhoneNumber(phoneET);
        data.setTitle(titleEditText);
        data.setFileName(file_path);
        data.setTotalInvestment(investmentEt);

        realm.commitTransaction();
        Log.d("save", "title "+titleEditText +" add "+addressET+" name "+nameET+" phn "+phoneET+" invest "+investmentEt+" file "+file_path);

    }

}
