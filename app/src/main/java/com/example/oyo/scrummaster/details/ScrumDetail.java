package com.example.oyo.scrummaster.details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import android.media.MediaPlayer;
import com.example.oyo.scrummaster.R;
import com.example.oyo.scrummaster.dashboard.ScrumListAdapter;
import com.example.oyo.scrummaster.data.Member;
import com.example.oyo.scrummaster.data.ScrumMeetings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ScrumDetail extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView name,date;
    private List<ScrumMeetings> scrumItems=new ArrayList<>();
    private List<Member> scrumMembers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum_detail);


        Intent intent=getIntent();
        int position=(int) intent.getSerializableExtra("pos");
        name=(TextView) findViewById(R.id.scrumNameDetail);
        date=(TextView) findViewById(R.id.scrumDateDetail);
        SharedPreferences sharedPreferences=getSharedPreferences("scrumMembers",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("List",null);
        Type type=new TypeToken<List<ScrumMeetings>>(){}.getType();
        scrumItems=gson.fromJson(json,type);
        name.setText(scrumItems.get(position).getScrumName());
        date.setText(scrumItems.get(position).getScrumDate());
         scrumMembers=scrumItems.get(position).getMembers();
         getRecycleView();
    }

    public void getRecycleView(){
        mAdapter=new ScrumListDetailAdapter(scrumMembers);
        mRecyclerView=findViewById(R.id.scrumdetailRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

  public void onPause(){
        super.onPause();
        MediaPlayer mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
  }

}
