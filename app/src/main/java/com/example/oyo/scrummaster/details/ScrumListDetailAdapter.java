package com.example.oyo.scrummaster.details;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oyo.scrummaster.dashboard.ScrumListAdapter;
import com.example.oyo.scrummaster.data.Member;
import com.example.oyo.scrummaster.R;
public class ScrumListDetailAdapter extends RecyclerView.Adapter<ScrumListDetailAdapter.ViewHolder> {
    private List<Member> memberRecords= new ArrayList<>();

    @NonNull
    @Override
    public ScrumListDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=(View) LayoutInflater.from(parent.getContext()).inflate(R.layout.content_scrum_detail_layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    public ScrumListDetailAdapter(List<Member> memberRecords) {
        this.memberRecords = memberRecords;
    }


    public void onBindViewHolder(@NonNull final ScrumListDetailAdapter.ViewHolder holder, int position) {
        final Member newMember=memberRecords.get(position);
        holder.nameView.setText(newMember.getMemberName());
        holder.designationView.setText((newMember.getDesignation()));
        holder.audio_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(newMember.getAudio());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    holder.audio_file.setBackgroundColor(Color.GRAY);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            holder.audio_file.setBackgroundColor(Color.WHITE);
                        }
                    }, 500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        });
    }

    @Override
    public int getItemCount() {
        return memberRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView designationView;
        public ImageView audio_file;
        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.newMemberName);
            designationView = (TextView) itemView.findViewById(R.id.newMemberDesignation);
            audio_file=(ImageView) itemView.findViewById(R.id.playAudio);

        }
    }
}
