package com.example.ash.landonus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ash.landonus.Adapters.ListAdapter;
import com.example.ash.landonus.Databases.OwnerData;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private Realm realm;
    private RealmResults<OwnerData> ownerDatas;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RealmChangeListener realmChangeListener;
    ListAdapter listAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        //init the realm instance variables

        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {

                Log.d("change", "changed");

                listAdapter.update(ownerDatas);

            }
        };

        realm.addChangeListener(realmChangeListener);


        //imageView = (ImageView) view.findViewById(R.id.fragmentImageView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ownerDatas = realm.where(OwnerData.class).findAllAsync();
        ownerDatas = ownerDatas.sort("id", Sort.DESCENDING);
        listAdapter = new ListAdapter(getActivity(), ownerDatas);
        recyclerView.setAdapter(listAdapter);

        getData();


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onstop", "onstop");
        //ownerDatas.removeChangeListener(realmChangeListener);
        //realm.removeChangeListener(realmChangeListener);
    }

    public void getData(){
        RealmResults<OwnerData> ownerDatas = realm.where(OwnerData.class).findAll();

        for(int i= 0 ;i <ownerDatas.size() ;i++){
            Log.d("database", ""+ownerDatas.get(i).getId());
            System.out.println("");
        }

    }




}
