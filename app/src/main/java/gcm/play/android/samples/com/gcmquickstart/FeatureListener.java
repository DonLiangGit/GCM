package gcm.play.android.samples.com.gcmquickstart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Wajih Sid on 7/4/2015.
 */
public class FeatureListener extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String message = intent.getExtras().getString("message");


    }
}
