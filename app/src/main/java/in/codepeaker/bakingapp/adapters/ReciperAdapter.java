package in.codepeaker.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.activities.DetailActivity;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;

/**
 * Created by github.com/codepeaker on 18/12/17.
 */

public class ReciperAdapter extends RecyclerView.Adapter<ReciperAdapter.RecipeViewHolder> {
    private Context context;
    private List<BakingModel> bakingModels;

    public ReciperAdapter(Context context, List<BakingModel> bakingModels) {
        this.context = context;
        this.bakingModels = bakingModels;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.recipeName.setText(bakingModels.get(position).getName());
        holder.noOfIngred.setText(
                String.format(Locale.ENGLISH, "%d ingredients",
                        bakingModels.get(position).getIngredients().size()));
        holder.noOfSteps.setText(
                String.format(Locale.ENGLISH, "%d step to prepare",
                        bakingModels.get(position).getSteps().size()));
        holder.noOfServing.setText(
                String.format(Locale.ENGLISH, "%d servings",
                        bakingModels.get(position).getServings()));
    }

    @Override
    public int getItemCount() {
        return bakingModels.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView noOfIngred;
        TextView noOfSteps;
        TextView noOfServing;

        RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.dish_name);
            noOfIngred = itemView.findViewById(R.id.cart_text);
            noOfSteps = itemView.findViewById(R.id.steps_text);
            noOfServing = itemView.findViewById(R.id.serving_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    ArrayList<BakingModel.IngredientsBean> ingredientsBeans =
                            bakingModels.get(getAdapterPosition()).getIngredients();
                    ArrayList<BakingModel.StepsBean> stepsBeans =
                            bakingModels.get(getAdapterPosition()).getSteps();
                    intent.putExtra(Constant.recipeName, recipeName.getText().toString());
                    intent.putExtra(Constant.ingredients, ingredientsBeans);
                    intent.putExtra(Constant.stepsBean, stepsBeans);
                    intent.putExtra(Constant.recipePosition,getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
}
