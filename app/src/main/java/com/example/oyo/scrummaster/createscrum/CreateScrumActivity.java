package com.example.oyo.scrummaster.createscrum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.oyo.scrummaster.R;
import com.example.oyo.scrummaster.data.Member;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.example.oyo.scrummaster.data.ScrumMeetings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import android.widget.ArrayAdapter;


public class CreateScrumActivity extends AppCompatActivity implements AddMemberDialog.MemberDialogListener,View.OnClickListener{
    private Button save;
    private Button back;
    private RecyclerView dailyScrumRecyclerView;
    private CreateScrumAdapter scrumAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private String thisDate;
    private String teamName;
    public List<Member> membersList =new ArrayList<>();
    List<ScrumMeetings> newMeeting = new ArrayList<>();
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum_create);
        initView();
    }



    //recieves data from dialog box
    @Override
    public void addMember(String membername, String memberdesignation) {
        if(memberdesignation.length()!=0&&membername.length()!=0) {
            int position = membersList.size();
            membersList.add(position, new Member(membername, memberdesignation));
            scrumAdapter.notifyItemInserted(position);
        }
    }



    public void getRecycleView(List<Member> membersList){
        scrumAdapter=new CreateScrumAdapter(membersList);
        dailyScrumRecyclerView=findViewById(R.id.scrum_create);
        dailyScrumRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        dailyScrumRecyclerView.setLayoutManager(mLayoutManager);
        dailyScrumRecyclerView.setAdapter(scrumAdapter);
    }



    void initView(){
        Spinner spinner = (Spinner) findViewById(R.id.team_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.team_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               teamName=parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        getRecycleView(membersList);
        fab = (FloatingActionButton) findViewById(R.id.add_people);
        fab.setOnClickListener(this);
        TextView dateView=(TextView) findViewById(R.id.todayDate);
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
         thisDate = currentDate.format(todayDate);
        dateView.setText(thisDate);
        save=(Button)findViewById(R.id.saveScrum);
        save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_people:
                AddMemberDialog addMemberDialog=new AddMemberDialog();
                addMemberDialog.show(getSupportFragmentManager(),"member dialog");
                break;
            case R.id.saveScrum:
                sendResponse();
                break;

        }
    }

    public void onPause(){
        super.onPause();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
    }



    public void sendResponse(){
        String date=thisDate;
        Intent intent1=new Intent();
        if(membersList.size()==0)
            intent1.putExtra("Allow",0);
        else {
            intent1.putExtra("Allow", 1);
            SharedPreferences sharedPreferences = getSharedPreferences("scrumMembers", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ScrumMeetings sc = new ScrumMeetings(teamName, date, membersList);
            String json = sharedPreferences.getString("List", "");
            Type type = new TypeToken<List<ScrumMeetings>>() {}.getType();
            Gson gson = new Gson();
            newMeeting = gson.fromJson(json, type);
            if (newMeeting == null) {
                newMeeting = new ArrayList<>();
            }
            newMeeting.add(sc);
            String json1 = gson.toJson(newMeeting);
            editor.putString("List", json1);
            editor.commit();

            if (membersList.size() == 0)
                intent1.putExtra("Allow", 0);
            else
                intent1.putExtra("Allow", 1);
        }
        setResult(RESULT_OK,intent1);
        finish();
    }
}
