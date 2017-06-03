package com.example.ash.landonus;

import android.app.*;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ash.landonus.Adapters.FlatListAdapter;
import com.example.ash.landonus.Databases.FlatManagement;
import com.example.ash.landonus.Databases.FlatRentList;
import com.example.ash.landonus.Databases.TotalDueManager;
import com.example.ash.landonus.Databases.TotalRentManager;

import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class FlatListActivity extends AppCompatActivity {

    private static final int SHOW_ID = 100;
    private TextView textView;
    private Realm realm;
    private int id;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FlatListAdapter adapter;
    private RealmResults<FlatManagement> realmResults;
    private LinearLayoutManager linearLayoutManager;
    int day, month, year , day_x , month_x , year_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_list);
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        day_x = c.get(Calendar.DAY_OF_MONTH);
        month_x = c.get(Calendar.MONTH);
        year_x = c.get(Calendar.YEAR);




        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();

        id = intent.getIntExtra("id",-1);
        realmResults = realm.where(FlatManagement.class).equalTo("id",id).findAll();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.FFB);
        recyclerView = (RecyclerView) findViewById(R.id.FRecyclerView);
        toolbar = (Toolbar) findViewById(R.id.Ftoolbar);
        toolbar.setTitle("Flats");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        adapter = new FlatListAdapter(realmResults, getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        updateData();





        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatListActivity.this, FlatEditorActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        //insertData();
        getData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater layoutInflater = getMenuInflater();
        layoutInflater.inflate(R.menu.flat_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.makeBills:
                showDialog(SHOW_ID);
                break;
            case R.id.collectBills:
                Intent intent1 = new Intent(FlatListActivity.this, CollectBillsActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);

                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(SHOW_ID==100){
            return new DatePickerDialog(this, dpickerListerner , year, month , day);
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener dpickerListerner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, final int year3, final int month3, int dayOfMonth3) {

           // Toast.makeText(getApplicationContext(),dayOfMonth3 +" "+(month3+1)+" "+year3, Toast.LENGTH_SHORT).show();

//            day_x = String.valueOf(dayOfMonth);
//            month_x = String.valueOf(month);
//            year_x = String.valueOf(year);
//            day_x = dayOfMonth;
//            month_x = month;
//            year_x = year;

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<FlatRentList> re = realm.where(FlatRentList.class).equalTo("id", id).findAll();
                    //int key;

                    //TotalRentManager totalRentManager;
                    for(int i = 0 ; i < re.size() ; i++){
                        int key;
                        try {
                            key = realm.where(TotalDueManager.class).max("id_1").intValue()+1;
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            key = 0;
                        }

                        TotalRentManager totalRentManager = realm.createObject(TotalRentManager.class );
                        TotalDueManager totalDueManager = realm.createObject(TotalDueManager.class, key);


                        totalRentManager.setMonthYear(month3+""+year3);
                        totalRentManager.setId(id);
                        totalRentManager.setFlatTitle(re.get(i).getFlatTitle());
                        totalRentManager.setRent(0);


                        totalDueManager.setMonthYear(month3+""+year3);
                        totalDueManager.setId(id);
                        totalDueManager.setDue(Integer.valueOf(re.get(i).getRent()));
                        totalDueManager.setFlatTitle(re.get(i).getFlatTitle());




                    }


                }
            });

            Intent intent = new Intent(FlatListActivity.this, MakingBillsActivity.class);
            intent.putExtra("id" , id);
            startActivity(intent);

          //  Toast.makeText(getApplicationContext(),day +" "+month+" "+year, Toast.LENGTH_SHORT).show();


        }
    };

    public void insertData(){

        realm.beginTransaction();
        FlatManagement flatManagement = realm.createObject(FlatManagement.class);

        String flats[] = {"FA-4","FB-2","FC-9","FD-3","FG-3","FF-3"};
        String names[] = {"Asharf","Aj","Rcockry","GZZZ","Fahim","Ash"};
        Random random = new Random();
        int n = random.nextInt(5)+0;

        Log.d("random",""+n);

        flatManagement.setFlatTitle(flats[n]);
        flatManagement.setId(id);
        flatManagement.setCoordinatorName(names[n]);
        flatManagement.setPhoneNumber("01816032138");

        realm.commitTransaction();


    }

    public void getData(){
//        RealmResults<FlatManagement> realmResults = realm.where(FlatManagement.class).equalTo("id",id).findAll();
//        for(int i = 0 ;i < realmResults.size() ; i++)
//            Log.d("ashraf", ""+realmResults.get(i).getFlatTitle()+""+realmResults.get(i).getPhoneNumber()
//                    +""+realmResults.get(i).getCoordinatorName()+""+realmResults.get(i).getId());

        RealmResults<TotalDueManager> re = realm.where(TotalDueManager.class).equalTo("id", id).findAll();
        for(int i = 0 ; i < re.size() ; i++){
            Log.d("ashraf" , " "+re.get(i).getId_1());

        }
    }

    public void updateData(){
        RealmResults<TotalDueManager> total = realm.where(TotalDueManager.class).equalTo("id", id).findAll();

        for(int i = 0 ; i < total.size() ; i++){
            Log.d("devash", total.size()+" "+total.get(i).getId_1()+" "+total.get(i).getFlatTitle()+" "+total.get(i).getDue());
        }
    }
}
