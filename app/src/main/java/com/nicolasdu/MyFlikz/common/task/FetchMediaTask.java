package com.nicolasdu.MyFlikz.common.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Nicolas on 2/19/2016.
 */
public class FetchMediaTask extends AsyncTask<String, Void, String>{

    private Context context;
    private String action;
    private String callUrl;
    public static final String HTTP_RESPONSE = "httpResponse";

    public FetchMediaTask(Context context, String action) {
        this.context = context;
        this.action = action;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        Uri.Builder builder = new Uri.Builder()
                .scheme("http")
                .authority("www.myapifilms.com")
                .path("imdb")
                .appendPath(params[0])
                .appendQueryParameter("format", "json")
                .appendQueryParameter("language", "en-us")
                .appendQueryParameter("token", "e170d3f0-6958-469b-9573-d3b2943b4898");
        switch (params[0]) {
            case "top":
                builder.appendQueryParameter("start","1")
                        .appendQueryParameter("end", "100")
                        .appendQueryParameter("data","1");
                break;
            case "comingSoon":
                builder.appendQueryParameter("date", new SimpleDateFormat("yyyy-MM").format(new Date()));
                break;
            default:
                break;
        }

        String callUrl = builder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, callUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(action);
                        intent.putExtra(HTTP_RESPONSE, response);
                        context.sendBroadcast(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR FETCHING MOVIES :"+error);
            }
        });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(stringRequest);

        return result;
    }

}
