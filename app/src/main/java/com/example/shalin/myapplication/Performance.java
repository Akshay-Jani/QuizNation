package com.example.shalin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shalin on 13-03-2019.
 */

public class Performance extends AppCompatActivity {


    int correct,incorrect;
    float gained_percentage;
    String PERFORMANCE="http://192.168.1.106/quizee/api/performance.php";
    String pass="Pass";
    String fail = "Fail";
    TextView correctcount,incorrectcount,datana,final_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        correctcount = findViewById(R.id.correctcount);
        incorrectcount = findViewById(R.id.incorrectcount);
        datana = findViewById(R.id.detana);
        final_result = findViewById(R.id.finalResult);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, PERFORMANCE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray qa=jsonObject.getJSONArray("response");

                    for(int i=0;i<qa.length();i++) {

                        Log.d("Response", response);

                        JSONObject uniObject = qa.getJSONObject(i);

                        correct = uniObject.getInt("correct");

                        incorrect = 4 - correct;

                        correctcount.setText(Integer.toString(correct));
                        incorrectcount.setText(Integer.toString(incorrect));

                        gained_percentage = (correct*100)/4;
                        if(gained_percentage > 40){

                            final_result.setText(pass);
                        }
                        else{
                            final_result.setText(fail);
                        }
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Performance.this,"ERROR in performance.",Toast.LENGTH_LONG).show();

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);



        detana();
    }

    private void detana() {

        datana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Performance.this,Result.class);
                startActivity(intent);

            }
        });
    }
}
