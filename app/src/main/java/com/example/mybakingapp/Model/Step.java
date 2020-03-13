package com.example.mybakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    Step(String stepId, String shortDescription, String description, String videoURl, String thumbnailURL){
        this.id = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURl;
        this.thumbnailURL = thumbnailURL;
    }

    Step(){
        this.id = null;
        this.shortDescription = null;
        this.description = null;
        this.videoURL = null;
        this.thumbnailURL = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    private Step(Parcel in){
        this.id = in.readString();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        public Step[] newArray(int size){
            return new Step[size];
        }
    };

    public String getStepId(){return id;}
    public String getShortDescription(){return shortDescription;}
    public String getDescription(){return description;}
    public String getVideoURl(){return videoURL;}
    public String getThumbnailURL(){return thumbnailURL;}
}
