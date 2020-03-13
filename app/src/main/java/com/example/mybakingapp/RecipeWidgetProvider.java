package com.example.mybakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import com.example.mybakingapp.Model.Ingredient;

import android.example.mybakingapp.R;

import com.example.mybakingapp.Service.RecipeService;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, ArrayList<Ingredient> ingredients) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        CharSequence widgetText = "";
        if(ingredients==null || ingredients.size()==0){
            //for the first time
            widgetText = context.getString(R.string.widget_default_text);
        }else {
            widgetText = widgetText.toString() + "Ingredient" + "\n";
            for (Ingredient ingredient : ingredients) {
                String ing = ingredient.getIngredient() + ":" + ingredient.getQuantity() + " " + ingredient.getMeasure();
                widgetText = widgetText.toString() + "+" + ing + "\n";
            }
        }
        views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeService.startActionUpdataIngredients(context, new ArrayList<Ingredient>());
    }

    public static void updataRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          ArrayList<Ingredient> ingredients, int[] appWidgetIds){
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

