package in.codepeaker.bakingapp.service;

/**
 * Created by github.com/codepeaker on 22/12/17.
 */

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import in.codepeaker.bakingapp.IngredientsWidgetProvider;
import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;
import in.codepeaker.bakingapp.model.ListItem;
import in.codepeaker.bakingapp.utils.AppUtils;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    RemoteViews remoteView;
    ArrayList<BakingModel.IngredientsBean> ingredientsBeans;
    private ArrayList<ListItem> listItemList = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;


    public WidgetAdapter(Context context, Intent intent) {
        this.context = context;


    }

    private void populateListItem(ArrayList<BakingModel.IngredientsBean> ingredientsBeans) {
        if (ingredientsBeans == null) {
            return;
        }
        listItemList = new ArrayList<>();
        for (int i = 0; i < ingredientsBeans.size(); i++) {
            ListItem listItem = new ListItem();

            listItem.ingredTool = (int) ingredientsBeans.get(i).getQuantity() + " " +
                    ingredientsBeans.get(i).getMeasure();
            listItem.ingredName = ingredientsBeans.get(i).getIngredient();
            listItemList.add(listItem);
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        populateListItem(IngredientsWidgetProvider.ingredientsBeans);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_view);
        ListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.ingredient_tool_id, listItem.ingredTool);
        remoteView.setTextViewText(R.id.ingredient_name_id, listItem.ingredName);

        Intent intent = new Intent();
        int reciperPosition = AppUtils.getIntpreferences(context, Constant.recipePosition);

        if (reciperPosition != -1) {
            intent.putExtra(Constant.fillInIntentRecipePosition, reciperPosition);
        } else {
            intent.putExtra(Constant.fillInIntentRecipePosition, 0);
        }



        intent.putExtra(Constant.fromWidget, true);
        remoteView.setOnClickFillInIntent(R.id.ingredient_name_id, intent);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}

