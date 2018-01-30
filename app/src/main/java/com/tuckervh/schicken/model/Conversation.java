package com.tuckervh.schicken.model;

/**
 * Created by will on 1/28/18.
 */

public class Conversation {
    private String name;
    private long phoneNumber;
    private String initials;
    private Message mostRecent;

    public Conversation(String name, String initials, Message mostRecent) {
        this.name = name;
        this.initials = initials;
        this.mostRecent = mostRecent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Message getMostRecent() {
        return mostRecent;
    }

    public void setMostRecent(Message mostRecent) {
        this.mostRecent = mostRecent;
    }
}
