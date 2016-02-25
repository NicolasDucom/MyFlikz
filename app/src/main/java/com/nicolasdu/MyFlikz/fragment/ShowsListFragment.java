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
import com.nicolasdu.MyFlikz.common.util.ItemClickSupport;
import com.nicolasdu.MyFlikz.model.Show;
import com.nicolasdu.MyFlikz.showsFilter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nicolas on 2/20/2016.
 */
public class ShowsListFragment extends Fragment  {
    protected  String ACTION_FOR_INTENT_CALLBACK = "";
    protected  String FILTER = "";
    protected  BroadcastReceiver receiver;
    protected  ProgressDialog progress;
    protected  String content;
    protected  List<Show> showsList = new ArrayList<>();
    protected  ShowAdapter showAdapter;
    protected  RecyclerView recyclerView;
    private showsFilter filter;

    private ShowListCallback callback;

    private void createFilter(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (progress != null) {
                    progress.dismiss();
                }
                content = intent.getStringExtra(FetchMediaTask.HTTP_RESPONSE);
                switch (filter){
                    case COMING_SOON:
                    case IN_THEATERS:
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
                        break;
                    case TOP:
                        try {
                            Show.buildListFromJSONArray(showsList,  new JSONObject(content).getJSONObject("data").getJSONArray("movies"));
                            Collections.sort(showsList);
                            showAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        this.filter = (showsFilter) this.getArguments().get("filter");
        assert filter != null;
        switch (filter){
            case COMING_SOON:
                ACTION_FOR_INTENT_CALLBACK = "INTENT_CALLBACK_COMING_SOON";
                FILTER = "comingSoon";
                break;
            case IN_THEATERS:
                ACTION_FOR_INTENT_CALLBACK = "INTENT_CALLBACK_IN_THEATERS";
                FILTER = "inTheaters";
                break;
            case TOP:
                ACTION_FOR_INTENT_CALLBACK = "INTENT_CALLBACK_TOP";
                FILTER = "top";
                break;
        }
        createFilter();
        recyclerView = (RecyclerView) view.findViewById(R.id.shows_list);
        recyclerView.setHasFixedSize(false);
        getContent();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        showAdapter = new ShowAdapter(getActivity(),showsList);
        recyclerView.setAdapter(showAdapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport
                .OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                callback.onItemSelected(showsList.get(position));
            }
        });
        return view;
    }

    private void getContent()
    {
        showsList.clear();
        try {
            FetchMediaTask task = new FetchMediaTask(getActivity(), ACTION_FOR_INTENT_CALLBACK);
            task.execute(FILTER);
            progress = ProgressDialog.show(getActivity(), "Fetching Data....", "", true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowListCallback) {
             callback = (ShowListCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PersonListCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface ShowListCallback {
        void onItemSelected(Show show);
    }

}
