package com.example.app.baking.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter  extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>  {
    private Context mContext;
    private ArrayList<Steps> steps;
    OnStepClickListener mCallback;

    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId;
        layoutId = R.layout.layout_step;
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);

        return new StepsAdapter.StepsAdapterViewHolder(view);
    }

    public StepsAdapter(Context context , ArrayList<Steps> steps) {
        mContext = context;
        this.steps = steps;
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsAdapterViewHolder holder, int position) {
        holder.stepDesc.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView stepDesc;
        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            stepDesc = itemView.findViewById(R.id.step_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Steps step = steps.get(adapterPosition);
            mCallback = (OnStepClickListener) mContext;
            mCallback.onStepSelected(adapterPosition , step, steps);
        }
    }

    public interface OnStepClickListener {
        void onStepSelected(int position , Steps step , ArrayList<Steps> allSteps);
    }
}
