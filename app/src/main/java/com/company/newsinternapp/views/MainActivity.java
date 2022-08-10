package com.company.newsinternapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.company.newsinternapp.R;
import com.company.newsinternapp.adapter.RecyclerAdapter;
import com.company.newsinternapp.modals.NewsApiResponse;
import com.company.newsinternapp.modals.NewsHeadlines;
import com.company.newsinternapp.networking.RequestManager;
import com.company.newsinternapp.networking.ResponseListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logoutbtn);
        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener,"general",null);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private final ResponseListener<NewsApiResponse> listener = new ResponseListener<NewsApiResponse>() {
        @Override
        public void getData(List<NewsHeadlines> list, String message) {
            
            showNews(list);

        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView =findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerAdapter= new RecyclerAdapter(this, list);
        recyclerView.setAdapter(recyclerAdapter);

    }
}