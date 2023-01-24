package com.evelyne.labs.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.evelyne.labs.myapplication.MainActivity;
import com.evelyne.labs.myapplication.MapsActivity;
import com.evelyne.labs.myapplication.R;

public class BookingFragment extends Fragment {
    private Button botton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_booking,null);
        botton = (Button) root.findViewById(R.id.buttonbooking);
        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_booking, container, false);
        return root;
    }
}