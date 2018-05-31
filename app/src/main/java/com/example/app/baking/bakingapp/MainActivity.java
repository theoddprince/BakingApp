package com.example.app.baking.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MasterListAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onRecipeSelected(int position , Recipe recipe) {
            Bundle b = new Bundle();
            b.putSerializable("Recipe", recipe);
            Intent myIntent = new Intent(this, RecipeIngredients.class);
            //Update the Widget when a recipe is been selected
            IngredientService.startActionUpdateIngredientWidgets(this , recipe);
            myIntent.putExtras(b);
            startActivity(myIntent);
    }
}
