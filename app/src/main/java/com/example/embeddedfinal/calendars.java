package com.example.embeddedfinal;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.icu.text.DateFormat;
        import android.icu.text.SimpleDateFormat;
        import android.icu.util.Calendar;
        import android.content.res.AssetManager;
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
        import com.prolificinteractive.materialcalendarview.CalendarDay;
        import com.prolificinteractive.materialcalendarview.CalendarMode;
        import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
        import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class calendars extends AppCompatActivity {
    TextView tv;
   // private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.textView2)
    TextView textView2;

    @BindView(R.id.back)
    Button back;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendars);

        tv=findViewById(R.id.tv);
        mQueue = Volley.newRequestQueue(this);
        textView2 = findViewById(R.id.textView2);
        ButterKnife.bind(this);

        //Setup initial text
        textView.setText("No Selection");

        MaterialCalendarView materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
              //  .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setMinimumDate(CalendarDay.from(2020, 5, 1))

                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                //   Toast.makeText(Calender.this, "" + date, Toast.LENGTH_SHORT).show();

                final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
                //  Toast.makeText(getApplicationContext(), "" + FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
                final String date1 = FORMATTER.format(date.getDate()) ;
                textView.setText(selected ? FORMATTER.format(date.getDate()) : "No Selection");


                String url = "http://192.168.27.1:3000/db/day";
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                    JSONObject calendardatescore =(JSONObject) response.getJSONObject(date1);
                                    int score = calendardatescore.getInt("score");
                                    int percent = calendardatescore.getInt("percent");
                                    int ptilt = calendardatescore.getInt("ptilt");
                                    int prb = calendardatescore.getInt("prb");
                                    int pcr = calendardatescore.getInt("pcr");

                                    String use = calendardatescore.getString("use");
                                    textView2.setText( " 점수 "+score +"점"+"\n 바른자세 유지 : "+ percent+"%\n" +
                                                       " 기운 자세 : "+ ptilt + "%\n 어깨구부린 자세 : "+ prb + "%\n 다리 꼰 자세 : "+ pcr+"%\n 사용시간 : "+use);



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
        }) ;

        Button back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }









}

