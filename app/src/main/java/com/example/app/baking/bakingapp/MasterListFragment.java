package com.example.app.baking.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MasterListFragment extends Fragment {
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    private RecyclerView mRecyclerView;

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //That is another method to identify if we are using a tablet or phone.
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerview_recipe);
        GridLayoutManager gridManager;
        if (tabletSize) {
            gridManager =
                    new GridLayoutManager(getActivity(), 3);
        } else {
            gridManager =
                    new GridLayoutManager(getActivity(), 1);
        }
        mRecyclerView.setLayoutManager(gridManager);
        mRecyclerView.setHasFixedSize(true);

        final MasterListAdapter mAdapter = new MasterListAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);

        //Required Fix to read the Json Data from the network.
        //There was some misunderstanding between me and the mentor , I have asked him about reading the file locally , but the images and videos from the internet

        readJsonAsyncTask testAsyncTask = new readJsonAsyncTask(new TaskCompleted() {
            @Override
            public void onTaskComplete(ArrayList<Recipe> recipeArray) {
                mAdapter.swapData(recipeArray);
            }
        });

        testAsyncTask.execute(getContext().getString(R.string.BAKING_URL));

        // Return the root view
        return rootView;
    }

}
