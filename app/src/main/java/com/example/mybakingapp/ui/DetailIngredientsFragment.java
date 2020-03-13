package com.example.mybakingapp.ui;

import com.example.mybakingapp.Model.Ingredient;
import android.example.mybakingapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailIngredientsFragment extends Fragment {
    public DetailIngredientsFragment(){}

    private static final String INGREDIENTS_OBJECT = "ingredients_object";

    private List<Ingredient> mIngredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        if(savedInstanceState!=null){
            mIngredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_OBJECT);
        }
        RecyclerView mIngredientList = (RecyclerView) view.findViewById(R.id.tv_detail_ingredients);
        mIngredientList.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsAdapter mAdapter = new IngredientsAdapter();
        if(mIngredients!=null){
            mAdapter.setIngredients(mIngredients);
        }
        mIngredientList.setAdapter(mAdapter);

        return view;
    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(INGREDIENTS_OBJECT, new ArrayList<Ingredient>(mIngredients));
    }
}
