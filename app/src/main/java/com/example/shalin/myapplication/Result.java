package com.example.shalin.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shalin on 11-03-2019.
 */

public class Result extends AppCompatActivity {

    int id,datavalue;

    ArrayList<Pojo> idname = new ArrayList<>();
    String QUESTION_INDEX="http://192.168.1.106/quizee/result.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);



        StringRequest request=new StringRequest(Request.Method.GET,
                QUESTION_INDEX,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  JSONObject jsonObject = null;
                        //   String JsonString = EntityU
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject responseobject = jsonArray.getJSONObject(i);
                                id = responseobject.getInt("question_index");
                                datavalue = responseobject.getInt("datavalue");

                                //    Toast.makeText(Regular.this,Integer.toString(id),Toast.LENGTH_LONG).show();

                                Pojo pojo = new Pojo(Integer.toString(id),Integer.toString(datavalue));
                                idname.add(pojo);

                                //   Toast.makeText(Regular.this,String.valueOf(typename),Toast.LENGTH_LONG).show();
                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(Result.this,LinearLayoutManager.HORIZONTAL,false);
                            RecyclerView recyclerView = findViewById(R.id.recycler);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                            ResultAdapter scorecard = new ResultAdapter(Result.this, idname);
                            recyclerView.setAdapter(scorecard);

                           // LinearLayoutManager layoutManager = new LinearLayoutManager(Result.this,LinearLayoutManager.VERTICAL,false);
                           // RecyclerView recyclerView = findViewById(R.id.recyclerview);
                           // recyclerView.setLayoutManager(layoutManager);
                           // RegularParentAdapter adapter = new RegularParentAdapter(Regular.this,typename,idname);
                           // recyclerView.setAdapter(adapter);
                            //   jsonObject = new JSONObject(response);
                            //   JSONObject responseObject = jsonObject.getJSONObject("Response");

                            //   JSONArray jsonArray = responseObject.getJSONArray("course");

                            //   String msg = responseObject.getString("msg");
                            // type = responseObject.getString("course_name");

                            //   typename.add(type);

                            //    Toast.makeText(Regular.this, "inn", Toast.LENGTH_LONG);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(Result.this);
        queue.add(request);


    }
}
