package com.example.ash.landonus.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ash.landonus.Databases.OwnerData;
import com.example.ash.landonus.FlatListActivity;
import com.example.ash.landonus.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.File;

import io.realm.RealmResults;

/**
 * Created by ash on 5/22/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    RealmResults<OwnerData> ownerDatas;

    public ListAdapter(Context context, RealmResults<OwnerData> ownerDatas) {
        this.context = context;
        //this.ownerDatas = ownerDatas;
        update(ownerDatas);
    }

    public void update(RealmResults<OwnerData> ownerDatas) {
        this.ownerDatas = ownerDatas;
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Title.setText(ownerDatas.get(position).getTitle());
        holder.AddressView.setText(ownerDatas.get(position).getAddress());

        File file = new File(ownerDatas.get(position).getFileName());
        Uri uri = Uri.fromFile(file);

        Glide.with(context).
                load(uri).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FlatListActivity.class);
                intent.putExtra("id", ownerDatas.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, ownerDatas.get(position).getPhoneNumber(), Toast.LENGTH_LONG).show();


                showDialog(position);


            }
        });

        holder.callView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {



                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    v.setBackgroundColor(Color.parseColor("#e6e6e6"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    v.setBackgroundColor(Color.parseColor("#ffffff"));
                }


                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return ownerDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title, AddressView;
        ImageView imageView;
        LinearLayout linearLayout;
        ImageView callView;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.Rtitle);
            AddressView = (TextView) itemView.findViewById(R.id.Raddress);
            imageView = (ImageView) itemView.findViewById(R.id.RimageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.cardLayout);
            callView = (ImageView) itemView.findViewById(R.id.callViewL);


        }
    }

    public void showDialog(final int position){
        new LovelyStandardDialog(context)
                .setTopColorRes(R.color.Green)
                .setButtonsColorRes(R.color.TextColorD)
                .setIcon(R.drawable.ic_info_white_24dp)
                .setTitle(R.string.title)
                .setMessage(R.string.message)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "positive clicked", Toast.LENGTH_SHORT).show();
                        call(position);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void call(int position){
        String uri = "tel:" + ownerDatas.get(position).getPhoneNumber().trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);


    }
}
