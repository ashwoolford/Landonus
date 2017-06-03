package com.example.ash.landonus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ash.landonus.R;
import com.example.ash.landonus.models.MBDummyData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by ash on 5/26/2017.
 */

public class MakingBillsAdapter  extends RecyclerView.Adapter<MakingBillsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<MBDummyData> dummyDataList;

    public MakingBillsAdapter(Context context, ArrayList<MBDummyData> dummyDataList) {
        this.context = context;
        this.dummyDataList = dummyDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.making_bills_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Title.setText(dummyDataList.get(position).getFlatName());
        holder.due.setText(dummyDataList.get(position).getDue()+"");
        holder.paid.setText(dummyDataList.get(position).getPaid()+"");

    }

    @Override
    public int getItemCount() {
        return dummyDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title;
        TextView paid, due;


        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.billFlatTitle);
            paid = (TextView) itemView.findViewById(R.id.paid);
            due = (TextView) itemView.findViewById(R.id.payable);

        }
    }

}
