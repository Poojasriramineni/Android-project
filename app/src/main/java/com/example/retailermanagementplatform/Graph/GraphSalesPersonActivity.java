package com.example.retailermanagementplatform.Graph;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retailermanagementplatform.R;
import com.example.retailermanagementplatform.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphSalesPersonActivity extends AppCompatActivity {

    private BarChart barChart;
    private String id;
    private ArrayList<BarEntry> entries;
    private int flag, store;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_salesperson);
        barChart = findViewById(R.id.chart);
        entries = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar13);

        progressBar.setVisibility(View.VISIBLE);

        SessionManager sm = new SessionManager(getApplicationContext());
        HashMap<String, String> details = sm.getUserDetails();
        id = details.get("id");

        // fetching salesperson's name
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Salesperson");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(id)) {

                        //found current salesperson
                        final String salespersonName = snapshot.getValue(SalesPerson.class).getName();
                        flag = 0;

                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("GraphSalesperson");
                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                    GraphObject go = snapshot1.getValue(GraphObject.class);
                                    if (go.getName().equals(salespersonName)) {

                                        // add in the list
                                        if (flag == 0) {
                                            entries.add(new BarEntry(0, Float.parseFloat(go.getProfit())));
                                            flag = 1;
                                            store = Integer.parseInt(go.getDate().substring(0, 2));
                                        } else {
                                            int currentDay = ((Integer.parseInt(go.getDate().substring(0, 2)) + 30) - store) % 30;
                                            entries.add(new BarEntry(currentDay, Float.parseFloat(go.getProfit())));
                                        }
                                    }
                                }

                                progressBar.setVisibility(View.GONE);
                                BarDataSet dataSet = new BarDataSet(entries, "Profit");
                                dataSet.setValueTextSize(15f);
                                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                BarData data = new BarData(dataSet);
                                data.setBarWidth(0.9f); // set custom bar width
                                barChart.animateY(3000, Easing.EasingOption.EaseInBounce);
                                barChart.setData(data);
                                barChart.setFitBars(true); // make the x-axis fit exactly all bars
                                barChart.invalidate(); // refresh
                                BarData barData = barChart.getBarData();
                                barData.setValueTextColor(getResources().getColor(R.color.colorPrimary));
                                XAxis xAxis = barChart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setTextSize(15f);
                                xAxis.setGranularity(1f);
                                xAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
                                xAxis.setDrawAxisLine(true);
                                xAxis.setDrawGridLines(false);

                                YAxis yAxisL = barChart.getAxisLeft();
                                yAxisL.setTextColor(getResources().getColor(R.color.colorPrimary));

                                YAxis yAxisR = barChart.getAxisRight();
                                yAxisR.setTextColor(getResources().getColor(R.color.colorPrimary));

                                Legend legend = barChart.getLegend();
                                legend.setTextColor(getResources().getColor(R.color.colorPrimary));
                                legend.setTextSize(12);
                                legend.setWordWrapEnabled(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
