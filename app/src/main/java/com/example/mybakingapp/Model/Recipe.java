package com.example.mybakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private String id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private String servings;
    private String image;



    Recipe(String id, String name, List<Ingredient> ingredients, List<Step> steps, String servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public String getRecipeId(){return id;}

    public String getName(){return name;}

    public List<Ingredient> getIngredients(){return ingredients;}

    public List<Step> getSteps(){return steps;}

    public String getServings(){return servings;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeString(servings);
        dest.writeString(image);
    }

    private Recipe(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        public Recipe[] newArray(int size){
            return new Recipe[size];
        }
    };
}
