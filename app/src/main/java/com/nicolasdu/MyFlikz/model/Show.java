package com.nicolasdu.MyFlikz.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Nicolas on 2/17/2016.
 */
public class Show implements Serializable,Comparable {
    private String name;
    private String synopsis;
    private URL imageSrc;
    private URL imdbURL;

    public Show(String name, String synopsis) {
        this.name = name;
        this.synopsis = synopsis;
    }

    public Show(String name, String synopsis, URL imageSrc, URL imdbURL) {
        this.name = name;
        this.synopsis = synopsis;
        this.imageSrc = imageSrc;
        this.imdbURL = imdbURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public URL getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(URL imageSrc) {
        this.imageSrc = imageSrc;
    }

    public URL getImdbURL() {
        return imdbURL;
    }

    public void setImdbURL(URL imdbURL) {
        this.imdbURL = imdbURL;
    }


    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }

    public static List<Show> buildListFromJSONArray(List<Show> showsList, JSONArray showsJson) {
        for(int i = 0; i < showsJson.length(); i++) {
            try {
                JSONObject showJson = showsJson.getJSONObject(i);
                Show show = new Show(showJson.getString("title"), showJson.getString("plot"));
                try {
                    show.setImageSrc(new URL(showJson.getString("urlPoster")));
                    show.setImdbURL(new URL(showJson.getString("urlIMDB")));
                    showsList.add(show);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return showsList;
    }

    @Override
    public int compareTo(Object another) {
        Show anotherShow = (Show) another;
        return this.getName().compareTo(((Show) another).getName());
    }
}
