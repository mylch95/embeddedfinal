package com.example.embeddedfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.button3)
    Button button3;


    private RequestQueue mQueue;
    private RequestQueue mQueue2;
    private int go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(this);
        mQueue2 = Volley.newRequestQueue(this);
        Button button=(Button) findViewById(R.id.button);
        Button button3=(Button) findViewById(R.id.button3);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.27.1:3000/init";
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                     go = response.getInt("OK");


                                    if( go == 1) {
                                        Intent intent = new Intent(getApplicationContext(), mainscreen.class);
                                        startActivity(intent);
                                    }
                                    else{
                                    //    Toast.makeText(MainActivity.this, "You're not connected with chair!" , Toast.LENGTH_SHORT).show();
                                    }

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


/*

                if( go == 1) {
                    Intent intent = new Intent(getApplicationContext(), mainscreen.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "You're not connected with chair!" , Toast.LENGTH_SHORT).show();
                }
*/

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.27.1:3000/init/new";
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    go = response.getInt("OK");

                                    if( go == 1) {
                                        Intent intent = new Intent(getApplicationContext(), mainscreen.class);
                                        startActivity(intent);
                                    }
                                    else{
                                      //  Toast.makeText(MainActivity.this, "You're not connected with chair!" , Toast.LENGTH_SHORT).show();
                                    }

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
        });

    }




}
