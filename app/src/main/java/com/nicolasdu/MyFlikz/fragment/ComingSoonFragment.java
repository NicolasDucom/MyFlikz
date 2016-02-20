package com.nicolasdu.MyFlikz.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nicolasdu.MyFlikz.common.task.FetchMediaTask;
import com.nicolasdu.MyFlikz.model.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

/**
 * Created by Nicolas on 2/17/2016.
 */
public class ComingSoonFragment extends ShowsListFragment {

    public ComingSoonFragment() {
        ACTION_FOR_INTENT_CALLBACK = "INTENT_CALLBACK_COMING_SOON";
        FILTER = "comingSoon";
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (progress != null) {
                    progress.dismiss();
                }
                content = intent.getStringExtra(FetchMediaTask.HTTP_RESPONSE);
                try {
                    JSONArray list = new JSONObject(content).getJSONObject("data").getJSONArray("inTheaters");
                    for (int i = 0; i < list.length(); i++) {
                        Show.buildListFromJSONArray(showsList, list.getJSONObject(i).getJSONArray("movies"));
                    }
                    Collections.sort(showsList);
                    showAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
