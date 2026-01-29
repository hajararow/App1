package com.example.app1;

public class RequestModel {
    public String specificJob;
    public String city;
    public String hourlyRate;
    public String age;

    public RequestModel() {} // فارغ للكود

    public RequestModel(String specificJob, String city, String hourlyRate, String age) {
        this.specificJob = specificJob;
        this.city = city;
        this.hourlyRate = hourlyRate;
        this.age = age;
    }

}
