package com.example.oyo.scrummaster.createscrum;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import com.example.oyo.scrummaster.R;

import com.example.oyo.scrummaster.data.Member;

import java.util.List;

public class CreateScrumAdapter extends RecyclerView.Adapter<CreateScrumAdapter.ViewHolder> {
   private List<Member> memberList;

    public CreateScrumAdapter(List<Member> memberArrayList) {
        this.memberList=memberArrayList;
    }

    @NonNull
    @Override
    public CreateScrumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=(View) LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_scrum_view,parent,false);
        ViewHolder vh=new ViewHolder(v);
       return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final CreateScrumAdapter.ViewHolder holder, final int position) {
      final  Member newMember=memberList.get(position);
        holder.nameView.setText(newMember.getMemberName());
        holder.designationView.setText((newMember.getDesignation()));
/*        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });*/
        holder.record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    holder.scrumAudioRecorder.prepare();
                    holder.scrumAudioRecorder.start();
                    holder.record.setBackgroundColor(Color.GRAY);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            holder.record.setBackgroundColor(Color.WHITE);
                        }
                    }, 500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.record.setEnabled(false);
                holder.stop.setEnabled(true);
            }
        });
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(holder.outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    holder.play.setBackgroundColor(Color.GRAY);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            holder.play.setBackgroundColor(Color.WHITE);
                        }
                    }, 500);
                    } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newMember.setAudio(holder.outputFile);
                    holder.scrumAudioRecorder.release();
                    holder.scrumAudioRecorder = null;
                    holder.stop.setBackgroundColor(Color.GRAY);
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            holder.stop.setBackgroundColor(Color.WHITE);
                        }
                    }, 500);
                    holder.record.setEnabled(true);
                    holder.stop.setEnabled(false);
                    holder.play.setEnabled(true);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size() ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
       public TextView nameView;
       public TextView designationView;
       public ImageView delete;
        public ImageView play;
        public ImageView stop;
        public ImageView record;
        public MediaRecorder scrumAudioRecorder;
        private String outputFile=Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording"+System.currentTimeMillis()+".3gp";
        public ViewHolder(View itemView) {
           super(itemView);
            nameView=(TextView) itemView.findViewById(R.id.newMemberName);
            designationView=(TextView) itemView.findViewById(R.id.newMemberDesignation);
            //delete=(ImageView)itemView.findViewById(R.id.deleteMember);
            play=(ImageView)itemView.findViewById(R.id.playRecording);
            record=(ImageView)itemView.findViewById(R.id.recordingMic);
            stop=(ImageView)itemView.findViewById(R.id.stopMic);
            stop.setEnabled(false);
            play.setEnabled(false);
            scrumAudioRecorder = new MediaRecorder();
            scrumAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            scrumAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            scrumAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            scrumAudioRecorder.setOutputFile(outputFile);

        }
    }
}
