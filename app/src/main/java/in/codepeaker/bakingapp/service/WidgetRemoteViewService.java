package in.codepeaker.bakingapp.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by github.com/codepeaker on 22/12/17.
 */

public class WidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("asdas", "asdasdasd");
        return (new WidgetAdapter(this.getApplicationContext(), intent));
    }
}
