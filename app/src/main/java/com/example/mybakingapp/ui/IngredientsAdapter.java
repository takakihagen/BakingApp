package com.example.mybakingapp.ui;

import android.content.Context;
import com.example.mybakingapp.Model.Ingredient;

import android.example.mybakingapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> mIngredients;

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutForListItem = R.layout.card_list_ingredient;
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttachToParentImmediately);
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        if(mIngredients!=null){
            Ingredient ing = mIngredients.get(position);
            holder.mIngredientTextView.setText(ing.getIngredient());
            holder.mQuantityTextView.setText(ing.getQuantity());
            holder.mMeasureTextView.setText(ing.getMeasure());
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients==null?0:mIngredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredients = ingredients;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_ingredient)
        TextView mIngredientTextView;
        @BindView(R.id.tv_quantity)
        TextView mQuantityTextView;
        @BindView(R.id.tv_measure)
        TextView mMeasureTextView;
        public IngredientsViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
