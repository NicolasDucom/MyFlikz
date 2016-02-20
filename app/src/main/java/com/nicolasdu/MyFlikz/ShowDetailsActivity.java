package com.nicolasdu.MyFlikz;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicolasdu.MyFlikz.common.task.DownloadImageTask;
import com.nicolasdu.MyFlikz.model.Show;

import java.net.URISyntaxException;

public class ShowDetailsActivity extends AppCompatActivity {

    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        Bundle b = getIntent().getExtras();
        show = (Show) b.get("show");

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Details");
            actionBar.setDisplayShowTitleEnabled(true);
        }
        populateActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateActivity() {
        TextView showName = (TextView) this.findViewById(R.id.show_name);
        TextView showPlot = (TextView) this.findViewById(R.id.show_plot);
        ImageView imageView = (ImageView) this.findViewById(R.id.show_thumb);
        Button imdbButton = (Button) this.findViewById(R.id.button);
        Button favoriteButton = (Button) this.findViewById(R.id.fav_button);

        showName.setText(show.getName());
        showPlot.setText(show.getSynopsis());

        new DownloadImageTask(imageView)
                .execute(show.getImageSrc().toString());

        imdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(show.getImdbURL().toString()));
                startActivity(intent);
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringToDisplay = "Show Added to favorites";
                Snackbar snackbar = Snackbar
                        .make(v, stringToDisplay, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}
