package com.capgemini.vuzixscanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.capgemini.vuzixscanner.adapters.ProductListAdapter;
import com.capgemini.vuzixscanner.entity.OrderDetails;
import com.capgemini.vuzixscanner.model.ProductModel;
import com.capgemini.vuzixscanner.service.GetOrderDetailsService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import com.vuzix.hardware.GestureSensor;

//import com.vuzix.speech.Constants;
//import com.vuzix.speech.VoiceControl;

public class DashBoardActivity extends FragmentActivity implements RecognitionListener {

    private static final String LOG_TAG = DashBoardActivity.class.getSimpleName();
    private static Context applicationEx;

    //    private MyGestureSensor mGS;
//    private Canvas mCanvas;
//    private Bitmap mBG;
    public static final String EXTRA_SCAN_RESULT = "scan";
    private int mX;
    private int mY;
    private int mSize;

    public static final int ZBAR_SCANNER_REQUEST = 0;
    public static final int ZBAR_QR_SCANNER_REQUEST = 1;
    private String BROADCAST_ACTION = "Action";
    private TextView txtResult;

    //    private MyVoiceControl mVoiceControl;
    private SpeechRecognizer mRecognizer;
    private String productName;
    private ListView lv_products;
    private ArrayList<ProductModel> productList;
    private ProductListAdapter mAdapter;
    private View mHeaderView;
    private Button barcodeButton, QrCodeButton, startButton;
    private PreferenceUtils preferenceUtils;

    private static ThreadPoolExecutor operationsQueue;
    private static final int CORE_POOL_SIZE = 1;//6;
    private static final int MAXIMUM_POOL_SIZE = 1;//6;

    private static boolean isSSL;
    private static boolean RUN_LOCAL_CODE ;
    private GetOrderDetailsService getOrderDetailsService;
    private ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
    private long delay = (3 * 1000);
    private Timer timer = null;
    public static  final String BACK = "back";
    public static  final String OK = "confirm";
    public static  final String CANCEL = "cancel";
    public static  final String GO_ONLINE = "go_online";
    public static  final String GO_OFFLINE = "go_offline";
   // private VuzixSpeechClient mSpeechClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationEx = getApplicationContext();
        isSSL = false;
        RUN_LOCAL_CODE = false;
        /*try {
            mSpeechClient = new VuzixSpeechClient(DashBoardActivity.this);
            mSpeechClient.deletePhrase("*");
           // mSpeechClient.insertPhrase("OK",OK);
           // mSpeechClient.insertPhrase("Cancel",CANCEL);
           // mSpeechClient.insertPhrase("Go back",BACK);
           // mSpeechClient.insertPhrase("Exit",BACK);
            mSpeechClient.insertPhrase("Go Online",GO_ONLINE);
            mSpeechClient.insertPhrase("Go Offline",GO_OFFLINE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        registerReceiver(mReceiver,new IntentFilter(VuzixSpeechClient.ACTION_VOICE_COMMAND));*/
       initialWork();
//        mVoiceControl = new MyVoiceControl(DashBoardActivity.this);
        Log.i("DashBoardActivity", "--------------------------------------5");
        txtResult = (TextView) findViewById(R.id.txt_result);
//        if (mVoiceControl != null) {
//            Log.i("DashBoardActivity", "--------------------------------------6");
//            mVoiceControl.addGrammar(Constants.GRAMMAR_BASIC);
//            mVoiceControl.addGrammar(Constants.GRAMMAR_MEDIA);
//            mVoiceControl.addGrammar(Constants.GRAMMAR_CAMERA);
//            mVoiceControl.addGrammar(Constants.GRAMMAR_NAVIGATION);
//            Log.i("DashBoardActivity", "--------------------------------------7");
//        }
        Log.i("DashBoardActivity", "--------------------------------------8");
//        mRecognizer = SpeechRecognizer.createSpeechRecognizer(DashBoardActivity.this) ;
//        mRecognizer.setRecognitionListener(this);
        Log.i("DashBoardActivity", "--------------------------------------9");
//        if (mGS == null) {
//            android.util.Log.i("VUZIX-Gesture", "Unable to create MyGestureSensor()");
//            return;
////        }else {
////            mGS.register();
//        }
        Log.i("DashBoardActivity", "--------------------------------------10");
        // Set default values for circle
        mX = 215;
        mY = 100;
        mSize = 40;

        // Set up drawing canvas
        // mBG = Bitmap.createBitmap(432,200,Bitmap.Config.ARGB_8888);
        // mCanvas = new Canvas(mBG);

        // Draw initial circle
        // drawShape(Color.BLUE);


