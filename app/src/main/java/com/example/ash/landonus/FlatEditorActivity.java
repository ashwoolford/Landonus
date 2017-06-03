package com.example.ash.landonus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ash.landonus.Databases.FlatManagement;

import io.realm.Realm;

public class FlatEditorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Realm realm;
    private EditText title, number, name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_editor);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        title = (EditText) findViewById(R.id.FEtitle);
        number = (EditText) findViewById(R.id.FENumber);
        name = (EditText) findViewById(R.id.FEName);

        recyclerView = (RecyclerView) findViewById(R.id.FRecyclerView);
        toolbar = (Toolbar) findViewById(R.id.FEToolbar);
        toolbar.setTitle("Add a Flat");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //init realm instance variables

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();


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
                insert(title.getText().toString(), name.getText().toString(), number.getText().toString(),id);
                finish();
                default:
                    break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void insert(String title, String name, String number , int id){
        realm.beginTransaction();
        FlatManagement flatManagement = realm.createObject(FlatManagement.class);
        flatManagement.setCoordinatorName(name);
        flatManagement.setPhoneNumber(number);
        flatManagement.setId(id);
        flatManagement.setFlatTitle(title);
        realm.commitTransaction();
    }
}
