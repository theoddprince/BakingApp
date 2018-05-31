package com.example.app.baking.bakingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.JsonReader;
import android.widget.Toast;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


public class Recipe implements Serializable {

    public Recipe() {
    }

    public int mRecipeID;
    public String mName;
    public String mSteps;
    public String mServings;
    public String mImageURL;
    public ArrayList<Ingredients> recipeIngredients = new ArrayList<>();
    public ArrayList<Steps> recipeSteps = new ArrayList<>();

    public ArrayList<Steps> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<Steps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public ArrayList<Ingredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<Ingredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public int getmRecipeID() {
        return mRecipeID;
    }

    public void setmRecipeID(int mRecipeID) {
        this.mRecipeID = mRecipeID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSteps() {
        return mSteps;
    }

    public void setmSpets(String mSpets) {
        this.mSteps = mSpets;
    }

    public String getmServings() {
        return mServings;
    }

    public void setmServings(String mServings) {
        this.mServings = mServings;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public Recipe(int mRecipeID, String mName ,  ArrayList<Ingredients> recipeIngredients , ArrayList<Steps> recipeSteps) {
        this.mRecipeID = mRecipeID;
        this.mName = mName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    public static JsonReader readJSONFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String uri = null;

        try {
            for (String asset : assetManager.list("")) {
                if (asset.endsWith("baking.json")) {
                    uri = "asset:///" + asset;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.recipe_list_load_error, Toast.LENGTH_LONG)
                    .show();
        }

        String userAgent = Util.getUserAgent(context, "ClassRecipe");
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        } finally {
            Util.closeQuietly(dataSource);
        }

        return reader;
    }


    public static ArrayList<String> getAllRecipeNames(Context context){
        JsonReader reader;
        ArrayList<String> recipeName = new ArrayList<>();
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                recipeName.add(readEntry(reader).getmName());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeName;
    }

    public static ArrayList<Recipe> getAllRecipies(Context context){
        JsonReader reader;
        ArrayList<Recipe> recipeArray = new ArrayList<>();
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                recipeArray.add(readEntry(reader));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeArray;
    }

    public static ArrayList<Recipe> getRecipieById(Context context,int id){
        JsonReader reader;
        ArrayList<Recipe> recipeArray = new ArrayList<>();
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                if(readEntry(reader).getmRecipeID() == id)
                    recipeArray.add(readEntry(reader));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeArray;
    }


    private static Recipe readEntry(JsonReader reader) {
        Integer id = -1;
        String rname = null;
        double quantity = 0;
        String measure = null;
        String ingredient = null;
        String shortDescription = null;
        String description = null;
        String videoURL = null;
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ArrayList<Steps> steps = new ArrayList<>();

        //Mentor Suggestion
        String thumbnailURL = null;


        try { reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "id":
                        id = reader.nextInt();
                        break;
                    case "name":
                        rname = reader.nextString();
                        break;
                    case "ingredients":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String nameIng = reader.nextName();
                                switch (nameIng)
                                {
                                    case "quantity" :
                                        quantity = reader.nextDouble();
                                        break;
                                    case  "measure" :
                                        measure = reader.nextString();
                                        break;
                                    case "ingredient" :
                                        ingredient = reader.nextString();
                                        break;
                                    default:
                                        reader.skipValue();
                                        break;
                                }
                            }
                            Ingredients oneIng = new Ingredients(quantity, measure,ingredient);
                            ingredients.add(oneIng);
                            reader.endObject();
                        }

                        reader.endArray();
                        break;
                    case "steps":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String nameIng = reader.nextName();
                                switch (nameIng)
                                {
                                    case "id" :
                                        id = reader.nextInt();
                                        break;
                                    case  "shortDescription" :
                                        shortDescription = reader.nextString();
                                        break;
                                    case "description" :
                                        description = reader.nextString();
                                        break;
                                    case "videoURL":
                                        videoURL = reader.nextString();
                                        break;
                                    case "thumbnailURL":
                                        thumbnailURL = reader.nextString();
                                        break;
                                    default:
                                        reader.skipValue();
                                        break;
                                }
                            }
                            Steps oneStep = new Steps(id, shortDescription,description,videoURL,thumbnailURL);
                            steps.add(oneStep);
                            reader.endObject();
                        }

                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Recipe(id, rname,ingredients ,steps);
    }


}
