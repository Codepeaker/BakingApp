package in.codepeaker.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.model.BakingModel;

/**
 * Created by github.com/codepeaker on 20/12/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private Context context;
    private List<BakingModel.IngredientsBean> ingredientsBeans;

    public IngredientAdapter(Context context, List<BakingModel.IngredientsBean> ingredientsBeans) {
        this.context = context;
        this.ingredientsBeans = ingredientsBeans;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_view, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingredientToolsTextView.setText(
                String.format(Locale.ENGLISH,"%d %s", (int)ingredientsBeans.get(position).getQuantity(), ingredientsBeans.get(position).getMeasure()));

        holder.ingredientNameTextView.setText(ingredientsBeans.get(position)
                .getIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredientsBeans.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientToolsTextView;
        TextView ingredientNameTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientToolsTextView = itemView.findViewById(R.id.ingredient_tool_id);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name_id);
        }
    }
}
