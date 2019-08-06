package com.capgemini.vuzixscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.capgemini.vuzixscanner.adapters.ProductListAdapter;
import com.capgemini.vuzixscanner.entity.OrderDetails;
import com.capgemini.vuzixscanner.service.GetOrderDetailsService;
import com.capgemini.vuzixscanner.utils.Constants;

import java.util.ArrayList;

/**
 * Created by apple on 01/09/16.
 */
public class ProductListActivity extends FragmentActivity implements GetOrderDetailsService.GetOrderDetailsServiceListener {

    private static final String LOG_TAG = ProductListActivity.class.getSimpleName();
    private GetOrderDetailsService getOrderDetailsService;
    private ArrayList<OrderDetails> orderDetailsArrayList;

    private ListView lv_products;
    //    private ArrayList<ProductModel> productList;
    private ProductListAdapter mAdapter;
    public static final String KEY_PRODUCT = "product_bundle";
    public static final String PREFS_KEY_VUZIX = "vuzix";
    private String productName = "";
    private View mHeaderView;
    private Button mBtnNavigate;
    private PreferenceUtils prefUtils;

    public String PRODUCT_KEY = "";
    public static final String ORDER_KEY = "order_key";
    public static  final String SCAN_FIRST = "scan_first";
    public static  final String SCAN_NEXT = "scan_next";
    public static  final String SCAN_PERFUME = "scan_perfume";
    public static  final String SCAN_IPHONE = "scan_iphone";
    public static  final String SCAN_PRODUCT = "scan_product";
    public static  final String BACK = "back";
    //private VuzixSpeechClient mSpeechClient;
    private int scanCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrderDetailsService = new GetOrderDetailsService(getApplicationContext(), this);

        orderDetailsArrayList = (ArrayList<OrderDetails>) getIntent().getSerializableExtra(ORDER_KEY);
        /*try {
            mSpeechClient = new VuzixSpeechClient(ProductListActivity.this);
        mSpeechClient.deletePhrase("*");
       *//* mSpeechClient.insertPhrase("Scan First",SCAN_FIRST);*//*
            mSpeechClient.insertPhrase("Scan Perfume",SCAN_NEXT);
        mSpeechClient.insertPhrase("Scan Hand bag",SCAN_NEXT);
        mSpeechClient.insertPhrase("Scan I Phone",SCAN_NEXT);
            mSpeechClient.insertPhrase("Scan Product",SCAN_NEXT);
       *//* mSpeechClient.insertPhrase("Go back",BACK);
        mSpeechClient.insertPhrase("Exit",BACK);*//*
        mSpeechClient.insertPhrase("Go Online",DashBoardActivity.GO_ONLINE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        registerReceiver(mScanReceiver,new IntentFilter(VuzixSpeechClient.ACTION_VOICE_COMMAND));
*/
        setContentView(R.layout.activity_product_list);
        Utility.setActionBar(ProductListActivity.this);
        lv_products = (ListView) findViewById(R.id.lv_products);
        setListViewHeightBasedOnChildren(lv_products);
        prefUtils = new PreferenceUtils(ProductListActivity.this);

        setList();
//        productList = new ArrayList<ProductModel>();
        productName = getIntent().getStringExtra(KEY_PRODUCT);
        Log.i("ProductListActivity", "productName :: " + productName);

        if(orderDetailsArrayList == null || orderDetailsArrayList.isEmpty()){
            DashBoardActivity.getOperationsQueue().execute(getOrderDetailsService);
        }else{
            if(orderDetailsArrayList != null && !orderDetailsArrayList.isEmpty()){
                populateList(orderDetailsArrayList);
            }
        }
        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("KK", "onitem click " + position);

                PRODUCT_KEY = orderDetailsArrayList.get(position).getBarcode();

