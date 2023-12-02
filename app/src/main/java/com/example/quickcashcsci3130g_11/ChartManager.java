package com.example.quickcashcsci3130g_11;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartManager {
    public void displayLineChart(Context context, Map<String, Float> monthlyIncomes) {
        LineChart chart = ((AppCompatActivity) context).findViewById(R.id.lineChart);

        List<Entry> entries = new ArrayList<>();
        int index = 0;

        // Iterate through the monthly income map
        for (Map.Entry<String, Float> entry : monthlyIncomes.entrySet()) {
            entries.add(new Entry(index++, entry.getValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Monthly Income");
        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.invalidate();
    }

    public void displayBarGraph(Context context, Map<String, Integer> jobCounts) {
        BarChart chart = ((AppCompatActivity) context).findViewById(R.id.barChart);

        List<BarEntry> entries = new ArrayList<>();
        int index = 0;

        // Iterate through the job counts map
        for (Map.Entry<String, Integer> entry : jobCounts.entrySet()) {
            entries.add(new BarEntry(index++, entry.getValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Number of Jobs");
        BarData barData = new BarData(dataSet);

        chart.setData(barData);
        chart.invalidate();
    }
}
