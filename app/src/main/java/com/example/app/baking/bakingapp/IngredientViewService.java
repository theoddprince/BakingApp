package com.example.app.baking.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class IngredientViewService extends RemoteViewsService{

    public IngredientViewFactory onGetViewFactory(Intent intent)
    {
        return new IngredientViewFactory(this.getApplicationContext());
    }
}

class IngredientViewFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private Recipe recipe;
    private ArrayList<Ingredients> ingredients;

    public IngredientViewFactory(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.recipe = RecipeWidgetProvider.recipe;
        ingredients = recipe.recipeIngredients;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_text_view_widget_layout);
       // views.setTextViewText(R.id.text_view_recipe_widget, mIngredients[position].getmIngredient());
        String st = ingredients.get(position).getIngredient();
        views.setTextViewText(R.id.text_view_ingredient_widget, st);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