                if (!prefUtils.getProduct1State(position+1)) {
                    launchQRScanner();
//                    launchNavigation();
                }
            }
        });

        lv_products.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }


    public void launchNavigation() {
        //commenting acceleremoter map
        Intent navigateIntent = new Intent(ProductListActivity.this, NavigationActivity.class);
        // showing indoor atlas map
        //Intent navigateIntent = new Intent(ProductListActivity.this, ImageViewActivity.class);
        navigateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(navigateIntent);
    }

    public void setList() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mHeaderView = inflater.inflate(R.layout.dashboard_header, null);
        mBtnNavigate = (Button) findViewById(R.id.btn_navigate);
        /*mBtnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtils.getProduct1State() && prefUtils.getProduct2State() && prefUtils.getProduct3State()) {
                    launchNavigation();
                } else {
                    Utility.showToast(ProductListActivity.this, "Please pick all the three products in consignment and then start navigation");
                }
            }
        });*/
        //lv_products.addHeaderView(mHeaderView, null, true);

    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    cameraPremissionGranted();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void launchQRScanner() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                1);


    }

    private void cameraPremissionGranted() {
        if (Utility.isCameraAvailable(ProductListActivity.this)) {
//            Intent intent = new Intent(ProductListActivity.this, ZBarScannerActivity.class);
//            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
//            startActivityForResult(intent, DashBoardActivity.ZBAR_QR_SCANNER_REQUEST);

           /* Intent intent = new Intent("com.google.zxing.client.android.CGCUSTOMSCAN");
            //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");

            startActivityForResult(intent, DashBoardActivity.ZBAR_QR_SCANNER_REQUEST);*/


            Intent intent1 = new Intent(this,ScanActivity.class);
            startActivityForResult(intent1,DashBoardActivity.ZBAR_QR_SCANNER_REQUEST);


        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }
//*********************************result from scanner**********************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = "";


        switch (requestCode) {
            case DashBoardActivity.ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                 Toast.makeText(this, "Item found : " + data.getStringExtra(Constants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                    Log.e("KK", "Scan Result = " + data.getStringExtra(Constants.SCAN_RESULT));
                    // txtResult.setText("Scan Result : " + data.getStringExtra(ZBarConstants.SCAN_RESULT));

                    result = data.getStringExtra(Constants.SCAN_RESULT);

                    productName = result;

                    setSelectedProduct();
                    scanCounter = getProductToScan();
                    mAdapter.notifyDataSetChanged();


//                } else if (resultCode == RESULT_CANCELED && data != null) {
//                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//                    if (!TextUtils.isEmpty(error)) {
//                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//                        Log.e("KK", error);
//
//                    }
                }
                break;
        }
    }

    public void setSelectedProduct() {
        if (null != orderDetailsArrayList) {
            for (int i = 0; i < orderDetailsArrayList.size(); i++) {
                if (productName.equalsIgnoreCase(orderDetailsArrayList.get(i).getBarcode())) {
                    orderDetailsArrayList.get(i).setPicked(true);
                    prefUtils.setCheckedProduct(i + 1);
                    lv_products.setSelection(i + 1);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        boolean toNavigate = true;
        for(int i = 0 ; i < orderDetailsArrayList.size() ; i++){
            OrderDetails orderDetails = orderDetailsArrayList.get(i);
            if(!prefUtils.getProduct1State(i+1)){
                toNavigate = false;
                break;
            }
        }
        if (toNavigate) {
            launchNavigation();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefUtils.clearAllPrefs();
        //unregisterReceiver(mScanReceiver);
    }

    @Override
    public void onGetOrderDetailsServiceFinished(final ArrayList<OrderDetails> ordersArrayList) {
        runOnUiThread(new Runnable() {
            public void run() {
                populateList(ordersArrayList);
            }
        });
    }

    private void populateList(ArrayList<OrderDetails> ordersArrayList) {
        if (null != ordersArrayList) {
            orderDetailsArrayList = ordersArrayList;
            Log.i(LOG_TAG, "orderDetailsArrayList Size :: " + orderDetailsArrayList.size());
            Log.i(LOG_TAG, "orderDetailsArrayList getCatalogName :: " + orderDetailsArrayList.get(0).getCatalogName());
            Log.i(LOG_TAG, "orMderDetailsArrayList getColor :: " + orderDetailsArrayList.get(0).getColor());
            mAdapter = new ProductListAdapter(getApplicationContext(), ordersArrayList);
            lv_products.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

   /* private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VuzixSpeechClient.ACTION_VOICE_COMMAND)) {
                String phrase = intent.getStringExtra(VuzixSpeechClient.PHRASE_STRING_EXTRA);
                if (phrase != null ) {
                    Log.e("KK","phrase is "+ phrase);
                    if (phrase.equals(SCAN_FIRST)) {
                        Log.e("KK","scan first");
                        PRODUCT_KEY = orderDetailsArrayList.get(0).getBarcode();

                        if (!prefUtils.getProduct1State(1)) {
                            launchQRScanner();
//                    launchNavigation();
                        }
                    }else if(phrase.equals(SCAN_NEXT) || phrase.equals(SCAN_PRODUCT) || phrase.equals(SCAN_PERFUME) || phrase.equals(SCAN_IPHONE)){
                        Log.e("KK","scan next");
                        if(scanCounter < orderDetailsArrayList.size()) {
                            PRODUCT_KEY = orderDetailsArrayList.get(scanCounter).getBarcode();

                            if (!prefUtils.getProduct1State(scanCounter + 1)) {
                                launchQRScanner();
//                    launchNavigation();
                            }
                        }
                    }else if(phrase.equalsIgnoreCase(BACK)){
                        Log.e("KK","back");
                        finish();
                    }else if(phrase.equalsIgnoreCase(DashBoardActivity.GO_ONLINE)){
                        DashBoardActivity.setRunLocalCode(false);
                        Intent dashboardIntent = new Intent(ProductListActivity.this,DashBoardActivity.class);
                        dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(dashboardIntent);
                    }
                }
            }
        }
    };*/

    @Override
    public void onGetOrderDetailsServiceFailed(String error) {
        Log.i("", "" + error);
    }

    public int getProductToScan(){
        for(int i = 0 ; i < orderDetailsArrayList.size();i++){
            if(!prefUtils.getProduct1State(i+1)){
               return i;
            }
        }
        int variable = (scanCounter+1);
        return variable;
    }
}
