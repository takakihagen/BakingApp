package com.example.mybakingapp.utils;

import com.example.mybakingapp.Model.Recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

public class JsonUtils {
    public static List<Recipe> getSimpleRecipeData(String jsonData) throws JSONException {
        Gson gson = new Gson();
        List<Recipe> recipes = gson.fromJson(jsonData, new TypeToken<List<Recipe>>(){}.getType());

        return recipes;
    }
}
