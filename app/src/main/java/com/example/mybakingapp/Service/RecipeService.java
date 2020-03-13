package com.example.mybakingapp.Service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Build;

import com.example.mybakingapp.Model.Ingredient;
import com.example.mybakingapp.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecipeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE_INGREDIENTS = "android.example.mybakingapp.Service.action.update_ingredients";

    // TODO: Rename parameters
    private static final String EXTRA_INGREDIENTS_LIST = "android.example.mybakingapp.Service.extra.ingredients_list";

    public RecipeService() {
        super("RecipeService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdataIngredients(Context context, List<Ingredient> ingredients) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS_LIST, new ArrayList<Ingredient>(ingredients));
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                final  ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(EXTRA_INGREDIENTS_LIST);
                handleActionUpdataIngredients(ingredients);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdataIngredients(ArrayList<Ingredient> ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updataRecipeWidget(this, appWidgetManager,
                ingredients, appWidgetIds);
    }

}
