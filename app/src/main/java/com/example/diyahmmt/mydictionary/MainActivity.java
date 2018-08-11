package com.example.diyahmmt.mydictionary;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.diyahmmt.mydictionary.Adapter.SearchAdapter;
import com.example.diyahmmt.mydictionary.Helper.DictionaryHelper;
import com.example.diyahmmt.mydictionary.Model.DictionaryModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    SearchView searchView;
    String language;
    private DictionaryHelper dictionaryHelper;
    private SearchAdapter searchAdapter;
    private ArrayList<DictionaryModel> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        searchView = (SearchView)findViewById(R.id.search_bar);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        dictionaryHelper = new DictionaryHelper(this);
        searchAdapter = new SearchAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

        language = "Eng";
        getData(language, "");

    }

    private void getData(String selection,  String search) {
        try {
            dictionaryHelper.open();
            if (search.isEmpty()) {
                arraylist = dictionaryHelper.getAllData(selection);
            } else {
                arraylist = dictionaryHelper.getDataByName(search, selection);
            }

            String title = null;
            String hint = null;
            if (selection == "Eng") {
                title = getResources().getString(R.string.eng_to_ind);
                hint = getResources().getString(R.string.search_word);
            } else {
                title = getResources().getString(R.string.ind_to_eng);
                hint = getResources().getString(R.string.cari_kata);
            }
            getSupportActionBar().setSubtitle(title);
            searchView.setQueryHint(hint);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dictionaryHelper.close();
        }
        searchAdapter.replaceAll(arraylist);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english_to_indo) {
            language = "Eng";
            getData(language, "");
        } else if (id == R.id.nav_indo_to_english) {
            language = "Ind";
            getData(language, "");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getData(language, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        getData(language, query);
        return false;
    }
}
