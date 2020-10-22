package com.uc.myfirebaseapss.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {


    private String id,subject,day,start,end,lecturer;

    public Course(){

    }

    public Course(String id, String subject, String day, String start, String end, String lecturer) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.start = start;
        this.end = end;
        this.lecturer = lecturer;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDay() {
        return day;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getLecturer() {
        return lecturer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.subject);
        dest.writeString(this.day);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeString(this.lecturer);
    }

    protected Course(Parcel in) {
        this.id = in.readString();
        this.subject = in.readString();
        this.day = in.readString();
        this.start = in.readString();
        this.end = in.readString();
        this.lecturer = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
