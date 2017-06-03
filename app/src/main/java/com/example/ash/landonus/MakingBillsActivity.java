package com.example.ash.landonus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.ash.landonus.Adapters.MakingBillsAdapter;
import com.example.ash.landonus.Databases.FlatRentList;
import com.example.ash.landonus.Databases.TotalDueManager;
import com.example.ash.landonus.Databases.TotalRentManager;
import com.example.ash.landonus.models.MBDummyData;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.value;

public class MakingBillsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MakingBillsAdapter adapter;
    private ArrayList<MBDummyData> list;
    private String titles[] = {"FA-01", "FA-02" ,"FA-03","FA-04","FA-05","FA-06"};
    private int paid[] = {123445,4567,6787789,789878,689880,76889};
    private double due[] = {0.00,23556.67, 3455.35,0.00 , 0.00 , 0.00 };
    private Realm realm;
    private int id , month , year;
    RealmResults<FlatRentList> rentLists;
    RealmResults<TotalDueManager> dueManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_bills);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        //getCurrent month and year
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        //
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        //
        rentLists = realm.where(FlatRentList.class).equalTo("id",id).findAll();
        dueManagers = realm.where(TotalDueManager.class).equalTo("id" , id).findAll();
        //dueManagers = realm.where(TotalDueManager.class).equalTo("id" , id).findAll();


        toolbar = (Toolbar) findViewById(R.id.makingbills_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.MBrecyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        populateRecyclerView();
        makeDummyData();

    }

    public void populateRecyclerView(){

        int sum = 0;

        for(int i = 0 ; i <rentLists.size() ; i++){
            Number sum1 = dueManagers.where().equalTo("flatTitle", rentLists.get(i).getFlatTitle()).sum("due");
            //dueManagers.where().equalTo("flatTitle", rentLists.get(i).getFlatTitle()).findAll().

           Log.d("valueCounter", rentLists.get(i).getFlatTitle()+" "+sum1);//
//            for(int j = 0 ; j < value; j++){
//                dueManagers.where().equalTo("due" , dueManagers.get(i).getDue()).findAll();
//
//
//            }

            list.add(new MBDummyData(rentLists.get(i).getFlatTitle(),rentLists.get(i).getRent(), sum1));


        }

        adapter = new MakingBillsAdapter(getApplicationContext(), list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public void makeDummyData(){
//        RealmResults<TotalRentManager> ress = realm.where(TotalRentManager.class).findAll();
//        for(int i = 0 ; i < ress.size() ; i++){
//            Log.d("dev" , ress.get(i).getFlatTitle()+" "+ress.get(i).getRent()+" "+ress.get(i).getMonthYear());
//        }

       // RealmResults<TotalDueManager> ress = realm.where(TotalDueManager.class).findAll();
        for(int i = 0 ; i < dueManagers.size() ; i++){
            Log.d("dev" , dueManagers.get(i).getFlatTitle()+" "+dueManagers.get(i).getDue()+" "+dueManagers.get(i).getMonthYear());

        }


    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
