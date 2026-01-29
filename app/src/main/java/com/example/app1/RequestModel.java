package com.example.app1;

public class RequestModel {
    private String specificJob;
    private String city;
    private String hourlyRate;
    private String age;
    private String postKey;

    public RequestModel() {} // فارغ للكود

    public RequestModel(String specificJob, String city, String hourlyRate, String age,String Key) {
        this.specificJob = specificJob;
        this.city = city;
        this.hourlyRate = hourlyRate;
        this.age = age;
        this.postKey = postKey;
    }

    public String getSpecificJob() {
        return specificJob;
    }

    public void setSpecificJob(String specificJob) {
        this.specificJob = specificJob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
}
