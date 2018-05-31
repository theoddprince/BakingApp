package com.example.app.baking.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class IngredientService extends IntentService {

    public static final String ACTION_CHANGE_INGREDIENTS = "com.example.app.baking.bakingapp.action.change_ingrediens";
    public static final String ACTION_CHANGE_INGREDIENTS_WIDGET = "com.example.app.baking.bakingapp.action.change_ingrediens_widget";
    ArrayList<Recipe> recipe;
    Recipe oneRecipe ;

    public IngredientService() {
        super("IngredientService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHANGE_INGREDIENTS.equals(action)) {
                handleActionChangeIngredients();
            }else if(ACTION_CHANGE_INGREDIENTS_WIDGET.equals(action))
            {
                Bundle bundle;
                bundle = intent.getExtras();
                oneRecipe =  (Recipe) bundle.getSerializable("RecipeService");

                handleActionUpdateIngredientsWidgets(oneRecipe);
            }
        }
    }

    private void handleActionChangeIngredients()
    {
       recipe =  Recipe.getAllRecipies(this);
    }

    public static void startActionUpdateIngredientWidgets(Context context , Recipe recipe) {
        Bundle b = new Bundle();
        b.putSerializable("RecipeService" , recipe);
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_CHANGE_INGREDIENTS_WIDGET);
        intent.putExtras(b);
        context.startService(intent);
    }


    private void handleActionUpdateIngredientsWidgets(Recipe recipe) {

         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        //Now update all widgets
        RecipeWidgetProvider.updateIngredientWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
