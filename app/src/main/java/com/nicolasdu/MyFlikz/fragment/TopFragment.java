package com.nicolasdu.MyFlikz.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicolasdu.MyFlikz.R;
import com.nicolasdu.MyFlikz.common.adapter.ShowAdapter;
import com.nicolasdu.MyFlikz.common.task.FetchMediaTask;
import com.nicolasdu.MyFlikz.model.Show;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nicolas on 2/17/2016.
 */
public class TopFragment extends ShowsListFragment{

    public TopFragment() {
        ACTION_FOR_INTENT_CALLBACK = "INTENT_CALLBACK_TOP";
        FILTER = "top";
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(progress != null)
                {
                    progress.dismiss();
                }
                content = intent.getStringExtra(FetchMediaTask.HTTP_RESPONSE);
                try {
                    Show.buildListFromJSONArray(showsList,  new JSONObject(content).getJSONObject("data").getJSONArray("movies"));
                    Collections.sort(showsList);
                    showAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}