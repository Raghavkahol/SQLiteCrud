package com.example.oyo.scrummaster.dashboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.oyo.scrummaster.R;

import java.util.*;
import com.example.oyo.scrummaster.data.ScrumMeetings;
public class ScrumListAdapter extends RecyclerView.Adapter<ScrumListAdapter.ViewHolder> {
    List<ScrumMeetings>  scrumList;
    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=(View) LayoutInflater.from(parent.getContext()).inflate(R.layout.mylayout,parent,false);
        ViewHolder vh=new ViewHolder(v,mListener);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ScrumMeetings scrum=scrumList.get(position);
        holder.scrumNameView.setText(scrum.getScrumName());
        holder.scrumDateView.setText(scrum.getScrumDate());

    }

    @Override
    public int getItemCount() {
        return scrumList.size();
    }

    public ScrumListAdapter(List<ScrumMeetings> myDataset){
        this.scrumList=myDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView scrumNameView;
        public TextView scrumDateView;

        public ViewHolder(View v,final onItemClickListener listener){
            super(v);

            scrumNameView=(TextView) v.findViewById(R.id.ScrumName);
            scrumDateView=(TextView) v.findViewById(R.id.ScrumDate);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener!=null){
                        int position=getAdapterPosition();
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}