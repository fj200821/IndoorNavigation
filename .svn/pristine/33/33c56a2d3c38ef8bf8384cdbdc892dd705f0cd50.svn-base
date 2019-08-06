package com.capgemini.vuzixscanner;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.capgemini.vuzixscanner.animation.Rotate3DAnimation;
import com.capgemini.vuzixscanner.pedometer.PedometerSettings;
import com.capgemini.vuzixscanner.pedometer.StepService;
import com.capgemini.vuzixscanner.pedometer.Utils;
import com.capgemini.vuzixscanner.utils.SendDeliveryStatusTask;
import com.vuzix.hardware.VuzixCamera;
import com.vuzix.speech.VoiceControl;
//import net.sourceforge.zbar.ImageScanner;

public class NavigationActivity extends FragmentActivity implements SensorEventListener, SurfaceHolder.Callback {

    private SensorManager sensorMan;
    private Sensor accelerometer;
    private static final String TAG = "Pedometer";
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private Utils mUtils;
    private float[] mGravity;
    private static float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private TextView mTxtDirection, mTxtResult;
    private int mStepValue;
    private int mPaceValue;
    private float mDistanceValue;
    private float mSpeedValue;
    private int mCaloriesValue;
    private float mDesiredPaceOrSpeed;
    private int mMaintain;
    private boolean mIsMetric;
    private float mMaintainInc;
    private boolean mQuitting = false;
    private ImageView mImgDirection;
    private float currentDegree = 0f;
    private Rotate3DAnimation rotation;
    private StartNextRotate startNext;
    private double lastValue = 0;

    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Sensor orientation;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;
    private ImageView img_path, img_second_path;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private VuzixCamera.PictureCallback rawCallback;
    private VuzixCamera.ShutterCallback shutterCallback;
    private VuzixCamera.PictureCallback jpegCallback;
    private FrameLayout parent_navigation;
//    private CameraPreview mPreview;
//    private ImageScanner mScanner;
    private Handler mAutoFocusHandler;
    private boolean mPreviewing = true ;
    private static boolean mTakeRight = false;
    public static final double TOTAL_DISTANCE = 3.5;
    public static final double FIRST_DISTANCE = 3.5;
    public static final double SECOND_DISTANCE = 3.5;
    public double pathHeight, arrowLength, arrowWidth, arrowTop, pathY, inititialArrow, firstPathY, secondPathHeight;

    public double secondPathWidth, secondPathDest, secondPathInitArrow;
    private MyVoiceControl mVoiceControl;
    private TextView ckpt1 , ckpt2;
    private boolean isDataFetched = false;
    /**
     * True, when service is running.
     */
    private boolean mIsRunning;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String KEY_SHARED_PREFS = "prefs";
    public static final String KEY_DISTANCE = "distance";
    private PreferenceUtils prefUtils ;
    private  boolean isBound = false;
    public static  final String BACK = "back";
    /*private VuzixSpeechClient mSpeechClient;*/



   /* @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
     /*   if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(500);

            ra.setFillAfter(true);

            mImgDirection.startAnimation(ra);
            mCurrentDegree = -azimuthInDegress;
        }


        if(event.sensor == orientation){
            Log.e("KK","orientaui change "+event.values[0] );
        }else if(event.sensor == mMagnetometer) {
            Log.e("KK", "magneto change " + event.values[0]);
        }*/


