package com.example.mybakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import com.example.mybakingapp.Model.Ingredient;
import com.example.mybakingapp.Model.Recipe;
import com.example.mybakingapp.Model.Step;
import android.example.mybakingapp.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailStepActivity extends AppCompatActivity {

    @BindView(R.id.previous_button) Button mPreButton;
    @BindView(R.id.next_button) Button mNextButton;
    private Recipe mRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        int orientation = getResources().getConfiguration().orientation;

        ButterKnife.bind(this);

        if(orientation==Configuration.ORIENTATION_LANDSCAPE){
            if (getSupportActionBar() != null){
                getSupportActionBar().hide();
                mPreButton.setVisibility(View.INVISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
            }
        }else{
            if (getSupportActionBar()!=null){
                getSupportActionBar().show();
                mPreButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);

            }
        }
        if(savedInstanceState==null) {
            Intent intent = getIntent();
            final int position = intent.getIntExtra(getString(R.string.extra_position_name), 0);
            final Recipe recipe = intent.getParcelableExtra(getString(R.string.extra_recipe_name));
            mRecipe = recipe;
            if (position!=0) {
                Step step = recipe.getSteps().get(position-1);

                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setStep(step);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.tv_recipe_detail_fragment, stepDetailFragment).commit();
            } else if (position==0) {
                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(recipe.getIngredients());

                DetailIngredientsFragment detailIngredientsFragment = new DetailIngredientsFragment();
                detailIngredientsFragment.setIngredients(ingredients);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.tv_recipe_detail_fragment, detailIngredientsFragment).commit();
            }
            if(position<recipe.getSteps().size()) {
                mNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context contex = v.getContext();
                        Intent startDetailIntent = new Intent(contex, DetailStepActivity.class);
                        startDetailIntent.putExtra(getString(R.string.extra_recipe_name), recipe);
                        startDetailIntent.putExtra(getString(R.string.extra_position_name), position + 1);
                        startActivity(startDetailIntent);
                    }
                });
            }else {
                mNextButton.setVisibility(View.INVISIBLE);
            }
            if(position-1>=0){
                mPreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context contex = v.getContext();
                        Intent startDetailIntent = new Intent(contex, DetailStepActivity.class);
                        startDetailIntent.putExtra(getString(R.string.extra_recipe_name), recipe);
                        startDetailIntent.putExtra(getString(R.string.extra_position_name), position-1);
                        startActivity(startDetailIntent);
                    }
                });
            }else{
                mPreButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setVisibilityNextButton(int visibility) {
        mNextButton.setVisibility(visibility);
    }

    public void setVisibilityPreButton(int visibility) {
        mPreButton.setVisibility(visibility);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if (getSupportActionBar()!=null){
                getSupportActionBar().show();
            }
            setVisibilityPreButton(View.VISIBLE);
            setVisibilityNextButton(View.VISIBLE);
        }else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (getSupportActionBar() != null){
                getSupportActionBar().hide();
            }

            setVisibilityPreButton(View.INVISIBLE);
            setVisibilityNextButton(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_step, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.back_to_recipe_detail){
            Intent startSettingActivity = new Intent(this, DetailActivity.class);
            startSettingActivity.putExtra(getString(R.string.extra_recipe_name), mRecipe);
            startActivity(startSettingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
