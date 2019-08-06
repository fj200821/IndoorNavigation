package com.capgemini.vuzixscanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocationManager;


/**
 * Created by apple on 10/08/16.
 */
public class Utility {

    public static void setActionBar(FragmentActivity activity){
        ActionBar actionBar = activity.getActionBar() ;
        // Set up Gesture Sensor
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View view = activity.getLayoutInflater().inflate(R.layout.action_bar_layout,null);
        actionBar.setCustomView(view,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,activity.getResources().getDisplayMetrics())));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.show();
    }


    public static void showToast(Context context , String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(30);
        toast.show();
    }


    public static  boolean isCameraAvailable(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


     /* Shares the trace ID of the client. Trace ID can be used under certain conditions by
     * IndoorAtlas to provide detailed support.
     */
    public static void shareTraceId(View view, final Context context,
                                    final IALocationManager manager) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                shareText(context, manager.getExtraInfo().traceId, "traceId");
                return true;
            }
        });
    }

    /**
     * Use the share tool to share text via Slack, email, WhatsApp etc.
     */
    public static void shareText(Context context, String text, String title) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        context.startActivity(Intent.createChooser(sendIntent, title));
    }

    /**
     * Shows a {@link } with defined text
     */
    public static void showInfo(Activity activity, String text) {
      Toast.makeText(activity, text,
                Toast.LENGTH_SHORT).show();

    }
}
