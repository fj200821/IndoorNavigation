package com.capgemini.vuzixscanner.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.capgemini.vuzixscanner.DashBoardActivity;
import com.capgemini.vuzixscanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by sphansek on 6/14/2017.
 */

public final class ServiceUtils  {
    private static final String LOG_TAG = ServiceUtils.class.getSimpleName();

    public static void checkForResponseError(final JSONObject responseObj) throws JSONException, Exception{
        if (responseObj != null && (responseObj.has(Constants.SUCCESS) && !responseObj.getBoolean(Constants.SUCCESS) || responseObj.has(Constants.ERRORS))) {
            final String error = getParsedErrorFromSerivce(responseObj);
            if (TextUtils.isEmpty(error)){
                throw new Exception(DashBoardActivity.getApplicationEx().getResources().getString(R.string.general_exception_occured));
            }
            else{
                throw new Exception(error);
            }
        }
    }

    public static String getParsedErrorFromSerivce(final JSONObject responseJsonObj) throws JSONException, Exception {
        if (responseJsonObj.has(Constants.ERRORS)) {
            final JSONObject errorJSON = responseJsonObj.optJSONObject(Constants.ERRORS);
            if(errorJSON != null){
                if (errorJSON.has(Constants.ERROR_REASON)) {
                    if (errorJSON.has(Constants.CODE)) {
                        if (errorJSON.has(Constants.ERROR_REASON)) {
                            return errorJSON.getString(Constants.ERROR_REASON);
                        }
                    } else {
                        if (errorJSON.has(Constants.ERROR_DETAILS)) {
                            return errorJSON.getString(Constants.ERROR_DETAILS);
                        }
                    }
                } else {
                    @SuppressWarnings("rawtypes")
                    final Iterator iterator = errorJSON.keys();
                    while (iterator.hasNext()) {
                        final String key = (String) iterator.next();
                        final String value = errorJSON.optString(key);
                        return value;
                    }
                }
            }
            else{
                final JSONArray errorArray = responseJsonObj.optJSONArray(Constants.ERRORS);
                if(errorArray != null){
                    final JSONObject errorObj = errorArray.getJSONObject(0);
                    String errorText = Constants.Symbol.EMPTY;
                    if(errorObj.has(Constants.CODE)){
                        final String code = errorObj.getString(Constants.CODE);
                        if(!TextUtils.isEmpty(code) && code.equalsIgnoreCase(Constants.IntText.ZERO)){
//                            DashBoardActivity.setExpired(true);
                        }
                    }
                    if(errorObj.has(Constants.ERROR_DETAILS) && !TextUtils.isEmpty(errorObj.getString(Constants.ERROR_DETAILS))){
                        errorText = errorObj.getString(Constants.ERROR_DETAILS);
                    }
                    else if(errorObj.has(Constants.ERROR_REASON)){
                        errorText = errorObj.getString(Constants.ERROR_REASON);
                    }
                    return errorText;
                }
            }
        }
        return null;
    }

    public static String readResponseFromAssetsFile(final Context context, final String filename, final boolean isEncrypted) {
        String response = null;
        try {
            final InputStream inputStream = context.getAssets().open(filename);
            final int size = inputStream.available();
            final byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            if(isEncrypted){
                final SecurityUtils securityUtils = new SecurityUtils();
                response = new String(securityUtils.decrypt(new String(buffer,"UTF-8")));
            }
            else{
                response = new String(buffer,"UTF-8");
            }

        }catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return response;
    }
}
