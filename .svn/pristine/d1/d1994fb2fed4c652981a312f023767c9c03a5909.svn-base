package com.capgemini.vuzixscanner.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.capgemini.vuzixscanner.R;
import com.capgemini.vuzixscanner.service.BaseConnection;
import com.capgemini.vuzixscanner.service.GetOrderDetailsService;

import static java.net.HttpURLConnection.HTTP_OK;

public class SendDeliveryStatusTask extends AsyncTask<Void,Void,Void> {
    private Context mContext;
    private GetOrderDetailsService mService;
    private int statusCode ;

    public SendDeliveryStatusTask(Context context){
        mContext = context;
        mService = new GetOrderDetailsService(context,null);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for(int i = 0 ; i < 3 ;i++) {
                mService.makeRequest(mContext.getResources().getString(R.string.api_delivery_notification), null, BaseConnection.GET);
                if(mService.getStatusCode() == HTTP_OK){
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
