package com.example.ash.landonus;

import android.app.*;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.load.engine.Resource;
import com.example.ash.landonus.Adapters.CollectBillAdapter;
import com.example.ash.landonus.Databases.FlatRentList;
import com.example.ash.landonus.Databases.TotalDueManager;
import com.example.ash.landonus.models.CollectBillData;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CollectBillsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CollectBillData> list;
    private LinearLayoutManager linearLayoutManager;
    private CollectBillAdapter collectBillAdapter;
    private String titles[] = {"FA-01","FA-02","FB-01","FC-03","FA-03","FC-01"};
    private Realm realm;
    private RealmResults<FlatRentList> rentLists;
    private int id , month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_bills);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        Intent intent  = getIntent();
        id  = intent.getIntExtra("id", -1);


        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        //RealmResults<FlatRentList> flatList = realm.where(FlatRentList.class).equalTo("id", id).findAll();
        rentLists = realm.where(FlatRentList.class).equalTo("id",id).findAll();

        toolbar = (Toolbar) findViewById(R.id.CBtoolbar);
        toolbar.setTitle("Collect Rents");
        recyclerView = (RecyclerView) findViewById(R.id.CBRecyclerView);
        list = new ArrayList<>();

        populateArrayList();
//        for(int i = 0 ; i < titles.length ;i ++)
//           list.add(new CollectBillData(titles[i]));


        collectBillAdapter = new CollectBillAdapter(list, getApplicationContext() ,id , month+""+year);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(collectBillAdapter);

        //updateData();

    }

    public void populateArrayList(){

        for(int i = 0 ; i < rentLists.size() ; i++){
            CollectBillData data = new CollectBillData(rentLists.get(i).getFlatTitle());
            list.add(data);
        }

    }

    public void updateData(){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                TotalDueManager totalDueManager = new TotalDueManager();
                totalDueManager.setDue(133432);
                totalDueManager.setId(id);
                totalDueManager.setFlatTitle("duduru");
                totalDueManager.setMonthYear("3567");
                totalDueManager.setId_1(8);

                realm.copyToRealmOrUpdate(totalDueManager);

            }
        });




    }
}
