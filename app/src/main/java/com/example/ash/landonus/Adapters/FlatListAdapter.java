package com.example.ash.landonus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ash.landonus.Databases.FlatManagement;
import com.example.ash.landonus.FlatDetailsActivity;
import com.example.ash.landonus.R;

import io.realm.RealmResults;

/**
 * Created by ash on 5/23/2017.
 */

public class FlatListAdapter extends RecyclerView.Adapter<FlatListAdapter.ViewHolder>{
    private RealmResults<FlatManagement> flatManagements;
    private Context context;

    public FlatListAdapter(RealmResults<FlatManagement> flatManagements, Context context) {
        this.flatManagements = flatManagements;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(flatManagements.get(position).getCoordinatorName());
        holder.title.setText(flatManagements.get(position).getFlatTitle());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FlatDetailsActivity.class);
                intent.putExtra("id", flatManagements.get(position).getId());
                intent.putExtra("name", flatManagements.get(position).getFlatTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return flatManagements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, name ;
        ImageView imageView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.list_layoutTextView1);
            name = (TextView) itemView.findViewById(R.id.list_layoutTextView2);
            imageView = (ImageView) itemView.findViewById(R.id.list_layoutImageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linerLayoutFL);
        }
    }
}
