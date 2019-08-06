package com.capgemini.vuzixscanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.capgemini.vuzixscanner.utils.Constants;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends FragmentActivity {

    private SurfaceView mSurfaceView;
    private TextView mResult;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private static  final int CAMERA_REQUEST_CODE = 1023;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mSurfaceView = findViewById(R.id.surfaceView);
        mResult = findViewById(R.id.txt_result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScanner();
    }

    public void initScanner(){
     mBarcodeDetector = new BarcodeDetector.Builder(ScanActivity.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

     mCameraSource = new CameraSource.Builder(ScanActivity.this,mBarcodeDetector)
             .setRequestedPreviewSize(1920,1080)
             .setAutoFocusEnabled(true)
             .build();
     mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
         @Override
         public void surfaceCreated(SurfaceHolder holder) {
             try {
                 if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                     mCameraSource.start(mSurfaceView.getHolder());
                 }else{
                     ActivityCompat.requestPermissions(ScanActivity.this,new String[]{},CAMERA_REQUEST_CODE);
                 }

             }catch (Exception e){
                 e.printStackTrace();
             }
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

         }

         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {
             mCameraSource.stop();
         }
     });

     mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
         @Override
         public void release() {
             Log.e("TAG","releasing camera source");
         }

         @Override
         public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray<Barcode> barcodes =  detections.getDetectedItems();
            if(barcodes.size() > 0){
                String value = barcodes.valueAt(0).rawValue;
                Intent intent = new Intent();
                intent.putExtra(Constants.SCAN_RESULT,value);
                setResult(RESULT_OK,intent);
                finish();
            }
         }
     });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                    try {
                        mCameraSource.start(mSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCameraSource.release();
    }
}
