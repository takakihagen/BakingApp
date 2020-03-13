package com.example.mybakingapp.ui;

import android.content.Context;
import com.example.mybakingapp.Model.Recipe;
import android.example.mybakingapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipes;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe recipe);
    }

    RecipeAdapter(RecipeAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutForListItem = R.layout.card_list_item;
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if(mRecipes!=null && mRecipes.size()!=0) {
            holder.mNameTextView.setText(mRecipes.get(position).getName());
            holder.mServingTitleTextView.setText(holder.itemView.getContext().getString(R.string.serving_title));
            holder.mServingTextView.setText(mRecipes.get(position).getServings());
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes==null?0:mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        @BindView(R.id.name_of_recipe)
        TextView mNameTextView;
        @BindView(R.id.tv_serving_title)
        TextView mServingTitleTextView;
        @BindView(R.id.tv_serving)
        TextView mServingTextView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    public void setRecipes(List<Recipe> recipes){mRecipes=recipes;}
}
