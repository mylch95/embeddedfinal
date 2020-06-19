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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class realtime extends AppCompatActivity {

    @BindView(R.id.back)
    Button back;

    private RequestQueue mQueue;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        lineChart = (LineChart) findViewById(R.id.bargraph);
        mQueue = Volley.newRequestQueue(this);

        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String url = "http://192.168.27.1:3000/db/min";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int len = response.length();
                            ArrayList<Entry> barEntries = new ArrayList<>();
                            ArrayList<String> theDates = new ArrayList<>();
                            for(int i = 0; i < len; i++) {
                                String j = "db"+String.valueOf(i);
                                String xaxis = (i+1)+"분";
                                JSONObject db = response.getJSONObject(j);
                                int score = db.getInt("score");
                                barEntries.add(new Entry( (float) score,i));
                                theDates.add(xaxis);
                            }
                            LineDataSet DataSet = new LineDataSet(barEntries, "Dates");
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
