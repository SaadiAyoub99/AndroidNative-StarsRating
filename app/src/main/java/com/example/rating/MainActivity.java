package com.example.rating;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.*;
import com.example.rating.adapter.StarAdapter;
import com.example.rating.beans.Star;
import com.example.rating.service.StarService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StarService service;
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));

        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    };




    public void init(){
        service.create(new Star("Ansu Fati", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmA1YcPeONcbLzYVJLPh0xV82u_mwludQwow&usqp=CAU", 1));
        service.create(new Star("Ronald Araujo", "https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/ARA%C3%9AJO.jpg", 5));
        service.create(new Star("Pablo Gavi", "https://www.footballdatabase.eu/images/photos/players/a_443/443092.jpg", 3.5f));
        service.create(new Star("Memphis Depay", "https://assets.laliga.com/squad/2021/t178/p106824/2048x2048/p106824_t178_2021_1_002_000.jpg", 3));
        service.create(new Star("frenkie de jong", "https://s.hs-data.com/bilder/spieler/gross/290241.jpg?fallback=png", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Share ){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt) .startChooser();
        }
        return super.onOptionsItemSelected(item); }

}