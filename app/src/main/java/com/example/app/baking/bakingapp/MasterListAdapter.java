package com.example.app.baking.bakingapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MasterListAdapterViewHolder>  {

    // Keeps track of the context and list of images to display
    private Context mContext;
  //  private List<String> recepieCard;
    private ArrayList<Recipe> allRecipies;
    OnRecipeClickListener mCallback;

    @Override
    public MasterListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId;
        layoutId = R.layout.fragment_recipes;
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);

        return new MasterListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterListAdapterViewHolder holder, int position) {
        holder.recipeName.setText(allRecipies.get(position).getmName());

        //Suggestion fixed to read recipe image from the url using picasso
        //In case the url empty then load from the local pictuers
        if(TextUtils.isEmpty(allRecipies.get(position).getmImageURL())){
        switch (allRecipies.get(position).getmName()) {
            case "Nutella Pie":
                    holder.recipeImage.setImageResource(R.drawable.nutellapie);
                break;
            case "Brownies":
                holder.recipeImage.setImageResource(R.drawable.brownies);
                break;
            case "Yellow Cake":
                holder.recipeImage.setImageResource(R.drawable.yellowcake);
                break;
            case "Cheesecake":
                holder.recipeImage.setImageResource(R.drawable.cheesecake);
                break;
            default:
                holder.recipeImage.setImageResource(R.drawable.baking);
                break;
            }
        }
        else
            Picasso.with(mContext).load(allRecipies.get(position).getmImageURL()).into(holder.recipeImage);

    }


    public MasterListAdapter(Context context , ArrayList<Recipe> allRecipies) {
        mContext = context;
        this.allRecipies = allRecipies;
    }

    public void swapData(ArrayList<Recipe> allRecipies) {
        this.allRecipies = allRecipies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allRecipies == null)
            return 0;
        else
        return allRecipies.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

     class MasterListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView recipeName;
        final ImageView recipeImage;

        MasterListAdapterViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.recipe_text_view);
            recipeImage = view.findViewById(R.id.RecipeImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = allRecipies.get(adapterPosition);
            mCallback = (OnRecipeClickListener) mContext;
            mCallback.onRecipeSelected(adapterPosition , recipe);

        }
    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position , Recipe recipe);
    }
}
