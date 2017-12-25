package in.codepeaker.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import in.codepeaker.bakingapp.IngredientsWidgetProvider;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;

/**
 * Created by github.com/codepeaker on 23/12/17.
 */

public class WidgetUpdateService extends IntentService {

    public static final String UpdateWidget = "updateWidget";


    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startActionIngredWidget(Context context,
                                               ArrayList<BakingModel.IngredientsBean> ingredientsBeans,
                                               String recipename) {

        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(Constant.ingredients, ingredientsBeans);
        intent.putExtra(Constant.recipeName, recipename);
        context.startService(intent);
        ;

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        Bundle data = intent.getExtras();

        String recipename =(String) intent.getExtras().get(Constant.recipeName);

        ArrayList<BakingModel.IngredientsBean> ingredientsBeans = data.getParcelableArrayList(Constant.ingredients);

        IngredientsWidgetProvider.updateWidgetListViews(this, appWidgetManager,
                appWidgetIds, ingredientsBeans,
                recipename);


    }


}
