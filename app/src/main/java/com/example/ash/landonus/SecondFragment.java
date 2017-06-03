package com.example.ash.landonus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    BarChart mBarChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_second, container, false);

        mBarChart = (BarChart) view.findViewById(R.id.lineGraph);

        ArrayList<Integer> data1 = new ArrayList<>();
        data1.add(343456);
        data1.add(2345676);
        data1.add(6754344);
        data1.add(89456554);
        data1.add(45544556);


        ArrayList<Integer> serialNumber = new ArrayList<>();

        serialNumber.add(1);
        serialNumber.add(2);
        serialNumber.add(3);
        serialNumber.add(4);
        serialNumber.add(5);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<data1.size();i++){
            entries.add(new BarEntry(data1.get(i),serialNumber.get(i)));
        }

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        mBarChart.animateY(5000);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarData data = new BarData(labels, dataset);
        mBarChart.setData(data);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(true);
        return view;
    }
}
