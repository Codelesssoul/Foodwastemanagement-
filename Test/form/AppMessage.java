package com.example.foodwastagemanagement.form;

public class AppMessage {

    private String mid;
    private String sender;
    private String receiver;
    private String textmessage;

    public String getTextmessage() {
        return textmessage;
    }
    public void setTextmessage(String textmessage) {
        this.textmessage = textmessage;
    }
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
