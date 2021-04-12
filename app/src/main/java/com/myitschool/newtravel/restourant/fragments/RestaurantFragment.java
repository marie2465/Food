package com.myitschool.newtravel.restourant.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myitschool.newtravel.R;

import java.util.ArrayList;

import Models.Restourant;
import Models.Restourants;
import Util.APIService;
import Util.Constants;
import flipviewpager.adapters.RestourantsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {

    private RecyclerView restRecyclerView;
    private RestourantsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_restaurant, container, false);
        restRecyclerView=(RecyclerView)view.findViewById(R.id.rest_recviews);
        layoutManager = new LinearLayoutManager(getActivity());
        restRecyclerView.setLayoutManager(layoutManager);
        restRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.apilink)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Restourants> call = service.getRestourants();
        call.enqueue(new Callback<Restourants>() {
            @Override
            public void onResponse(Call<Restourants> call, Response<Restourants> response) {
                ArrayList<Restourant> rest=response.body().getRestourants();
                adapter = new RestourantsAdapter(rest,getActivity());
                restRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Restourants> call, Throwable t) {

            }
        });
        return view;
    }

}
