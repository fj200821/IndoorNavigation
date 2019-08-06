package com.capgemini.vuzixscanner.service;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.capgemini.vuzixscanner.DashBoardActivity;
import com.capgemini.vuzixscanner.R;
import com.capgemini.vuzixscanner.utils.Constants;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by sphansek on 6/14/2017.
 */

public abstract class BaseConnection implements Runnable {
    private static final String LOG_TAG = BaseConnection.class.getSimpleName();
    private static boolean security = false;
    private static final int CONNECTION_TIMEOUT = 60 * 1000; // 60 seconds

    public static final int GET = 10001;
    public static final int POST = 10002;
    public static final int PUT = 10003;
    public static final int DELETE = 10004;

    protected static int statusCode = -1;

    protected String apiName = Constants.Symbol.EMPTY;

    private final Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
    private  Context mContext;

    public BaseConnection(Context context) {
        // For Security Testing
        mContext = context;
        if (security)
            forceSecurity();
    }

    // For Security Testing
    // Load the security certificate if required.
    public static void forceSecurity() {
        //ConfigValues.setCertName(BURP_CERT);
    }

    /**
     * Execute the request based on the type of service
     */
    abstract void executeRequest();

    void sentFailure(final String code) {
    }

    @Override
    public void run() {
        executeRequest();
    }


