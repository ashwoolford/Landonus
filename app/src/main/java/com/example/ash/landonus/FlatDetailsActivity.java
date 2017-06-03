package com.example.ash.landonus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ash.landonus.Databases.FlatRentList;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.id;

public class FlatDetailsActivity extends AppCompatActivity {

    //private TextView textView;
    private Toolbar toolbar;
    //private RecyclerView recyclerView;
    private Realm realm;
    private String title;
    private EditText editText;
    private Button button;
    private String text;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_details);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        title = intent.getStringExtra("name");

        editText = (EditText) findViewById(R.id.rentEditText1);
        button = (Button) findViewById(R.id.saveButton);

       // recyclerView = (RecyclerView) findViewById(R.id.recylerViewFD);
        toolbar = (Toolbar) findViewById(R.id.toolbarFD);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      //textView = (TextView) findViewById(R.id.textViewFD);
        getData();

        id = intent.getIntExtra("id", -1);
        //textView.setText(""+id);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!editText.getText().toString().isEmpty()){
                   saveData(editText.getText().toString());
                   Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();}

               else
                   Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void saveData(final String editText){
//        realm.beginTransaction();
//        FlatRentList flatRentList = realm.createObject(FlatRentList.class);
//        flatRentList.setFlatTitle(title);
//        flatRentList.setRent(editText);
//        //realm.copyToRealmOrUpdate(flatRentList);
//        realm.commitTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FlatRentList flatRentList = realm.createObject(FlatRentList.class, editText);
                flatRentList.setFlatTitle(title);
                flatRentList.setId(id);
                realm.copyToRealmOrUpdate(flatRentList);



            }
        });

        //getData();

    }

    public void getData(){

        RealmResults<FlatRentList> result = realm.where(FlatRentList.class).equalTo("flatTitle", title).findAll();

            for(int i = 0 ; i < result.size() ; i ++){
                Log.d("result" , ""+result.get(i).getRent());
                editText.append(""+result.get(i).getRent());
            }
//        if(result.size()>0)
//          editText.append(""+result.get(result.size()-1).getRent());
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_activity_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
