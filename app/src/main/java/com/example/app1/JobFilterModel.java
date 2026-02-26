package com.example.app1;

public class JobFilterModel {
    private String age;
    private String region;
    private String city;
    private String jobType;
    private String hourlyRate;
    private String jobField;
    private String specificJob;

    public JobFilterModel() {}

    public JobFilterModel(String age, String region, String city,
                          String jobType, String hourlyRate,
                          String jobField, String specificJob) {

        this.age = age;
        this.region = region;
        this.city = city;
        this.jobType = jobType;
        this.hourlyRate = hourlyRate;
        this.jobField = jobField;
        this.specificJob = specificJob;
    }

    public String getAge() { return age; }
    public String getRegion() { return region; }
    public String getCity() { return city; }
    public String getJobType() { return jobType; }
    public String getHourlyRate() { return hourlyRate; }
    public String getJobField() { return jobField; }
    public String getSpecificJob() { return specificJob; }
}