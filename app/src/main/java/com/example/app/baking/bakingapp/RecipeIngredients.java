package com.example.app.baking.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredients extends AppCompatActivity implements StepsAdapter.OnStepClickListener {

    private RecyclerView mRecyclerView;
    Bundle bundle = new Bundle();
    Recipe recipe;
    private static final String BULLET_SYMBOL = "&#8226";


    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);

        try
        {
            bundle = getIntent().getExtras();
            recipe =  (Recipe) bundle.getSerializable("Recipe");
        }
        catch(Throwable e)
        {e.printStackTrace();}

        if(recipe != null){

            setTitle(recipe.getmName());

         // Put all ingredients in the same text organized each ingredient followed by a bullet_symbol
        StringBuilder sb = new StringBuilder();
        ArrayList<Ingredients> ings = recipe.getRecipeIngredients();
        for(int i=0; i<ings.size(); i++) {
            Spanned formattedItem = Html.fromHtml(BULLET_SYMBOL);
            sb.append(formattedItem);
            sb.append(" ");
            sb.append(ings.get(i).quantity);
            sb.append(" ");
            sb.append(ings.get(i).measure);
            sb.append(" of ");
            sb.append(ings.get(i).ingredient);
            sb.append("\n");
        }

        TextView ingText = findViewById(R.id.ing_text_view);
        ingText.setText(sb.toString());

        mRecyclerView = findViewById(R.id.recyclerview_steps);
        LinearLayoutManager linearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearManager);
        StepsAdapter stepsAdapter = new StepsAdapter(this, recipe.getRecipeSteps());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(stepsAdapter);

        //Notice that I have used this method before your suggestion , Mentioned that its another method for identifying tablets at MasterListFragment
            //I just kept both ways in order not to forget .

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        //if(findViewById(R.id.media_linear_layout) != null) {
        if(tabletSize){
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            if(savedInstanceState == null) {
                // In two-pane mode
                FragmentManager fragmentManager = getSupportFragmentManager();

                StepFragment stepFragment = new StepFragment();
                stepFragment.setOneStep(recipe.getRecipeSteps().get(0));
                fragmentManager.beginTransaction()
                        .add(R.id.player_container , stepFragment)
                        .commit();
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }
        }

    }

    @Override
    public void onStepSelected(int position, Steps step , ArrayList<Steps> steps) {

        if(mTwoPane)
        {
            StepFragment stepNewFragment = new StepFragment();
            stepNewFragment.setOneStep(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_container, stepNewFragment)
                    .commit();
        }
        else
        {
            Bundle b = new Bundle();
            b.putSerializable("Step", step);
            b.putSerializable("AllSteps",steps);
            b.putInt("stepPosition" , position);
            b.putBoolean("towPane" , mTwoPane);
            Intent myIntent = new Intent(this, StepActivity.class);
            myIntent.putExtras(b);
            startActivity(myIntent);
        }
    }


}
