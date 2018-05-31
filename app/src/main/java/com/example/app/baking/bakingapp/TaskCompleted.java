package com.example.app.baking.bakingapp;

import java.util.ArrayList;

public interface TaskCompleted {
    // Define data you like to return from AysncTask
    void onTaskComplete(ArrayList<Recipe> recipeArray);
}