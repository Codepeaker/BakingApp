package in.codepeaker.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import in.codepeaker.bakingapp.activities.DetailActivity;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;
import in.codepeaker.bakingapp.service.WidgetRemoteViewService;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static ArrayList<BakingModel.IngredientsBean> ingredientsBeans;

    private static void updateWidgetListView(Context context,
                                             AppWidgetManager appWidgetManager, int appWidgetId, ArrayList<BakingModel.IngredientsBean> data, String recipename) {


        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.ingredients_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetRemoteViewService.class);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BakingModel.IngredientsBean>>() {
        }.getType();
        String json = gson.toJson(data, type);
        if (data != null) {
            ingredientsBeans = data;
            svcIntent.putExtra(Constant.ingredientsString, json);
            remoteViews.setViewVisibility(R.id.recipe_name_id, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.listViewWidget, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.empty_view, View.GONE);
        } else {
            remoteViews.setViewVisibility(R.id.recipe_name_id, View.GONE);
            remoteViews.setViewVisibility(R.id.listViewWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.empty_view, View.VISIBLE);
            return;
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listViewWidget);
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);

        Intent appIntent = new Intent(context, DetailActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listViewWidget, appPendingIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        remoteViews.setTextViewText(R.id.recipe_name_id, recipename);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    public static void updateWidgetListViews(Context context, AppWidgetManager appWidgetManager,
                                             int[] appWidgetIds, ArrayList<BakingModel.IngredientsBean> data, String recipename) {
        for (int appWidgetId : appWidgetIds) {
            updateWidgetListView(context, appWidgetManager, appWidgetId,
                    data,
                    recipename);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateWidgetListView(context, appWidgetManager, appWidgetId, null, "");
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

