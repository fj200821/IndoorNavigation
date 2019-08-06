package com.capgemini.vuzixscanner;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by apple on 06/09/16.
 */
public class PreferenceUtils
{
    private SharedPreferences prefs ;
    private SharedPreferences.Editor editor ;
    public static final String PREFS_KEY_APP = "vuzix";
    public static final String PREFS_KEY_PRODUCT = "product";

    public PreferenceUtils(Context context){
        prefs = context.getSharedPreferences(PREFS_KEY_APP,Context.MODE_PRIVATE);
        editor = prefs.edit() ;
    }

    public void setCheckedProduct(int pos){
        editor.putBoolean(PREFS_KEY_PRODUCT+pos,true);
        editor.commit();
    }

    public boolean getProduct1State(int i){
        return prefs.getBoolean(PREFS_KEY_PRODUCT+""+i,false);
    }

    public boolean getProduct2State(){
        return prefs.getBoolean(PREFS_KEY_PRODUCT+""+2,false);
    }

    public boolean getProduct3State(){
        return prefs.getBoolean(PREFS_KEY_PRODUCT+""+3,false);
    }

    public void clearAllPrefs(){
        editor.clear();
        editor.commit();
    }

}