        startButton = (Button) findViewById(R.id.btn_start);
        startButton.setText(R.string.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                launchNavigation("");
            }
        });

        barcodeButton = (Button) findViewById(R.id.btn_scan);
        barcodeButton.setText(R.string.scan_barcode);
        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                launchScanner();
            }
        });

        QrCodeButton = (Button) findViewById(R.id.btn_scan_qr);
        QrCodeButton.setText(R.string.scan_qr);
        QrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                launchQRScanner();
            }
        });
    }

    public void initialWork(){
        orderDetailsList.clear();
        getOrderDetailsService = new GetOrderDetailsService(DashBoardActivity.this,null);

        setContentView(R.layout.activity_main);
        Log.i("DashBoardActivity", "--------------------------------------1");
        //Utility.setActionBar(DashBoardActivity.this);
        Log.i("DashBoardActivity", "--------------------------------------2");
        Log.i("KK", "dashboard product " + productName);
        // setList();
//        mGS = new MyGestureSensor(this);
        Log.i("DashBoardActivity", "--------------------------------------3");
        preferenceUtils = new PreferenceUtils(DashBoardActivity.this);
        Log.i("DashBoardActivity", "--------------------------------------4");
        preferenceUtils.clearAllPrefs();
    }

    @Override
    public void onDestroy() {
        // If we have an active Gesture Recognizer object, call
        // unregister() just to be safe (stop receiving gesture events)
//        if (mGS != null) {
//            mGS.unregister();
//        }
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        Log.i("KK", "dashboard destroy");

//        if (mVoiceControl != null) {
//            mVoiceControl.off();
//        }
        //unregisterReceiver(mReceiver);
        preferenceUtils.clearAllPrefs();

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("KK", "dashboard stop");
//        if (mVoiceControl != null) {
//            mVoiceControl.off();
//        }
//        mRecognizer.stopListening();
    }

    @Override
    public void onPause() {
        // If we have an active Gesture Recognizer object, call
        // unregister() just to be  safe (stop receiving gesture events)
//        if (mGS != null) {
//            android.util.Log.i("VUZIX-Gesture", "unregistering MyGestureSensor");
//            mGS.unregister();
//        }
        Log.i("KK", "dashboard pause");
//        if(mVoiceControl != null){
//            mVoiceControl.off();
//        }
        super.onPause();
    }



    @Override
    protected void onStart() {
        super.onStart();
        /*GetServiceTask getServiceTask = new GetServiceTask();
        getServiceTask.execute();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),delay,
                pendingIntent);*/

        setTimer();
        Log.i("KK", "dashboard start");

    }

    public void setTimer(){
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        if(!isRunLocalCode()) {

            if (timer == null) {
                timer = new Timer();
            }
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    GetServiceTask getServiceTask1 = new GetServiceTask();
                    getServiceTask1.execute();
                }
            }, 0, delay);
        }else{
            GetOrderDetailsService getOrderDetailsService = new GetOrderDetailsService(DashBoardActivity.this,mGetOrderListener);
            DashBoardActivity.getOperationsQueue().execute(getOrderDetailsService);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("KK", "dashboard resume");
        // If we have an active Gesture Recognizer object, call
        // register() to begin receiving gesture events again
//        if (mGS != null) {
//            mGS.register();
//        }`
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shape_pusher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        Log.e("KK", "on result " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

        String[] keywords = new String[]{"back", "next", "0", "1", "start", "exit", "end"};
        ArrayList<String> recData = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String getData = new String();

        for (String s : recData) {
            getData += s + ",";
        }

        // Show filtered keyword
        String result = "";
        for (String s : recData) {
            for (String t : keywords) {
                if (s.trim().equalsIgnoreCase(t.trim())) {
                    result = t;
                    break;
                }
            }
            if (!result.isEmpty()) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_scan:
//                launchScanner();
//                break;
//            case R.id.btn_scan_qr:
//                launchQRScanner();
//                break;
//            case R.id.btn_start:
//                launchNavigation("");
//                break;
//        }
//
//    }

    // draw a shape.   Takes an instance of ShapeInfo and a color.
    // We could pull the color from the ShapeInfo structure except in the
    // case where we are calling drawShape() to ERASE the shape.
  /*  private void drawShape(int color)
    {
        Paint paint = new Paint();
        paint.setColor(color);

        mCanvas.drawCircle(mX, mY, mSize, paint);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rect);
        linearLayout.setBackgroundDrawable(new BitmapDrawable(mBG));
    }*/

    // Here is the nested Gesture Sensor class used to retrieve
    // events from the Gesture Sensor
//    public class MyGestureSensor extends GestureSensor {
//        public MyGestureSensor(android.content.Context mContext) {
//            super(mContext);
//        }
//
//        // Gesture Callbacks
//        @Override
//        protected void onBackSwipe(int speed) {
//            android.util.Log.i("VUZIX-Gesture", "onBackSwipe(), speed=" + speed);
//
//            // if(speed >= 1){
//            launchQRScanner();
//            //}
//        }
//
//        @Override
//        protected void onForwardSwipe(int speed) {
//            android.util.Log.i("VUZIX-Gesture", "onForwardSwipe(), speed=" + speed);
//
//            // if(speed >= 1){
//            launchScanner();
//            // }
//
//        }
//
//        @Override
//        protected void onNear() {
//            android.util.Log.i("VUZIX-Gesture", "onNear()");
//
//        }
//
//        @Override
//        protected void onFar() {
//            android.util.Log.i("VUZIX-Gesture", "onFar()");
//
//
//        }
//
//        @Override
//        protected void onDown(int speed) {
//            // on Down() must be overridden, but the gesture is not enabled
//            android.util.Log.i("VUZIX-Gesture", "onDown(), speed=" + speed);
//        }
//
//        @Override
//        protected void onUp(int speed) {
//            // onUp() must be overridden, but the gesture is not enabled.
//            android.util.Log.i("VUZIX-Gesture", "onUp(), speed=" + speed);
//        }
//    }


    public void launchScanner() {
        if (Utility.isCameraAvailable(DashBoardActivity.this)) {
            //Intent intent = new Intent(DashBoardActivity.this, ZBarScannerActivity.class);
            //startActivityForResult(intent, ZBAR_SCANNER_REQUEST);


            Intent intent = new Intent("com.google.zxing.client.android.CGCUSTOMSCAN");
            //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);

        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    public void launchQRScanner() {


        if (Utility.isCameraAvailable(DashBoardActivity.this)) {
//            Intent intent = new Intent(DashBoardActivity.this, ZBarScannerActivity.class);
//            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
//            startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);

            Intent intent = new Intent("com.google.zxing.client.android.CGCUSTOMSCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);

        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = "";
        String contents = "";
        String format = "";
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
                contents = data.getStringExtra("SCAN_RESULT");
                format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, "Scan Result = " + data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();

                Intent navigateIntent = new Intent(DashBoardActivity.this, DetailActivity.class);
                navigateIntent.putExtra(EXTRA_SCAN_RESULT, "");
                navigateIntent.putExtra(ProductListActivity.KEY_PRODUCT, "");
                navigateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigateIntent);

                break;
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Scan Result = " + data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();
                    Log.e("KK", "Scan Result = " + data.getStringExtra("SCAN_RESULT"));
                    // txtResult.setText("Scan Result : " + data.getStringExtra(ZBarConstants.SCAN_RESULT));
                    result = data.getStringExtra("SCAN_RESULT");

                    if (!TextUtils.isEmpty(result)) {
                        launchNavigation(result);
                    } else {
                        Toast.makeText(this, "scan did not return readable result", Toast.LENGTH_SHORT).show();
                    }

//                    contents = data.getStringExtra("SCAN_RESULT");
//                    format = data.getStringExtra("SCAN_RESULT_FORMAT");
//                    Toast.makeText(this, "Scan Result = " + data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();

                } else if (resultCode == RESULT_CANCELED && data != null) {
//                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//                    if (!TextUtils.isEmpty(error)) {
                    Toast.makeText(this, "RESULT_CANCELED", Toast.LENGTH_SHORT).show();
                    Log.e("DashBoardActivity", "RESULT_CANCELED");
//                        txtResult.setText(error);
//                    }
                    contents = data.getStringExtra("SCAN_RESULT");
                    format = data.getStringExtra("SCAN_RESULT_FORMAT");
                    Toast.makeText(this, "Scan Result = " + data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


//    public class MyVoiceControl extends VoiceControl {
//
//        public MyVoiceControl(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected void onRecognition(String s) {
//
////            if (mVoiceControl != null && mVoiceControl.getConfScore() < 0.35) {
////                return;
////            }
//
//            String moveLeft = "move left";
//            String moveRight = "move right";
//            String cut = "cut";
//
//            if (s.trim().equalsIgnoreCase(moveLeft.trim())) {
//                launchScanner();
//            } else if (s.trim().equalsIgnoreCase(moveRight.trim())) {
//                launchQRScanner();
//            } else if (s.trim().equalsIgnoreCase(cut.trim())) {
//               exitNavigation();
//            }
//
//            Log.e("KK", "speech " + s);
//        }
//    }

    public void launchNavigation(String scanResult) {
        Intent navigateIntent;
        try {
            JSONObject object = new JSONObject(scanResult);
            navigateIntent = new Intent(DashBoardActivity.this, DetailActivity.class);
        } catch (JSONException e) {
            navigateIntent = new Intent(DashBoardActivity.this, ProductListActivity.class);
        }
        //Intent
        if (navigateIntent == null) {
            navigateIntent = new Intent(DashBoardActivity.this, ProductListActivity.class);
        }

        navigateIntent.putExtra(EXTRA_SCAN_RESULT, scanResult);
        navigateIntent.putExtra(ProductListActivity.KEY_PRODUCT, scanResult);
        navigateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        navigateIntent.putExtra(ProductListActivity.ORDER_KEY,orderDetailsList);
        startActivity(navigateIntent);
    }


    public void startListeningVoice() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getPackageName());
        mRecognizer.startListening(intent);
    }


    /*public void setList() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        mHeaderView = inflater.inflate(R.layout.dashboard_header,null);
        mBtnBarcode = (Button)mHeaderView.findViewById(R.id.btn_scan);
        mBtnQrCode = (Button)mHeaderView.findViewById(R.id.btn_scan_qr);
        mBtnBarcode.setOnClickListener(this );
        mBtnQrCode.setOnClickListener(this);
        lv_products = (ListView) findViewById(R.id.lv_products);
        productList = new ArrayList<ProductModel>();
        for (int i = 0; i < 3; i++) {
            ProductModel model = new ProductModel();
            model.setProductName("Product " + (i+1));
            *//*if (i % 2 == 0) {
                model.setIsPicked(true);
            } else {
                model.setIsPicked(false);
            }*//*
            productList.add(model);
        }
        mAdapter = new ProductListAdapter(this, productList);
        lv_products.addHeaderView(mHeaderView,null,false);
        lv_products.setAdapter(mAdapter);

        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //launchNavigation(productList.get(position).getProductName());
                Log.e("KK", "onitem click " + position);
            }
        });

    }*/

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private  AlertDialog dialog;

    public void showExitDialog() {
         dialog = new AlertDialog.Builder(DashBoardActivity.this).setMessage("Are you sure want to cancel current consignment and exit")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Cancel", null).create();
         if(dialog != null && !dialog.isShowing()) {
             dialog.show();
         }
    }

    public static Context getApplicationEx() {
        return applicationEx;
    }

    public static boolean isSSL() {
        return isSSL;
    }

    public static boolean isRunLocalCode() {
        return RUN_LOCAL_CODE;
    }

    public static ThreadPoolExecutor getOperationsQueue() {
        if (operationsQueue == null) {
            operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
        return operationsQueue;
    }

    private class GetServiceTask extends AsyncTask<Void,Void,ArrayList<OrderDetails>> {

        @Override
        protected ArrayList<OrderDetails> doInBackground(Void... voids) {

            ArrayList<OrderDetails> orderDetails= getOrderDetailsService.makeRequestAndGetData();
            Log.e("KK","API response sixe "+orderDetails.size());
            return orderDetails;
        }

        @Override
        protected void onPostExecute(ArrayList<OrderDetails> aVoid) {
            super.onPostExecute(aVoid);
            orderDetailsList = (ArrayList<OrderDetails>) aVoid;
            if(!orderDetailsList.isEmpty()){
                if(!TextUtils.isEmpty(orderDetailsList.get(0).getBarcode())){
                    //alarmManager.cancel(pendingIntent);
                    if(timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    launchNavigation("");
                }
            }
        }


    }

    private GetOrderDetailsService.GetOrderDetailsServiceListener mGetOrderListener = new GetOrderDetailsService.GetOrderDetailsServiceListener() {
        @Override
        public void onGetOrderDetailsServiceFinished(ArrayList<OrderDetails> customersArrayList) {
            orderDetailsList = customersArrayList;
            launchNavigation("");
        }

        @Override
        public void onGetOrderDetailsServiceFailed(String error) {
            Toast.makeText(DashBoardActivity.this,"cannot load data",Toast.LENGTH_SHORT).show();
        }
    };

    /*private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VuzixSpeechClient.ACTION_VOICE_COMMAND)) {
                String phrase = intent.getStringExtra(VuzixSpeechClient.PHRASE_STRING_EXTRA);
                if (phrase != null ) {
                    Log.e("KK","phrase is "+ phrase);
                   *//* if(phrase.equalsIgnoreCase(BACK)){
                        Log.e("KK","back");
                       // exitNavigation();
                        showExitDialog();
                    }else if(phrase.equalsIgnoreCase(OK)){
                        finish();
                    }else if(phrase.equalsIgnoreCase(CANCEL)){
                        if(dialog != null && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }*//*
                    if(phrase.equals(GO_ONLINE)){
                    RUN_LOCAL_CODE = false;
                    initialWork();
                    setTimer();

                    }else if(phrase.equals(GO_OFFLINE)){
                    RUN_LOCAL_CODE = true;
                    initialWork();
                    setTimer();
                   }
                }
            }
        }
    };*/

    public static void setRunLocalCode(boolean isLocal){
        RUN_LOCAL_CODE = isLocal;
    }


}
