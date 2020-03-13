package com.example.mybakingapp.ui;

import com.example.mybakingapp.Model.Recipe;

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

public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler {

    public RecipeDetailFragment(){}
    public RecipeDetailFragment(StepOnClickHandler hanlder){
        mClickHandler = hanlder;
    }

    private Recipe mRecipe;
    private StepOnClickHandler mClickHandler;

    public interface StepOnClickHandler{
        void onClick(Recipe recipe, int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView mStepsList = (RecyclerView) view.findViewById(R.id.list_of_steps);
        RecipeDetailAdapter mAdapter = new RecipeDetailAdapter(this);
        if(mRecipe!=null){
            mAdapter.setData(mRecipe);
        }
        mStepsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsList.setAdapter(mAdapter);

        return view;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }

    @Override
    public void onClick(Recipe recipe, int position) {
        mClickHandler.onClick(recipe, position);
    }

}
