package com.example.mybakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String  quantity;
    private String measure;
    private String ingredient;

    Ingredient(String quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    Ingredient(){
        this.quantity = null;
        this.measure = null;
        this.ingredient = null;
    }

    public String getQuantity(){return quantity;}

    public String getMeasure(){return measure;}

    public String getIngredient(){return ingredient;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    private Ingredient(Parcel in){
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        public Ingredient[] newArray(int size){
            return new Ingredient[size];
        }
    };
}
