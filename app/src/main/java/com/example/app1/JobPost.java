package com.example.app1;

public class JobPost {
    public static final int VISIBLE = 1;
    public static final int INVISIBLE = -1;


    private String age;
    private String region;
    private String city;
    private String jobType;
    private String hourlyRate;
    private String jobField;
    private String specificJob;
    private String additionalInfo;
    private String phone;
    private String postKey; // لتحديد صاحب المنشور

    public JobPost() {} // Firebase يحتاج الكونستركتور الفارغ

    public JobPost(String age, String region, String city, String jobType, String hourlyRate,
                   String jobField, String specificJob, String additionalInfo, String phone, String postId) {
        this.age = age;
        this.region = region;
        this.city = city;
        this.jobType = jobType;
        this.hourlyRate = hourlyRate;
        this.jobField = jobField;
        this.specificJob = specificJob;
        this.additionalInfo = additionalInfo;
        this.phone = phone;
        this.postKey = postId;

    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getJobField() {
        return jobField;
    }

    public void setJobField(String jobField) {
        this.jobField = jobField;
    }

    public String getSpecificJob() {
        return specificJob;
    }

    public void setSpecificJob(String specificJob) {
        this.specificJob = specificJob;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
}