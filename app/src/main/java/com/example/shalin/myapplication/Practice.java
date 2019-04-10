package com.example.shalin.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Practice extends AppCompatActivity implements View.OnClickListener{

    RequestQueue rq;
    int ansvalue;
    int index, id, question_id ;
    int attempt_que = 0;
    int increment_progress=0;
    private static final long COUNTDOWN_TOTAL_TIME = 2400000;
    ImageButton ibPrevious;
    ImageButton ibNext;
    RadioGroup queRg;
    RadioButton op1;
    RadioButton op2;
    RadioButton op3;
    RadioButton op4;
    TextView tvAttempt,question1;
    TextView tvTotal;
    ProgressBar prgBar;
    TextView tvTimer;
    TextView anscard;

    String question,option1,option2,option3,option4,answer,selectedoption,description,default_description;

    private long timeLeft = COUNTDOWN_TOTAL_TIME;   // in millis
    private boolean timerrunning;   // status of timer whether it is running or not
    private CountDownTimer countDownTimer;
    //  Context context;

    private static final String QUEANS_URL="http://192.168.1.106/quizee/api/getPracticeQuestionsAPI.php";
    private static final String SELECTEDOPTION_URL="http://192.168.1.106/quizee/userselected.php";
    private static final String ANSWER_CHECK="http://192.168.1.106/quizee/check_answer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);

        index = 0;
        id = 0;
        question_id = 1;
        default_description = "Click here for description";
        ibPrevious = findViewById(R.id.ibPrevious);
        ibNext = findViewById(R.id.ibNext);
        queRg = findViewById(R.id.queRg);
        question1 = findViewById(R.id.qTv);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);
        tvAttempt = findViewById(R.id.tvAttempt);
        tvTotal = findViewById(R.id.tvTotal);
        prgBar = findViewById(R.id.prgBar);
        tvTimer = findViewById(R.id.tvTimer);
        anscard = findViewById(R.id.ansCard);

        rq = Volley.newRequestQueue(this);
        sendjsonrequest();

        ibPrevious.setOnClickListener(this);
        ibNext.setOnClickListener(this);

        queRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.op1:
                        selectedoption = op1.getText().toString();
                        Toast.makeText(Practice.this,selectedoption,Toast.LENGTH_SHORT).show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.106/quizee/check_answer.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject responseObject = jsonObject.getJSONObject("Response");

                                    String msg = responseObject.getString("msg");
                                    String exists = responseObject.getString("exists");

                                    switch (exists) {
                                        case "1":
                                            Log.d("existed", "yes");
                                            //    Toast.makeText(Test_page.this, "True", Toast.LENGTH_LONG).show();
                                            ansvalue = 1;
                                            answertrue();
                                            break;
                                        case "0":
                                            Log.d("existed", "no");
                                            //    Toast.makeText(Test_page.this, "False", Toast.LENGTH_LONG).show();
                                            ansvalue = 0;
                                            answertrue();
                                            // signUpApi();
                                            break;
                                        case "2":
                                            //    Toast.makeText(Test_page.this, "Error", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Practice.this, "ERROR in sending", Toast.LENGTH_LONG).show();
                                Toast.makeText(Practice.this,Integer.toString(index), Toast.LENGTH_LONG).show();

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String>map = new HashMap<>();
                                map.put("selecteddata",op1.getText().toString());
                                map.put("id", Integer.toString(id));
                                map.put("index",Integer.toString(index));
                                return map;
                            }
                        };

                        //    Volley.newRequestQueue(Test_page.this).add(stringRequest);
                        RequestQueue queue= Volley.newRequestQueue(Practice.this);
                        queue.add(stringRequest);
                     /*   StringRequest stringRequest1 = new StringRequest(Request.Method.POST, ANSWER_CHECK, new Response.Listener<String>() {

                            //Toast.makeText(Test_page.this,"phase0",Toast.LENGTH_LONG).show();
                            @Override
                            public void onResponse(String response) {

                            //    Toast.makeText(Test_page.this,"phase0",Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject responseObject = jsonObject.getJSONObject("Response");

                                  //  JSONArray jsonArray = responseObject.getJSONArray("register_login");

                                 //   JSONObject jsonObject=new JSONObject(response);
                                 //   JSONArray qa=jsonObject.getJSONArray("response");

                                    Log.d("Response1",response);

                                    Toast.makeText(Test_page.this,"entered",Toast.LENGTH_LONG).show();
                                    String msg = responseObject.getString("msg");
                                    String temp = responseObject.getString("exists");
                                    String temp1 = responseObject.getString("exit");

                                    if(temp1.equals("phase 0")){
                                        Toast.makeText(Test_page.this,"phase0",Toast.LENGTH_LONG).show();
                                    }
                                    else{

                                        Toast.makeText(Test_page.this,"phase0 failed.",Toast.LENGTH_LONG).show();
                                    }

                                    if (temp.equals("1")) {

                                      Toast.makeText(Test_page.this,"Equal",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Test_page.this, "NOt Equal", Toast.LENGTH_LONG).show();

                                    }

                                    //Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                    //startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Test_page.this, "ERROR in Checking", Toast.LENGTH_LONG).show();
                                Toast.makeText(Test_page.this,Integer.toString(index), Toast.LENGTH_LONG).show();

                            }
                        });





                        Volley.newRequestQueue(Test_page.this).add(stringRequest1);

*/
                        break;
                    case R.id.op2:
                        selectedoption = op2.getText().toString();
                        Toast.makeText(Practice.this,selectedoption,Toast.LENGTH_SHORT).show();
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST,ANSWER_CHECK, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject responseObject = jsonObject.getJSONObject("Response");

                                    String msg = responseObject.getString("msg");
                                    String exists = responseObject.getString("exists");

                                    switch (exists) {
                                        case "1":
                                            Log.d("existed", "yes");
                                            //   Toast.makeText(Test_page.this, "True", Toast.LENGTH_LONG).show();
                                            ansvalue = 1;
                                            answertrue();
                                            break;
                                        case "0":
                                            Log.d("existed", "no");
                                            //   Toast.makeText(Test_page.this, "False", Toast.LENGTH_LONG).show();
                                            ansvalue = 0;
                                            answertrue();
                                            // signUpApi();
                                            break;
                                        case "2":
                                            //   Toast.makeText(Test_page.this, "Error", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Practice.this, "ERROR in sending", Toast.LENGTH_LONG).show();
                                Toast.makeText(Practice.this,Integer.toString(index), Toast.LENGTH_LONG).show();

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("selecteddata",selectedoption);
                                params.put("id", Integer.toString(id));
                                params.put("index",Integer.toString(index));
                                return params;
                            }
                        };
                        //  Volley.newRequestQueue(Test_page.this).add(stringRequest2);
                        RequestQueue queue1= Volley.newRequestQueue(Practice.this);
                        queue1.add(stringRequest2);
                        break;
                    case R.id.op3:
                        selectedoption = op3.getText().toString();
                        Toast.makeText(Practice.this,selectedoption,Toast.LENGTH_SHORT).show();
                        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, ANSWER_CHECK, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject responseObject = jsonObject.getJSONObject("Response");

                                    String msg = responseObject.getString("msg");
                                    String exists = responseObject.getString("exists");

                                    switch (exists) {
                                        case "1":
                                            Log.d("existed", "yes");
                                            //    Toast.makeText(Test_page.this, "True", Toast.LENGTH_LONG).show();
                                            ansvalue = 1;
                                            answertrue();
                                            break;
                                        case "0":
                                            Log.d("existed", "no");
                                            //    Toast.makeText(Test_page.this, "False", Toast.LENGTH_LONG).show();
                                            ansvalue = 0;
                                            answertrue();
                                            // signUpApi();
                                            break;
                                        case "2":
                                            //    Toast.makeText(Test_page.this, "Error", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Practice.this, "ERROR in sending", Toast.LENGTH_LONG).show();
                                Toast.makeText(Practice.this,Integer.toString(index), Toast.LENGTH_LONG).show();

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("selecteddata",selectedoption);
                                params.put("id", Integer.toString(id));
                                params.put("index",Integer.toString(index));
                                return params;
                            }
                        };
                        // Volley.newRequestQueue(Test_page.this).add(stringRequest3);
                        RequestQueue queue3= Volley.newRequestQueue(Practice.this);
                        queue3.add(stringRequest3);
                        break;
                    case R.id.op4:
                        selectedoption = op4.getText().toString();
                        Toast.makeText(Practice.this,selectedoption,Toast.LENGTH_SHORT).show();
                        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, ANSWER_CHECK, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject responseObject = jsonObject.getJSONObject("Response");

                                    String msg = responseObject.getString("msg");
                                    String exists = responseObject.getString("exists");

                                    switch (exists) {
                                        case "1":
                                            Log.d("existed", "yes");
                                            //    Toast.makeText(Test_page.this, "True", Toast.LENGTH_LONG).show();
                                            ansvalue = 1;
                                            answertrue();
                                            break;
                                        case "0":
                                            Log.d("existed", "no");
                                            //  Toast.makeText(Test_page.this, "False", Toast.LENGTH_LONG).show();
                                            ansvalue = 0;
                                            answertrue();
                                            // signUpApi();
                                            break;
                                        case "2":
                                            //  Toast.makeText(Test_page.this, "Error", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Practice.this, "ERROR in sending", Toast.LENGTH_LONG).show();
                                Toast.makeText(Practice.this,Integer.toString(index), Toast.LENGTH_LONG).show();

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("selecteddata",selectedoption);
                                params.put("id", Integer.toString(id));
                                params.put("index",Integer.toString(index));
                                return params;
                            }
                        };
                        // Volley.newRequestQueue(Test_page.this).add(stringRequest4);
                        RequestQueue queue4= Volley.newRequestQueue(Practice.this);
                        queue4.add(stringRequest4);
                        break;
                }
            }
        });
    }

    private void Description() {

        anscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anscard.setText(description);

            }
        });

    }


    public void answertrue(){

        StringRequest stringRequest5 = new StringRequest(Request.Method.POST, SELECTEDOPTION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(Test_page.this,Integer.toString(index),Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Practice.this, "ERROR in sending for user", Toast.LENGTH_LONG).show();
                Toast.makeText(Practice.this,Integer.toString(index), Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put("selecteddata",selectedoption);
                map.put("id", Integer.toString(id));
                map.put("ansvalue",Integer.toString(ansvalue));
                map.put("index",Integer.toString(question_id));

                return map;
            }
        };

        RequestQueue queue5= Volley.newRequestQueue(Practice.this);
        queue5.add(stringRequest5);

    }

    @Override
    public void onClick(View v) {


        switch(v.getId()){
            case R.id.ibPrevious:

                if(index != 0) {

                    queRg.clearCheck();
                    anscard.setText(default_description);
                    index--;
                    question_id--;

                    StringRequest sr = new StringRequest(Request.Method.POST, QUEANS_URL, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {


                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray qa = jsonObject.getJSONArray("response");

                                Log.d("Response", response);

                                JSONObject uniObject = qa.getJSONObject(index);

                                id = uniObject.getInt("id");
                                question = uniObject.getString("question");
                                option1 = uniObject.getString("option1");
                                option2 = uniObject.getString("option2");
                                option3 = uniObject.getString("option3");
                                option4 = uniObject.getString("option4");
                                answer = uniObject.getString("answer");
                                description = uniObject.getString("description");

                                question1.setText(question);
                                op1.setText(option1);
                                op2.setText(option2);
                                op3.setText(option3);
                                op4.setText(option4);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(Practice.this, "ERROR in Fetching", Toast.LENGTH_LONG).show();

                                }
                            });
                    Volley.newRequestQueue(this).add(sr);
                    Description();

                }

                else{
                    index = 0;
                    anscard.setText(default_description);
                }
                break;
            case R.id.ibNext:
                if(index != 3) {
                    queRg.clearCheck();
                    anscard.setText(default_description);
                    attempt_que++;
                    index++;
                    question_id++;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, QUEANS_URL, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {


                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray qa = jsonObject.getJSONArray("response");

                                Log.d("Response", response);

                                JSONObject uniObject = qa.getJSONObject(index);

                                id = uniObject.getInt("id");
                                question = uniObject.getString("question");
                                option1 = uniObject.getString("option1");
                                option2 = uniObject.getString("option2");
                                option3 = uniObject.getString("option3");
                                option4 = uniObject.getString("option4");
                                answer = uniObject.getString("answer");
                                description = uniObject.getString("description");

                                question1.setText(question);
                                op1.setText(option1);
                                op2.setText(option2);
                                op3.setText(option3);
                                op4.setText(option4);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(Practice.this, "ERROR", Toast.LENGTH_LONG).show();

                                }
                            });
                    Volley.newRequestQueue(this).add(stringRequest);
                    Description();

                }

                else{

                    Intent i = new Intent(Practice.this,Result.class);
                    startActivity(i);

                }

                if(attempt_que < 50){
                    String Attempt = Integer.toString(attempt_que);
                    tvAttempt.setText(Attempt);
                    increment_progress += 2;
                    prgBar.setProgress(increment_progress);
                }
                else if (attempt_que == 50){
                    Toast.makeText(Practice.this,"You have completed the test",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(MainActivity.this,"Next",Toast.LENGTH_SHORT).show();

                break;
            default:
                Toast.makeText(Practice.this,"Error",Toast.LENGTH_LONG).show();
                break;

        }
    }

    public void sendjsonrequest(){

        queRg.clearCheck();
        anscard.setText(default_description);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, QUEANS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray qa=jsonObject.getJSONArray("response");

                    Log.d("Response",response);

                    JSONObject uniObject=qa.getJSONObject(index);

                    id=uniObject.getInt("id");
                    question=uniObject.getString("question");
                    option1 = uniObject.getString("option1");
                    option2 = uniObject.getString("option2");
                    option3 = uniObject.getString("option3");
                    option4 = uniObject.getString("option4");
                    answer = uniObject.getString("answer");
                    description = uniObject.getString("description");

                    question1.setText(question);
                    op1.setText(option1);
                    op2.setText(option2);
                    op3.setText(option3);
                    op4.setText(option4);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Practice.this,"ERROR",Toast.LENGTH_LONG).show();

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
        Description();


    }
}

