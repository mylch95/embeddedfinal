package com.example.embeddedfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.things.contrib.driver.pwmservo.Servo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class mainscreen extends AppCompatActivity {
    @BindView(R.id.mainbtn)
    Button mainbtn;
    @BindView(R.id.calendarbtn)
    Button calendarbtn;
    @BindView(R.id.stackbtn)
    Button stackbtn;
    @BindView(R.id.finish)
    Button finish;

    @BindView(R.id.timer)
    TextView timer;
    private RequestQueue mQueue;


    private Servo mServo;
    private Servo mServo2;

    Thread mThread;

    static int counter = 0;
    Runnable mRunnable0 = new Runnable() {
        //정상
        public void run() {
            try {

                mServo.setAngle(60);

                mServo2.setAngle(60);

            } catch (IOException e) {
            }

        }
    };

    Runnable mRunnable1 = new Runnable() {
        //왼쪽
        public void run() {
            try {

                mServo.setAngle(-90);
                mServo2.setAngle(90);
            } catch (IOException e) {
            }

        }
    };

    Runnable mRunnable2 = new Runnable() {
        //왼대각
        public void run() {
            try {

                mServo.setAngle(-90);
                mServo2.setAngle(90);
            } catch (IOException e) {
            }

        }
    };

    Runnable mRunnable3 = new Runnable() {
        //앞
        public void run() {
            try {


                mServo.setAngle(-90);
                mServo2.setAngle(-90);
            } catch (IOException e) {
            }

        }
    };

    Runnable mRunnable4 = new Runnable() {
        //오대각
        public void run() {
            try {

                mServo.setAngle(90);
                mServo2.setAngle(-90);
            } catch (IOException e) {
            }

        }
    };

    Runnable mRunnable5 = new Runnable() {
        //오른
        public void run() {
            try {

                mServo.setAngle(90);
                mServo2.setAngle(-90);
            } catch (IOException e) {
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        ImageView flower = (ImageView) findViewById(R.id.gif_image);

        Glide.with(this).load(R.drawable.flower_main).into(flower);


        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(this);
        try {

            mServo = new Servo("PWM1");

            mServo.setPulseDurationRange(0.75,2.6);

            mServo.setAngleRange(-90, 90);

            mServo.setEnabled(true);

        } catch (IOException e) {}

        try {

            mServo2 = new Servo("PWM0");

            mServo2.setPulseDurationRange(0.75,2.6);

            mServo2.setAngleRange(-90, 90);

            mServo2.setEnabled(true);

        } catch (IOException e) {}

        TimerTask tt = new TimerTask(){
            @Override
            public void run(){
               final ImageView flower2 = (ImageView) findViewById(R.id.gif_image2);


                String url = "http://192.168.27.1:3000/db/sec";
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int score = response.getInt("score");
                                    int mode = response.getInt("mode");
                                    String message = "";

                                    switch(mode){
                                        case 0:
                                            mThread = new Thread(mRunnable0);
                                            message = "바른자세입니다. \n 아주 좋아요!";
                                            mThread.start();

                                            flower2.setImageResource(R.drawable.good);
                                            break;
                                        case 1:
                                            mThread = new Thread(mRunnable1);
                                            message = "몸이 왼쪽으로 기울어졌어요.";
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();

                                            break;
                                        case 2:
                                            mThread = new Thread(mRunnable2);
                                            message = "몸이 왼쪽 앞으로 \n기울었어요.";
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();

                                            break;
                                        case 3:
                                            mThread = new Thread(mRunnable3);
                                            message = "몸이 앞으로 기울었어요.";
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();

                                            break;
                                        case 4:
                                            mThread = new Thread(mRunnable4);
                                            message = "몸이 오른쪽 앞으로 \n기울었어요.";
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();

                                            break;
                                        case 5:
                                            mThread = new Thread(mRunnable5);
                                            message = "몸이 오른쪽으로 기울었어요.";
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();

                                            break;
                                        case 6:

                                            message = "왼쪽 다리를 꼬았어요.";
                                            flower2.setImageResource(R.drawable.bad);

                                            break;
                                        case 7:

                                            message = "오른쪽 다리를 꼬았어요";
                                            flower2.setImageResource(R.drawable.bad);

                                            break;
                                        case 8://앞

                                            message = "어깨가 굽으셨어요. \n 등을 펴주세요.";
                                            flower2.setImageResource(R.drawable.bad);

                                            break;
                                        case 88://앞

                                            message = "어깨가 심하게 굽으셨어요. \n 등을 펴주세요.";
                                            flower2.setImageResource(R.drawable.bad);

                                            break;
                                        case 9://앞
                                            message = "몸을 너무 뒤로 젖히셨어요.";
                                            flower2.setImageResource(R.drawable.bad);

                                            break;

                                        default:
                                            //정상
                                            mThread = new Thread(mRunnable0);
                                            flower2.setImageResource(R.drawable.bad);
                                            mThread.start();


                                            break;
                                    }
                                    if(score == 33){
                                        timer.setText("자세 측정을 위해 \n 자리에 앉아주세요.");
                                        flower2.setImageResource(R.drawable.good);}
                                    else
                                       timer.setText( "점수 "+score +"점\n "+ message );

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
        };

        Timer timer = new Timer();
        timer.schedule(tt,0,2000);






        Button calendarbtn=(Button) findViewById(R.id.calendarbtn);
        calendarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),calendars.class);
                startActivity(intent);
            }
        });

        Button stackbtn=(Button) findViewById(R.id.stackbtn);
        stackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),stack.class);
                startActivity(intent);
            }
        });

        Button finish=(Button) findViewById(R.id.finish);
       finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.27.1:3000/db/end";
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                   int go = response.getInt("OK");

                                   finish();
                                   Thread.sleep(1);



                                } catch (JSONException | InterruptedException e) {
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
