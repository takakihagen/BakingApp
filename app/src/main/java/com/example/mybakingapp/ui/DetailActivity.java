package com.example.mybakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import com.example.mybakingapp.Model.Ingredient;
import com.example.mybakingapp.Model.Recipe;

import android.example.mybakingapp.R;
import com.example.mybakingapp.Service.RecipeService;
import android.os.Bundle;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity
                            implements RecipeDetailFragment.StepOnClickHandler {

    private boolean mTwoPane;
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if(intent.hasExtra("recipe")) mRecipe = intent.getParcelableExtra("recipe");

        //Inflate Fragments
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment(this);
        recipeDetailFragment.setRecipe(mRecipe);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_fragment, recipeDetailFragment).commit();

        if(findViewById(R.id.tv_recipe_detail_fragment)!=null){
            mTwoPane = true;
            if(savedInstanceState==null) {
                DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();
                detailIngredientsFragment.setIngredients(mRecipe.getIngredients());

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.tv_recipe_detail_fragment, detailIngredientsFragment).commit();
            }
        }else{
            mTwoPane = false;
        }

        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(mRecipe.getIngredients());
        RecipeService.startActionUpdataIngredients(this, ingredients);
    }

    @Override
    public void onClick(Recipe recipe, int position) {
        if(!mTwoPane) {
            Context context = this;
            Intent startDetailIntent = new Intent(context, DetailStepActivity.class);
            startDetailIntent.putExtra(getString(R.string.extra_recipe_name), recipe);
            startDetailIntent.putExtra(getString(R.string.extra_position_name), position);
            startActivity(startDetailIntent);
        }else{
            if(position==0) {
                DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();
                detailIngredientsFragment.setIngredients(recipe.getIngredients());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.tv_recipe_detail_fragment, detailIngredientsFragment).commit();
            }else {
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setStep(recipe.getSteps().get(position-1));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.tv_recipe_detail_fragment, stepDetailFragment).commit();
            }
        }

    }
}
