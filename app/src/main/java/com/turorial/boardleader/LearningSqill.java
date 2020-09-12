package com.turorial.boardleader;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningSqill extends Fragment {
    private RecyclerView recyclerHome ;
    private static String JSON_URI = "https://gadsapi.herokuapp.com/api/hours";
    private List<Student> articles =new ArrayList<>();

    public TextView article;
    public  TextView price;
    private LearnersAdapter lAdapter;

    public LearningSqill() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_learning_sqill, container, false);

        recyclerHome = (RecyclerView)rootView.findViewById(R.id.recycleHome);

        extractHoursInfo();
        return  rootView;
    }

    private void extractHoursInfo() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject studentObject = response.getJSONObject(i);
                        Student student = new Student(studentObject.getString("name"),studentObject.getString("hours"),studentObject.getString("country"),studentObject.getString("badgeUrl"));
                       articles.add(student);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerHome.setLayoutManager(new LinearLayoutManager(getContext()));
                lAdapter = new LearnersAdapter(articles,getContext());
                recyclerHome.setAdapter(lAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse:"+error);
            }
        });

        queue.add(jsonArrayRequest);
    }

}
