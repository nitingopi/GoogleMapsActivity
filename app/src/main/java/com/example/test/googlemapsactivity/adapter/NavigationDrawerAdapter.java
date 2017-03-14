package com.example.test.googlemapsactivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.googlemapsactivity.R;
import com.example.test.googlemapsactivity.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by test on 13/2/17.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private Context context;
    LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data){
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem navDrawerItem = data.get(position);
        holder.title.setText(navDrawerItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}
