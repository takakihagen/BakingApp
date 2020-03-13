package com.example.mybakingapp.ui;

import android.content.Context;

import com.example.mybakingapp.Model.Ingredient;
import com.example.mybakingapp.Model.Recipe;
import com.example.mybakingapp.Model.Step;
import android.example.mybakingapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    private Recipe mRecipe;
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;

    private final RecipeDetailAdapterOnClickHandler mClickHandler;

    public interface RecipeDetailAdapterOnClickHandler{
        void onClick(Recipe recipe, int position);
    }

    RecipeDetailAdapter(RecipeDetailAdapterOnClickHandler recipeDetailAdapterOnClickHandler){
        mClickHandler = recipeDetailAdapterOnClickHandler;
    }

    @Override
    public RecipeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutForListItem = R.layout.card_list_step;
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttachToParentImmediately);
        RecipeDetailViewHolder viewHolder = new RecipeDetailViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        if(mSteps!=null){
            if(position==0) {
                holder.mStepTextView.setText(holder.itemView.getContext().getString(R.string.ingredient_title));
                holder.mStepCountTextView.setVisibility(View.GONE);
            } else {
                holder.mStepTextView.setText(mSteps.get(position-1).getShortDescription());
                holder.mStepCountTextView.setText(mSteps.get(position-1).getStepId());
                holder.mIngredientImageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mSteps!=null){
            if(mIngredients!=null) return mSteps.size()+1;
            else return mSteps.size();
        }else{
            if(mIngredients!=null) return 1;
            else return 0;
        }
    }

    public void setData(Recipe recipe){
        mRecipe = recipe;
        mIngredients = recipe.getIngredients();
        mSteps = recipe.getSteps();
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
        @BindView(R.id.recipe_step)
        TextView mStepTextView;
        @BindView(R.id.step_count)
        TextView mStepCountTextView;
        @BindView(R.id.ingredient_icon)
        ImageView mIngredientImageView;

        public RecipeDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mRecipe, adapterPosition);
        }
    }
}
