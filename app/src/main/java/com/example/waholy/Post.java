package com.example.waholy;

public class Post {
    private String topic;
    private String name;
    private String details;
    private String job;
    private String amount;

    public Post() {
    }

    public Post(String topic, String name, String details, String job,String amount) {
        this.topic = topic;
        this.name = name;
        this.details = details;
        this.job = job;
        this.amount = amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getJob() {
        return job;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
