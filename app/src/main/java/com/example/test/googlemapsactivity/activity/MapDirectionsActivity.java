package com.example.test.googlemapsactivity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.test.googlemapsactivity.R;
import com.example.test.googlemapsactivity.adapter.MapDirectionsAdapter;
import com.example.test.googlemapsactivity.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 20/2/17.
 */
public class MapDirectionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_list);

        List<Step> steps = new ArrayList<>();
        steps = (ArrayList<Step>)getIntent().getSerializableExtra("Steps");
        for (Step stp:steps) {
            System.out.println("stp.getPath() = " + stp.getPath());
            System.out.println("stp.getDistance() = " + stp.getDistance().getText());
            System.out.println("stp.getDuration() = " + stp.getDuration().getText());

        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MapDirectionsAdapter(steps, R.layout.directions_item, this));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