    public String makeRequest(String url, final String postData, final int type) throws IOException, Exception{
        String authResponse = Constants.Symbol.EMPTY;
        if (isNetworkAvailable(DashBoardActivity.getApplicationEx().getApplicationContext())) {
//            url = ConfigValues.getBaseUrl() + url;
//            url = ConfigValues.getBaseUrl() + url;
            InputStream inputStream = null;
            try{
                final URL httpUrl = new URL(url);
                final HttpsURLConnection  urlConnection = (HttpsURLConnection) httpUrl.openConnection();
                urlConnection.setReadTimeout(CONNECTION_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setAllowUserInteraction(false);

                // Add header values
                urlConnection.setRequestProperty(DashBoardActivity.getApplicationEx().getString(R.string.content_type), DashBoardActivity.getApplicationEx().getString(R.string.content_type_json));
//                urlConnection.setRequestProperty(DashBoardActivity.getApplicationEx().getString(R.string.x_api_key), ConfigValues.getApiKey());
//                urlConnection.setRequestProperty(AUTHORIZATION, headerAuthorization);
//                urlConnection.setRequestProperty(UID, mAssistLite.getSelectedAgent().getAgentCode());
//                if(!TextUtils.isEmpty(mAssistLite.getJioRoute())){
//                    urlConnection.setRequestProperty(JIOROUTE, mAssistLite.getJioRoute());
//                    Log.d(LOG_TAG, JIOROUTE + " :: " + mAssistLite.getJioRoute());
//                }

                // load certificate values if isSSL true.
                if(!DashBoardActivity.isSSL()) {
                  /*  final Resources resources = DashBoardActivity.getApplicationEx().getResources();
                    final AssetManager assetManager = resources.getAssets();
//                    final InputStream certInputStream = assetManager.open(ConfigValues.getCertName());
//                    //Log.d(LOG_TAG, "makeRequest :: " + "Loading certificate");
                    final KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                    try {
//                        trustStore.load(certInputStream, ConfigValues.getCertPassword().toCharArray());
//                    } finally {
//                        certInputStream.close();
//                    }
                    final String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    final TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(trustStore);

                    // Transport Layer Security (TLS) and its predecessor, Secure Sockets Layer (SSL),
                    // both frequently referred to as "SSL", are cryptographic protocols that
                    // provide communications security over a computer network
                    final SSLContext context = SSLContext.getInstance(Constants.HttpMethods.TLS);
                    context.init(null, tmf.getTrustManagers(), null);
//                    urlConnection.setSSLSocketFactory(context.getSocketFactory());*/

                    urlConnection.setSSLSocketFactory(getSslSocketFactory(mContext));
                } else {
                    Log.d(LOG_TAG, "isSSL() return false");
                }

                if(type == GET){
                    urlConnection.setRequestMethod(Constants.HttpMethods.GET);
                    urlConnection.setDoInput(true);
                }
                else if(type == POST){
                    urlConnection.setRequestMethod(Constants.HttpMethods.POST);
                    urlConnection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.writeBytes(postData);
                    wr.flush();
                    wr.close();
                }
                else if(type == PUT){
                    urlConnection.setRequestMethod(Constants.HttpMethods.PUT);
                    urlConnection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.writeBytes(postData);
                    wr.flush();
                    wr.close();
                }
                else if(type == DELETE) {
                    //urlConnection.setRequestMethod();
                    // not using at this
                }

                Log.d(LOG_TAG, "url connecting :: " + url);
                if(!TextUtils.isEmpty(postData)){
                    Log.d(LOG_TAG, "PostData in url :: " + postData);
                }

                urlConnection.connect();
                statusCode = urlConnection.getResponseCode();
                Log.i(LOG_TAG, "STATUS CODE:: " + statusCode);
                if(statusCode == 204){
                    return Constants.Symbol.EMPTY;
                }

                headersMap.clear();
                headersMap.putAll(urlConnection.getHeaderFields());
//                for(final String item : headersMap.keySet()){
//                    if(!TextUtils.isEmpty(item) && item.equalsIgnoreCase(JIOROUTE)){
//                        DPCMainActivity.setJioRoute(headersMap.get(item).get(0));
//                        break;
//                    }
//
//                }
                try {
                    inputStream = urlConnection.getInputStream();
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    inputStream = urlConnection.getErrorStream();
                }
                if(inputStream == null){
                    throw new Exception(DashBoardActivity.getApplicationEx().getString(R.string.general_exception_occured));
                }

                authResponse = getResponse(inputStream);
            }
            finally{
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        else{
            throw new Exception(DashBoardActivity.getApplicationEx().getString(R.string.exception_network_not_found));
        }
        return authResponse;
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private String getResponse(final InputStream inputStream) throws IOException{
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        final StringBuffer responseBuffer = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        final String authResponse = responseBuffer.toString();
        int maxLogSize = 3000;
        for(int i = 0; i <= authResponse.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > authResponse.length() ? authResponse.length() : end;
            Log.d("Response ::", authResponse.substring(start, end));
        }
        return authResponse;
    }

    protected String getHeaderValue(final String key){
        for(final String item : headersMap.keySet()){
            if(!TextUtils.isEmpty(item) && item.equalsIgnoreCase(key)){
                return headersMap.get(item).get(0);
            }
        }
        return Constants.Symbol.EMPTY;
    }

    protected String getUrl(final int url){
        if(url <= 0){
            return Constants.Symbol.EMPTY;
        }
        apiName = DashBoardActivity.getApplicationEx().getResources().getResourceEntryName(url);
        return DashBoardActivity.getApplicationEx().getString(url);
    }

    public String getExceptionType(final Exception e) {
        if (e instanceof JSONException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_json_errorr);
        } else if (e instanceof KeyManagementException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_key_management);
        } else if (e instanceof UnrecoverableKeyException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_unrecoverable_key);
        } else if (e instanceof KeyStoreException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_keystore);
        } else if (e instanceof NoSuchAlgorithmException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_algorithm_not_found_exception);
        } else if (e instanceof CertificateException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_certificate_not_found);
        } else if (e instanceof IOException) {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_io_exception);
        } else if (e instanceof Exception) {
            // TODO
            return e.getMessage();
        } else {
            return DashBoardActivity.getApplicationEx().getString(R.string.exception_network_not_found);
        }
    }

    protected synchronized SSLSocketFactory getSslSocketFactory(Context context) {
        SSLContext mSslContext = null;
        try {

            // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt

            List<String> certificateFileList = Arrays.asList(context.getAssets().list(""));
            //detect if assets folder contains a valid certificate
            if(!certificateFileList.contains("APIService.cer")){
                //if not API will be called without SSL pinning
                return null;
            }
            InputStream caInput = new BufferedInputStream(context.getAssets().open("APIService.cer"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);

            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            mSslContext = SSLContext.getInstance("TLS");
            TrustManager mTrustManagerFactory = tmf.getTrustManagers()[0];
            mSslContext.init(null, tmf.getTrustManagers(), null);
            return mSslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}