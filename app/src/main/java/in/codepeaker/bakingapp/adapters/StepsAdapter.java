package in.codepeaker.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.fragments.DetailFragment;
import in.codepeaker.bakingapp.model.BakingModel;

/**
 * Created by github.com/codepeaker on 20/12/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private Context context;
    private ArrayList<BakingModel.StepsBean> stepsBeans;
    DetailFragment.OnFragmentInteractionListener mListener;

    public StepsAdapter(Context context, ArrayList<BakingModel.StepsBean> stepsBeans, DetailFragment.OnFragmentInteractionListener mListener) {
        this.context = context;
        this.stepsBeans = stepsBeans;
        this.mListener = mListener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_view, parent, false));
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.stepsTextView.setText(stepsBeans.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsBeans.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        TextView stepsTextView;
        ImageView playImageview;

        StepsViewHolder(View itemView) {
            super(itemView);
            stepsTextView = itemView.findViewById(R.id.steps_text_id);
            playImageview = itemView.findViewById(R.id.play_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onFragmentInteraction(getAdapterPosition());

                }
            });
        }
    }
}
