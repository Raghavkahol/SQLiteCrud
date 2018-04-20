package com.example.oyo.scrummaster.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.oyo.scrummaster.R;
import com.example.oyo.scrummaster.createscrum.CreateScrumActivity;

import com.example.oyo.scrummaster.data.Member;
import com.example.oyo.scrummaster.data.ScrumMeetings;
import com.example.oyo.scrummaster.details.ScrumDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private ScrumListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ScrumMeetings> scrumItems =new ArrayList<>();
    private int REQ_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(this);
        loadData();
        getRecyclerView();
    }

    public void getRecyclerView(){
        mAdapter=new ScrumListAdapter(scrumItems);
        mRecyclerView=findViewById(R.id.ScrumListView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ScrumListAdapter.onItemClickListener(){
            public void onItemClick(int position){
                Intent intent=new Intent(MainActivity.this, ScrumDetail.class);
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences("scrumMembers",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("List",null);
        Type type=new TypeToken<List<ScrumMeetings>>(){}.getType();
        scrumItems=gson.fromJson(json,type);
        if(scrumItems==null)
            scrumItems=new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent1=new Intent(MainActivity.this,CreateScrumActivity.class);
                startActivityForResult(intent1,REQ_CODE);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==REQ_CODE&&resultCode==RESULT_OK&&(int)data.getSerializableExtra("Allow")==1){
                SharedPreferences sharedPreferences=getSharedPreferences("scrumMembers",MODE_PRIVATE);
                Gson gson=new Gson();
                String json=sharedPreferences.getString("List",null);
                Type type=new TypeToken<List<ScrumMeetings>>(){}.getType();
                scrumItems=gson.fromJson(json,type);
                getRecyclerView();
            }
    }
}
