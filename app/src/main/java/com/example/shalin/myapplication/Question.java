package com.example.shalin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shalin on 12-03-2019.
 */

public class Question extends AppCompatActivity {

    RadioButton op1;
    RadioButton op2;
    RadioButton op3;
    RadioButton op4;
    TextView question1;

    String id;

    String question,option1,option2,option3,option4,answer;
    final String QUEANS_URL="http://192.168.1.106/quizee/api/getResultedQuestion.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);


        question1 = findViewById(R.id.qTv);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);

        id = getIntent().getExtras().get("question_index").toString();
        Toast.makeText(Question.this,id,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, QUEANS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {


                    Toast.makeText(Question.this, "Inn", Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray qa = jsonObject.getJSONArray("response");

                    Log.d("Response1", response);

                    JSONObject uniObject = qa.getJSONObject(Integer.parseInt(id));

               //     id = uniObject.getInt("id");
                    question = uniObject.getString("question");
                    option1 = uniObject.getString("option1");
                    option2 = uniObject.getString("option2");
                    option3 = uniObject.getString("option3");
                    option4 = uniObject.getString("option4");
                    answer = uniObject.getString("answer");

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

                        Toast.makeText(Question.this, "ERROR", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("question_index",id);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
