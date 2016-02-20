package com.nicolasdu.MyFlikz.common.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicolasdu.MyFlikz.R;
import com.nicolasdu.MyFlikz.common.task.DownloadImageTask;
import com.nicolasdu.MyFlikz.model.Show;

import java.util.List;

/**
 * Created by Nicolas on 2/18/2016.
 */
public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private List<Show> showList;
    private Activity context;

    public ShowAdapter(Activity context,List<Show> showList) {
        this.context = context;
        this.showList = showList;
    }

    public void setList(List<Show> showList) {
        this.showList = showList;
    }

    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShowAdapter.ViewHolder holder, int position) {
        Show show = showList.get(position);
        holder.showNameTxv.setText(show.getName());
        new DownloadImageTask(holder.imageView)
                .execute(show.getImageSrc().toString());
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        private View thumbView;
        private TextView showNameTxv;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            showNameTxv = (TextView) v.findViewById(R.id.show_name);
            imageView = (ImageView) v.findViewById(R.id.show_thumb);
        }
    }
}
