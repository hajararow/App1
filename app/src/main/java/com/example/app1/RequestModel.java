package com.example.app1;

public class RequestModel {

    private String specificJob;
    private String city;
    private String hourlyRate;
    private String age;
    private String postKey;

    private String region;
    private String jobType;
    private String field;
    private String info;
    private String phone;

    public RequestModel() {}

    public RequestModel(String specificJob,
                        String city,
                        String hourlyRate,
                        String age,
                        String postKey,
                        String region,
                        String jobType,
                        String field,
                        String info,
                        String phone) {

        this.specificJob = specificJob;
        this.city = city;
        this.hourlyRate = hourlyRate;
        this.age = age;
        this.postKey = postKey;

        this.region = region;
        this.jobType = jobType;
        this.field = field;
        this.info = info;
        this.phone = phone;
    }

    public String getSpecificJob() {
        return specificJob;
    }

    public String getCity() {
        return city;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public String getAge() {
        return age;
    }

    public String getPostKey() {
        return postKey;
    }

    public String getRegion() {
        return region;
    }

    public String getJobType() {
        return jobType;
    }

    public String getField() {
        return field;
    }

    public String getInfo() {
        return info;
    }

    public String getPhone() {
        return phone;
    }
}