package com.nicolasdu.MyFlikz.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicolasdu.MyFlikz.R;

/**
 * Created by Nicolas on 2/20/2016.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String navTitles[];
    private int icons[];
    private String name;
    private String email;
    private int profile;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;
        TextView email;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);


            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
               // imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderId = 1;
            } else {
                name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                holderId = 0;
            }
        }
    }

    public DrawerAdapter(String[] navTitles, int[] icons, String name, String email, int profile) {
        this.navTitles = navTitles;
        this.icons = icons;
        this.name = name;
        this.email = email;
        this.profile = profile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType == TYPE_ITEM) {
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_layout,parent,false);
           return new ViewHolder(v,viewType);
       } else if (viewType == TYPE_HEADER) {
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header,parent,false);
           return new ViewHolder(v,viewType);
       }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.holderId ==1) {
            holder.textView.setText(navTitles[position - 1]);
            //holder.imageView.setImageResource(icons[position -1]);
            }
        else{

        holder.profile.setImageResource(profile);
        holder.name.setText(name);
        holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return navTitles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}

