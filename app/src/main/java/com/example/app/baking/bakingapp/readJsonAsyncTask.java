package com.example.app.baking.bakingapp;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class readJsonAsyncTask extends AsyncTask<String, Void, String> {

    Recipe recipe;
    ArrayList<Recipe> recipeArray = new ArrayList<>();
    private TaskCompleted mCallback;

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_QUANTITY = "quantity";
    private static final String RECIPE_MEASURE = "measure";
    private static final String RECIPE_INGREDIENT = "ingredient";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_STEP_ID = "id";
    private static final String RECIPE_STEP_SHORTDESC = "shortDescription";
    private static final String RECIPE_STEP_DESC = "description";
    private static final String RECIPE_STEP_VIDEOURL = "videoURL";
    private static final String RECIPE_STEP_THUMBNAIL = "thumbnailURL";

    public readJsonAsyncTask(TaskCompleted context){
        this.mCallback =  context;
    }

    @Override
    protected String doInBackground(String... strings) {

           String json = "UNDEFINED";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONArray bakingRecipiesArray = new JSONArray(builder.toString());
            Ingredients oneIng=null;
            Steps oneStep=null;

            for (int i = 0; i < bakingRecipiesArray.length(); i++) {

                ArrayList<Steps> recipeSteps = new ArrayList<>();
                ArrayList<Ingredients> ingredientsArray = new ArrayList<>();

                int recipe_id = bakingRecipiesArray.getJSONObject(i).getInt(RECIPE_ID) ;
                String recipe_name = bakingRecipiesArray.getJSONObject(i).optString(RECIPE_NAME) ;

                JSONArray ingredients = bakingRecipiesArray.getJSONObject(i).getJSONArray(RECIPE_INGREDIENTS);

                for (int j = 0; j < ingredients.length(); j++) {

                    double quantity = ingredients.getJSONObject(j).getDouble(RECIPE_QUANTITY);
                    String measure = ingredients.getJSONObject(j).getString(RECIPE_MEASURE);
                    String ingredient = ingredients.getJSONObject(j).getString(RECIPE_INGREDIENT);
                    oneIng = new Ingredients(quantity, measure,ingredient);
                    ingredientsArray.add(oneIng);

                }

                JSONArray steps = bakingRecipiesArray.getJSONObject(i).getJSONArray(RECIPE_STEPS);
                for (int j = 0; j < steps.length(); j++) {
                    int id = steps.getJSONObject(j).getInt(RECIPE_STEP_ID);
                    String shortDescription = steps.getJSONObject(j).getString(RECIPE_STEP_SHORTDESC);
                    String description = steps.getJSONObject(j).getString(RECIPE_STEP_DESC);
                    String videoURL = steps.getJSONObject(j).getString(RECIPE_STEP_VIDEOURL);
                    String thumbnailURL = steps.getJSONObject(j).getString(RECIPE_STEP_THUMBNAIL);

                    oneStep = new Steps(id, shortDescription,description,videoURL,thumbnailURL);

                    recipeSteps.add(oneStep);
                }
                recipe = new Recipe(recipe_id, recipe_name,ingredientsArray ,recipeSteps);
                recipeArray.add(recipe);
            }


            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return json;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mCallback.onTaskComplete(recipeArray);
    }

}
