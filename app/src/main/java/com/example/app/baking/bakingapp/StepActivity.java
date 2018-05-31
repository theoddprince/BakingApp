package com.example.app.baking.bakingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    Steps step;
    Bundle bundle = new Bundle();
    private ArrayList<Steps> allSteps;
    private boolean mTwoPane;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_step);
        mTwoPane = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try
        {
            bundle = getIntent().getExtras();
            step =  (Steps) bundle.getSerializable("Step");
            allSteps  = (ArrayList<Steps>) bundle.getSerializable("AllSteps");
            mTwoPane = (Boolean) bundle.getSerializable("towPane");
            position = (int) bundle.getSerializable("stepPosition");
        }
        catch(Throwable e)
        {e.printStackTrace();}

        if(savedInstanceState != null)
        {
            step = (Steps) savedInstanceState.getSerializable("Step");
            allSteps = (ArrayList<Steps>) savedInstanceState.getSerializable("AllSteps");
            mTwoPane = (Boolean) savedInstanceState.getSerializable("towPane");
            position = (int) savedInstanceState.getSerializable("stepPosition");
        }

        final Button nextButton = (Button) findViewById(R.id.btn_next);
        final Button prevButton = (Button) findViewById(R.id.btn_prev);


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;

                if(position >= 0)
                {
                    if (position == 0)
                        prevButton.setVisibility(View.INVISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    StepFragment stepNewFragment = new StepFragment();
                    stepNewFragment.setOneStep(allSteps.get(position));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.player_container, stepNewFragment)
                            .commit();
                }
                else
                {
                    prevButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position == -1)
                    position = 0;

                position++;
                if(position < allSteps.size())
                {
                    if(position == allSteps.size() -1)
                        nextButton.setVisibility(View.INVISIBLE);
                    else
                        nextButton.setVisibility(View.VISIBLE);

                    prevButton.setVisibility(View.VISIBLE);
                    StepFragment stepNewFragment = new StepFragment();
                    stepNewFragment.setOneStep(allSteps.get(position));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.player_container, stepNewFragment)
                            .commit();
                }
                else
                {
                    nextButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(position == allSteps.size() - 1)
        {
            nextButton.setVisibility(View.INVISIBLE);
            prevButton.setVisibility(View.VISIBLE);
        }else if(position == 0)
        {
            nextButton.setVisibility(View.VISIBLE);
            prevButton.setVisibility(View.INVISIBLE);
        }

        if(mTwoPane)
        {
            nextButton.setVisibility(View.INVISIBLE);
            prevButton.setVisibility(View.INVISIBLE);
        }

        if(savedInstanceState == null) {
            StepFragment stepNewFragment = new StepFragment();
            stepNewFragment.setOneStep(step);

            fragmentManager.beginTransaction()
                    .add(R.id.player_container, stepNewFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        //Must save the step data in case we rotate the screen
        currentState.putSerializable("Step" , step);
        currentState.putSerializable("AllSteps" , allSteps);
        currentState.putSerializable("towPane" , mTwoPane);
        currentState.putSerializable("stepPosition" , position);

    }


}
