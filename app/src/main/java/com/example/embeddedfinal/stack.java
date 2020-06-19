package com.example.embeddedfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class stack extends AppCompatActivity {
    @BindView(R.id.back)
    Button back;
        private TextView mTextViewResult;
    private RequestQueue mQueue;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);

        lineChart = (LineChart) findViewById(R.id.bargraph);
        mQueue = Volley.newRequestQueue(this);
        Button realtime = findViewById(R.id.realtime);
        Button back = findViewById(R.id.back);

        realtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),realtime.class);

                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String url = "http://192.168.27.1:3000/db/week";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONObject day1 =(JSONObject) response.getJSONObject("db0");
                            int day1_score = day1.getInt("score");
                            JSONObject day2 =(JSONObject) response.getJSONObject("db1");
                            int day2_score = day2.getInt("score");
                            JSONObject day3 =(JSONObject) response.getJSONObject("db2");
                            int day3_score = day3.getInt("score");
                            JSONObject day4 =(JSONObject) response.getJSONObject("db3");
                            int day4_score = day4.getInt("score");
                            JSONObject day5 =(JSONObject) response.getJSONObject("db4");
                            int day5_score = day5.getInt("score");
                            JSONObject day6 =(JSONObject) response.getJSONObject("db5");
                            int day6_score = day6.getInt("score");
                            JSONObject day7 =(JSONObject) response.getJSONObject("db6");
                            int day7_score = day7.getInt("score");


                            ArrayList<Entry> barEntries = new ArrayList<>();
                            barEntries.add(new Entry( (float) day1_score,0));
                            barEntries.add(new Entry( (float)day2_score,1));
                            barEntries.add(new Entry((float)day3_score,2));
                            barEntries.add(new Entry((float)day4_score,3));
                            barEntries.add(new Entry((float)day5_score,4));
                            barEntries.add(new Entry((float)day6_score,5));
                            barEntries.add(new Entry((float)day7_score,6));
                            LineDataSet DataSet = new LineDataSet(barEntries, "Dates");

                            ArrayList<String> theDates = new ArrayList<>();
                            theDates.add("Day1");
                            theDates.add("Day2");
                            theDates.add("Day3");
                            theDates.add("Day4");
                            theDates.add("Day5");
                            theDates.add("Day6");
                            theDates.add("Day7");

                            LineData theData;
                            theData = new LineData(theDates, DataSet);

                            // theData.addDataSet(barDataSet);
                            lineChart.setNoDataText("여기를 클릭하세요!");
                            lineChart.setData(theData);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}