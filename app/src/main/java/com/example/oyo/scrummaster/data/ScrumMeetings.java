package com.example.oyo.scrummaster.data;

import java.util.ArrayList;
import java.util.List;

public class ScrumMeetings {

    String scrumName;
    String scrumDate;
    List<Member> members=new ArrayList<>();
    int prevId;


    public ScrumMeetings(String scrumName, String scrumDate, List<Member> members) {
        this.scrumName = scrumName;
        this.scrumDate = scrumDate;
        this.members = members;
    }

    public String getScrumName() {
        return scrumName;
    }

    public String getScrumDate() {
        return scrumDate;
    }

    public List<Member> getMembers() {
        return members;
    }
}
