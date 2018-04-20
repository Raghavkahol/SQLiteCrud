package com.example.oyo.scrummaster.data;

public class Member {
    String memberName;
    String memberDesignation;
    String audio;
    int scrumId;

    public String getMemberName() {
        return memberName;
    }
    public void setAudio(String str){
        audio=str;
    }
    public String getDesignation() {
        return memberDesignation;
    }

    public String getAudio() {
        return audio;
    }

    public  Member(String name, String designation){
        memberName=name;
        memberDesignation=designation;
    }
}

