package com.capgemini.vuzixscanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.vuzix.speech.VoiceControl;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailActivity extends FragmentActivity {

    public static final String KEY_PRODUCT = "product";
    public static final String KEY_IMEI = "imei";
    public static final String KEY_OS = "os_version";


    private String jsonObject = "{'product':'Nexus','imei':'0987654321','os_version':'Lollipop 5.0'}" ;

    private TextView tv_product , tv_imei , tv_os_version ;
   // private MyVoiceControl mVoiceControl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Utility.setActionBar(DetailActivity.this);
        tv_product = (TextView)findViewById(R.id.product_name);
        tv_imei = (TextView)findViewById(R.id.imei);
        tv_os_version = (TextView)findViewById(R.id.os_version);

        if(getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra(DashBoardActivity.EXTRA_SCAN_RESULT))){
        jsonObject = getIntent().getStringExtra(DashBoardActivity.EXTRA_SCAN_RESULT);
            /*mVoiceControl = new MyVoiceControl(DetailActivity.this);
            if(mVoiceControl != null){

                mVoiceControl.addGrammar(Constants.GRAMMAR_BASIC);
                mVoiceControl.addGrammar(Constants.GRAMMAR_MEDIA);
                mVoiceControl.addGrammar(Constants.GRAMMAR_CAMERA);
                mVoiceControl.addGrammar(Constants.GRAMMAR_NAVIGATION);


            }*/

            try {
                JSONObject obj = new JSONObject(jsonObject);
                if(obj != null){
                    if(obj.has(KEY_PRODUCT)){
                        tv_product.setText(obj.getString(KEY_PRODUCT));
                    }
                    if(obj.has(KEY_IMEI)){
                        tv_imei.setText(obj.getString(KEY_IMEI));
                    }
                    if(obj.has(KEY_OS)){
                        tv_os_version.setText(obj.getString(KEY_OS));
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        }



    public class MyVoiceControl extends VoiceControl {

        public MyVoiceControl(Context context){
            super(context);
        }

        @Override
        protected void onRecognition(String s) {

            /*if(mVoiceControl != null && mVoiceControl.getConfScore() <0.35){
                return;
            }*/

            String moveLeft = "cut";


            if(s.trim().equalsIgnoreCase(moveLeft.trim())){

                finish();

            }

            Log.e("KK", "speech " + s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

/*if(mVoiceControl != null){
    mVoiceControl.destroy();
}*/

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if(mVoiceControl != null){
            mVoiceControl.on();
        }*/
    }


    @Override
    protected void onPause() {
        super.onPause();
        /*if(mVoiceControl != null){
            mVoiceControl.off();
        }*/
    }
}
