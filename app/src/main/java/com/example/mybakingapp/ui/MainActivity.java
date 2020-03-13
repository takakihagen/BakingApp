package com.example.mybakingapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.content.Context;
import android.content.Intent;

import com.example.mybakingapp.Idling.SimpleIdlingResource;
import com.example.mybakingapp.Model.Recipe;
import android.example.mybakingapp.R;

import com.example.mybakingapp.utils.JsonUtils;
import com.example.mybakingapp.utils.NetworkUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    private static final int NUM_LIST_ITEMS = 4;

    private RecipeAdapter mAdapter;

    //Only for tests
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @BindView(R.id.tv_recipe_card)
    RecyclerView mRecipeList;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(findViewById(R.id.main_vertical_layout)!=null) {
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecipeList.setLayoutManager(mLinearLayoutManager);
        }else if(findViewById(R.id.main_tablet_layout)!=null){
            mGridLayoutManger = new GridLayoutManager(this, 3);
            mRecipeList.setLayoutManager(mGridLayoutManger);
        }
        mAdapter = new RecipeAdapter(this);
        //mAdapter.notifyDataSetChanged();
        mRecipeList.setAdapter(mAdapter);

        getIdlingResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new FetchRecipeDataa().execute();
    }

    @Override
    public void onClick(Recipe recipe) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra("recipe", recipe);
        startActivity(intentToStartDetailActivity);
    }

    public class FetchRecipeDataa extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mIdlingResource.setIdleState(false);
            if(mIdlingResource!=null){
                mIdlingResource.setIdleState(false);
            }
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            URL url = NetworkUtils.getURL();
            try{
                String jsonData = NetworkUtils.getRespondFromHttp(url);
                List<Recipe> recipes = JsonUtils.getSimpleRecipeData(jsonData);
                return recipes;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            mAdapter.setRecipes(recipes);
            mAdapter.notifyDataSetChanged();
            if(mIdlingResource!=null){
                mIdlingResource.setIdleState(true);
            }
        }
    }
}
