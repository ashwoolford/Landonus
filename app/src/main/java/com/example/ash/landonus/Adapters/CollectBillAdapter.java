package com.example.ash.landonus.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.ash.landonus.CollectBillsActivity;
import com.example.ash.landonus.Databases.TotalDueManager;
import com.example.ash.landonus.R;
import com.example.ash.landonus.models.CollectBillData;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ash on 6/2/2017.
 */

public class CollectBillAdapter extends RecyclerView.Adapter<CollectBillAdapter.ViewHolder>{

    ArrayList<CollectBillData> list;
    Context context;
    private String[] mDataset;
    int id;
    String monthYear;

    public CollectBillAdapter(ArrayList<CollectBillData> list, Context context , int id , String monthYear) {
        this.list = list;
        this.context = context;
        mDataset = new String[list.size()];
        this.id = id;
        this.monthYear = monthYear;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.collect_bill_layout, parent , false);
        return new ViewHolder(view , new MyCustomEditText());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textView.setText(list.get(position).getTitle());
        holder.customEditText.updatePosition(holder.getAdapterPosition());
        holder.editText.setText(mDataset[holder.getAdapterPosition()]);
        holder.loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loadingButton.startLoading();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        holder.loadingButton.loadingSuccessful();
                        Toast.makeText(context, mDataset[position] , Toast.LENGTH_SHORT).show();
                        updateData(position , list.size() , Integer.valueOf(mDataset[position]) , list.get(position).getTitle() , monthYear);

                    }
                },500);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateData(final int position, final int Size , final int due, final String title, final String monthYear ){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<TotalDueManager> total = realm.where(TotalDueManager.class).equalTo("id", id).findAll();

                TotalDueManager totalDueManager = new TotalDueManager();
                totalDueManager.setDue(due);
                totalDueManager.setId(id);
                totalDueManager.setFlatTitle(title);
                totalDueManager.setMonthYear(monthYear);
                totalDueManager.setId_1(((total.size()-1)-(Size-1))+position);

                realm.copyToRealmOrUpdate(totalDueManager);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        EditText editText;
        LoadingButton loadingButton;
        MyCustomEditText customEditText;


        public ViewHolder(View itemView , MyCustomEditText customEditText) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.flatTitle);
            editText = (EditText) itemView.findViewById(R.id.editTextDue);
            loadingButton = (LoadingButton) itemView.findViewById(R.id.loading_button);

            this.customEditText = customEditText;
            this.editText.addTextChangedListener(customEditText);
        }
    }


    public class MyCustomEditText implements TextWatcher {

        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mDataset[position] = s.toString();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