        float degree = Math.round(event.values[0]);
        //txtDegrees.setText("Rotation: "+Float.toString(degree)+" degrees");
        RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(1000);
        ra.setFillAfter(true);
       // mImgDirection.startAnimation(ra);
        currentDegree = -degree;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation);

       /* try {
            mSpeechClient = new VuzixSpeechClient(NavigationActivity.this);
            mSpeechClient.deletePhrase("*");
            mSpeechClient.insertPhrase("Go back",BACK);
            mSpeechClient.insertPhrase("Exit",BACK);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        registerReceiver(mReceiver,new IntentFilter(VuzixSpeechClient.ACTION_VOICE_COMMAND));
        */


         // Utility.setActionBar(NavigationActivity.this);
        mTxtDirection = (TextView) findViewById(R.id.txtdirection);
        mTxtResult = (TextView) findViewById(R.id.scanresult);
        img_path = (ImageView) findViewById(R.id.img_path1);
        ckpt1 = (TextView) findViewById(R.id.ckpt1);
        ckpt2 = (TextView)findViewById(R.id.ckpt2);
        ckpt2.animate().rotationBy(90).setDuration(300).setInterpolator(new LinearInterpolator()).start();
        img_second_path = (ImageView) findViewById(R.id.img_right);
        String result = getIntent().getStringExtra(DashBoardActivity.EXTRA_SCAN_RESULT);
        prefs = getSharedPreferences(KEY_SHARED_PREFS, Context.MODE_PRIVATE);
        prefUtils = new PreferenceUtils(NavigationActivity.this);
        if (!TextUtils.isEmpty(result)) {
            mTxtResult.setText("Scan Result : " + result);
        }
        mImgDirection = (ImageView) findViewById(R.id.direction_arrow);
        mStepValue = 0;
        mPaceValue = 0;
        mUtils = Utils.getInstance();
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = sensorMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orientation = sensorMan.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        parent_navigation = (FrameLayout) findViewById(R.id.navigation_parent);
        /*tilt(parent_navigation, 40f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tilt(ckpt1, -10f);
                tilt(ckpt2,10f);
            }
        }, 110);*/
        if (mMagnetometer != null) {
            Log.e("KK", "magnet exist");
        } else {
            Log.e("KK", "magnet deos not  exist");
        }
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mTakeRight = false;

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mPedometerSettings = new PedometerSettings(mSettings);

        mUtils.setSpeak(mSettings.getBoolean("speak", false));

        // Read from preferences if the service was running on the last onPause
        mIsRunning = mPedometerSettings.isServiceRunning();

        // Start the service if this is considered to be an application start (last onPause was long ago)
        startStepService();
        bindStepService();
       /* if (!mIsRunning && mPedometerSettings.isNewStart()) {
            startStepService();
            bindStepService();
        } else if (mIsRunning) {
            bindStepService();
        }*/

        mPedometerSettings.clearServiceRunning();


        mIsMetric = mPedometerSettings.isMetric();


        if (mMaintain == PedometerSettings.M_PACE) {
            mMaintainInc = 5f;
            mDesiredPaceOrSpeed = (float) mPedometerSettings.getDesiredPace();
        } else if (mMaintain == PedometerSettings.M_SPEED) {
            mDesiredPaceOrSpeed = mPedometerSettings.getDesiredSpeed();
            mMaintainInc = 0.1f;
        }
        displayDesiredPaceOrSpeed();

    }


    private static final int STEPS_MSG = 1;
    private static final int PACE_MSG = 2;
    private static final int DISTANCE_MSG = 3;
    private static final int SPEED_MSG = 4;
    private static final int CALORIES_MSG = 5;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DISTANCE_MSG:
                 // by kamlesh
                 //   mDistanceValue = ((int) msg.arg1) / 1000f;

                    mDistanceValue =( (((int) msg.arg1) / 1000f)+getDistance());
                    setDistance(mDistanceValue);
                    double pathToCover = inititialArrow - pathY;

                    if (mDistanceValue > 0 && mDistanceValue < FIRST_DISTANCE && mDistanceValue <= TOTAL_DISTANCE) {

                        double mRemaining = FIRST_DISTANCE - mDistanceValue;
                        Log.e("KK", mDistanceValue + " distance rem " + mRemaining + " to cover " + pathToCover);
                        mImgDirection.post(new Runnable() {
                            @Override
                            public void run() {
                                arrowTop = mImgDirection.getY();
                            }
                        });
                        double displacement = mDistanceValue - lastValue;
                        double multiplier = (pathToCover / FIRST_DISTANCE);

                        double seekTo = inititialArrow - (pathToCover - ((mRemaining / FIRST_DISTANCE) * pathToCover));


                        Log.e("KK", lastValue + " seeking " + seekTo);
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mImgDirection, "y", (float) lastValue, (float) seekTo);
                        anim.setDuration(1000);
                        anim.start();
                        lastValue = seekTo;

                        //mImgDirection.invalidate();
                    }/* else if (mDistanceValue >= FIRST_DISTANCE && !mTakeRight && mDistanceValue <= TOTAL_DISTANCE) {
                            Log.e("KK","> FIRST");
                            firstDeliveryFlow();

                    } else if (mDistanceValue >= FIRST_DISTANCE && mTakeRight && mDistanceValue <= TOTAL_DISTANCE) {
                            secondDeliveryFlow();
                        }

                    }*/

                    if (mDistanceValue >= TOTAL_DISTANCE) {
                        Log.e("KK","> 10");

                        deliveryComplete();
                    }


                    break;
                case SPEED_MSG:
                    mSpeedValue = ((int) msg.arg1) / 1000f;
                    break;

                default:
                    super.handleMessage(msg);
            }
        }


    };

    public void deliveryComplete(){
        if(isBound) {
            unbindStepService();
            SendDeliveryStatusTask statusTask = new SendDeliveryStatusTask(NavigationActivity.this);
            statusTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        stopStepService();
        if(!NavigationActivity.this.isFinishing()) {
            Utility.showToast(NavigationActivity.this, "You have reached destination");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AlertDialog dialog = new AlertDialog.Builder(NavigationActivity.this).setMessage("Delivery Complete")
                        .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exitNavigation();
                            }
                        }).create();
                if(!NavigationActivity.this.isFinishing()) {
                    dialog.show();
                }
            }
        }, 4*1000);
    }

    public void firstDeliveryFlow(){
        if(isBound) {
            unbindStepService();
        }
        stopStepService();
        if(!NavigationActivity.this.isFinishing()) {
            Utility.showToast(NavigationActivity.this, "Please deliver product 1 here");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AlertDialog dialog = new AlertDialog.Builder(NavigationActivity.this).setMessage("After placing product 1 , Continue Navigation?")
                        .setNeutralButton("Delivered", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                parent_navigation.animate().rotationBy(-90).setDuration(300).setInterpolator(new LinearInterpolator()).start();
                                mImgDirection.animate().rotationBy(90).setDuration(300).setInterpolator(new LinearInterpolator()).start();

                                img_second_path.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startStepService();
                                        bindStepService();
                                        secondPathWidth = img_second_path.getMeasuredWidth();
                                        secondPathDest = img_second_path.getX() + secondPathWidth;
                                        Log.e("KK", "second path dest " + secondPathDest);
                                    }
                                }, 310);

                                mImgDirection.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        secondPathInitArrow = mImgDirection.getX();
                                        arrowWidth = mImgDirection.getMeasuredWidth();
                                        inititialArrow = secondPathInitArrow + arrowWidth;
                                        lastValue = inititialArrow;
                                        Log.e("KK", "second path arrow " + inititialArrow);
                                    }
                                }, 310);
                                Utility.showToast(NavigationActivity.this, "Please turn right");

                                mTakeRight = true;
                            }
                        }).create();
                if(!NavigationActivity.this.isFinishing()) {
                    dialog.show();
                }
            }
        }, 4 * 1000);
    }


    public void secondDeliveryFlow() {
        double mRemaining = TOTAL_DISTANCE - mDistanceValue;

        double pathToCover1 = secondPathDest - (inititialArrow + arrowWidth);
        Log.e("KK", mDistanceValue + " distance rem " + mRemaining + " to cover1111 " + pathToCover1);
        if (mDistanceValue > FIRST_DISTANCE && mDistanceValue <= TOTAL_DISTANCE) {


            mImgDirection.post(new Runnable() {
                @Override
                public void run() {
                    arrowTop = mImgDirection.getY();
                }
            });
            double displacement = mDistanceValue - lastValue;
            double multiplier = (pathToCover1 / TOTAL_DISTANCE);

            double seekTo = inititialArrow + (pathToCover1 - ((mRemaining / SECOND_DISTANCE) * pathToCover1));


            Log.e("KK", lastValue + " seeking " + seekTo);
            ObjectAnimator anim = ObjectAnimator.ofFloat(mImgDirection, "x", (float) lastValue, (float) seekTo);
            anim.setDuration(1000);
            anim.start();
            lastValue = seekTo;

        }
    }

    protected void onResume() {
        Log.i(TAG, "[ACTIVITY] onResume");
        super.onResume();

        // applyRotation(0,0,90);
           sensorMan.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        //   sensorMan.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
       /* if (mVoiceControl != null) {
            mVoiceControl.on();
        }*/
        sensorMan.registerListener(this, orientation, SensorManager.SENSOR_DELAY_GAME);


        img_path.post(new Runnable() {
            @Override
            public void run() {
                pathHeight = img_path.getMeasuredHeight();
                firstPathY = img_path.getY();
                pathY = firstPathY;

            }
        });



        mImgDirection.post(new Runnable() {
            @Override
            public void run() {
                arrowLength = mImgDirection.getMeasuredHeight();
                arrowWidth = mImgDirection.getMeasuredWidth();
                arrowTop = mImgDirection.getY();
                inititialArrow = mImgDirection.getY();
                lastValue = inititialArrow;
                Log.e("KK", "top " + arrowTop);
            }
        });

        img_second_path.post(new Runnable() {
            @Override
            public void run() {
                secondPathHeight = img_second_path.getMeasuredHeight();

                pathY = firstPathY - (secondPathHeight - converToPixels(12.5f));
                Log.e("KK", pathY + " path " + firstPathY + " height " + secondPathHeight);
            }
        });


    }

    private void displayDesiredPaceOrSpeed() {
        if (mMaintain == PedometerSettings.M_PACE) {
            //    mDesiredPaceView.setText("" + (int)mDesiredPaceOrSpeed);
        } else {
            //  mDesiredPaceView.setText("" + mDesiredPaceOrSpeed);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "[ACTIVITY] onPause");
        //  sensorMan.unregisterListener(this,mAccelerometer);
        sensorMan.unregisterListener(this, mMagnetometer);
        sensorMan.unregisterListener(this, orientation);
       /* if (mVoiceControl != null) {
            mVoiceControl.off();
        }*/


    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        Log.i(TAG, "[ACTIVITY] onStop");

        super.onStop();
    }

    protected void onDestroy() {
        Log.i(TAG, "[ACTIVITY] onDestroy");
        setDistance(0f);
        if (isBound && !NavigationActivity.this.isFinishing()){
            unbindStepService();
            isBound = false ;
        }

        if (mQuitting) {
            mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
        } else {
            mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
        }


        savePaceSetting();
        stopStepService();

        //unregisterReceiver(mReceiver);

      /*  if (mVoiceControl != null) {
            mVoiceControl.destroy();
        }*/

        super.onDestroy();
    }

    protected void onRestart() {
        Log.i(TAG, "[ACTIVITY] onRestart");
        super.onRestart();
    }

    private void setDesiredPaceOrSpeed(float desiredPaceOrSpeed) {
        if (mService != null) {
            if (mMaintain == PedometerSettings.M_PACE) {
                mService.setDesiredPace((int) desiredPaceOrSpeed);
            } else if (mMaintain == PedometerSettings.M_SPEED) {
                mService.setDesiredSpeed(desiredPaceOrSpeed);
            }
        }
    }

    private void savePaceSetting() {
        mPedometerSettings.savePaceOrSpeedSetting(mMaintain, mDesiredPaceOrSpeed);
    }

    private StepService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = ((StepService.StepBinder) service).getService();
            isBound = true ;
            mService.registerCallback(mCallback);
            mService.reloadSettings();

        }

        public void onServiceDisconnected(ComponentName className) {

            isBound = false ;
            mService = null;
        }
    };


    private void startStepService() {
        if (!mIsRunning) {
            Log.i(TAG, "[SERVICE] Start");
            mIsRunning = true;
            startService(new Intent(NavigationActivity.this,
                    StepService.class));
        }
    }

    private void bindStepService() {
        Log.i(TAG, "[SERVICE] Bind");
        bindService(new Intent(NavigationActivity.this,
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }

    private void unbindStepService() {
        Log.i(TAG, "[SERVICE] Unbind");
        if(isBound && !NavigationActivity.this.isFinishing()) {
            unbindService(mConnection);
            isBound = false ;
        }
    }

    private void stopStepService() {
        Log.i(TAG, "[SERVICE] Stop");
        if (mService != null) {
            Log.i(TAG, "[SERVICE] stopService");
            stopService(new Intent(NavigationActivity.this,
                    StepService.class));
        }
        mIsRunning = false;
    }

    private void resetValues(boolean updateDisplay) {
        if (mService != null && mIsRunning) {
            mService.resetValues();
        } else {

            mTxtDirection.setText("0");
            SharedPreferences state = getSharedPreferences("state", 0);
            SharedPreferences.Editor stateEditor = state.edit();
            if (updateDisplay) {
                stateEditor.putInt("steps", 0);
                stateEditor.putInt("pace", 0);
                stateEditor.putFloat("distance", 0);
                stateEditor.putFloat("speed", 0);
                stateEditor.putFloat("calories", 0);
                stateEditor.commit();
            }
        }
    }

    private static final int MENU_SETTINGS = 8;
    private static final int MENU_QUIT = 9;

    private static final int MENU_PAUSE = 1;
    private static final int MENU_RESUME = 2;
    private static final int MENU_RESET = 3;


    // TODO: unite all into 1 type of message
    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
        }

        public void paceChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
        }

        public void distanceChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG, (int) (value * 1000), 0));
        }

        public void speedChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG, (int) (value * 1000), 0));
        }

        public void caloriesChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG, (int) (value), 0));
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void startRotation(float start, float end) {
        // Calculating center point
        final float centerX = mImgDirection.getWidth() / 2.0f;
        final float centerY = mImgDirection.getHeight() / 2.0f;
        Log.d(TAG, "centerX=" + centerX + ", centerY=" + centerY);
        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        //final Rotate3dAnimation rotation =new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        //Z axis is scaled to 0
        rotation = new Rotate3DAnimation(start, end, centerX, centerY, 300f, true);
        rotation.setDuration(60000);
        rotation.setFillAfter(true);
        //rotation.setInterpolator(new AccelerateInterpolator());
        //Uniform rotation
        rotation.setInterpolator(new LinearInterpolator());
        //Monitor settings
        startNext = new StartNextRotate();
        rotation.setAnimationListener(startNext);
        mImgDirection.startAnimation(rotation);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

       /* if (isCameraAvailable()) {
            stop_camera();
            start_camera();
        }*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //   stop_camera();
    }


    private class StartNextRotate implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onAnimationEnd......");
            mImgDirection.startAnimation(rotation);
        }

        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }


    private void start_camera() {
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.e("KK", "init_camera: " + e);
            return;
        }

        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("KK", "init_camera: " + e);
            return;
        }

    }

    private void stop_camera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }

    }

    public void tilt(final View view, final float deg) {
       /* view.setRotationX(deg);

        // Reset the rotation.
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setRotationX(0);
            }
        }, 100);*/


        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotationX", 0.0f, deg);
        animation.setDuration(100);
        //  animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
        //  view.setCameraDistance(10 * view.getHeight());
    }

    public float converToPixels(float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return px;
    }




    public class MyVoiceControl extends VoiceControl {

        public MyVoiceControl(Context context) {
            super(context);
        }

        @Override
        protected void onRecognition(String s) {

            /*if (mVoiceControl != null && mVoiceControl.getConfScore() < 0.35) {
                return;
            }*/

            String moveLeft = "cut";

            if (s.trim().equalsIgnoreCase(moveLeft.trim())) {
                finish();

            }

            Log.e("KK", "speech " + s);
        }
    }

    public void setDistance(float distance) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(KEY_DISTANCE, distance);
        editor.apply();
    }

    public float getDistance() {
        float distance = prefs.getFloat(KEY_DISTANCE, 0f);
        return distance;
    }


   /* private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VuzixSpeechClient.ACTION_VOICE_COMMAND)) {
                String phrase = intent.getStringExtra(VuzixSpeechClient.PHRASE_STRING_EXTRA);
                if (phrase != null ) {
                    Log.e("KK","phrase is "+ phrase);
                    if(phrase.equalsIgnoreCase(BACK)){
                        Log.e("KK","back");
                        exitNavigation();
                    }
                }
            }
        }
    };*/

    public void exitNavigation(){
        Intent dashBoard = new Intent(NavigationActivity.this,DashBoardActivity.class);
        dashBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        prefUtils.clearAllPrefs();
        startActivity(dashBoard);
    }

}
