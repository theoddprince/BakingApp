package com.example.app.baking.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static Recipe recipe;

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe oneRecipe ,int appWidgetId) {

        recipe = oneRecipe;

         //In case we added the widget but still didn't open the app , as a default get the first recipe from the list
         // and assign it to be presented .
        if(recipe == null)
        {
            recipe = (Recipe.getAllRecipies(context)).get(0);
        }

        if(recipe != null) {
            Intent intent = new Intent(context, IngredientViewService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            views.setRemoteAdapter(R.id.list_view_widget, intent);
            ComponentName component = new ComponentName(context, RecipeWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view_widget);
            appWidgetManager.updateAppWidget(component, views);
        }
        }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        IngredientService.startActionUpdateIngredientWidgets(context,recipe);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                          Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
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

