package com.capgemini.vuzixscanner.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.capgemini.vuzixscanner.DashBoardActivity;
import com.capgemini.vuzixscanner.R;
import com.capgemini.vuzixscanner.entity.OrderDetails;
import com.capgemini.vuzixscanner.utils.Constants;
import com.capgemini.vuzixscanner.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sphansek on 6/15/2017.
 */

public class GetOrderDetailsService extends BaseConnection {
    private static final String LOG_TAG = GetOrderDetailsService.class.getSimpleName();
    private final GetOrderDetailsServiceListener getOrderDetailsServiceListener;
    private final Context context;
    private String requestURL;

    public interface GetOrderDetailsServiceListener
    {
        void onGetOrderDetailsServiceFinished(final ArrayList<OrderDetails> customersArrayList);
        void onGetOrderDetailsServiceFailed(final String error);
    }


    public GetOrderDetailsService(final Context context, final GetOrderDetailsServiceListener getOrderDetailsServiceListener) {
        super(context);
        this.context = context;
        this.getOrderDetailsServiceListener = getOrderDetailsServiceListener;
        requestURL = getUrl(R.string.api_getOrderDetails);
    }

    @Override
    void executeRequest() {
        if (DashBoardActivity.isRunLocalCode()) {
            runLocalCode();
        } else {
            makeNetworkRequest();
        }
    }

    private void makeNetworkRequest() {
        try {
            final String response = makeRequest(requestURL, Constants.Symbol.EMPTY, GET);

            if (null == response) {
                sentFailure(DashBoardActivity.getApplicationEx().getString(R.string.exception_network_not_found));
            } else if (response.length() == 0) {
                sentFailure(DashBoardActivity.getApplicationEx().getString(R.string.general_exception_occured));
            } else if (getOrderDetailsServiceListener != null) {
                getOrderDetailsServiceListener.onGetOrderDetailsServiceFinished(OrderDetails.getOrderDetails(response));
            }
            //}
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sentFailure(getExceptionType(e));
        }
    }

    public ArrayList<OrderDetails> makeRequestAndGetData(){
        ArrayList<OrderDetails> productList = new ArrayList<>();
        try {
            final String response = makeRequest(requestURL, Constants.Symbol.EMPTY, GET);


            if (null == response) {
                sentFailure(DashBoardActivity.getApplicationEx().getString(R.string.exception_network_not_found));
            } else if (response.length() == 0) {
                sentFailure(DashBoardActivity.getApplicationEx().getString(R.string.general_exception_occured));
            } else  {
                productList = OrderDetails.getOrderDetails(response);
            }
            //}
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sentFailure(getExceptionType(e));
        }
        return productList;
    }

    private void runLocalCode() {
        // TODO: Simulation of 500 ms sleep needs to be changed
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        // Use the response from assets for time being if WS is not responding
        final String response = ServiceUtils.readResponseFromAssetsFile(context, "orderDetails.json", false);

        final ArrayList<OrderDetails> orderDetailsServicesArrayList;
        try {
            orderDetailsServicesArrayList = OrderDetails.getOrderDetails(response);
            if (getOrderDetailsServiceListener != null) {
                getOrderDetailsServiceListener.onGetOrderDetailsServiceFinished(orderDetailsServicesArrayList);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sentFailure(e.getMessage());
        }
    }

    @Override
    void sentFailure(final String code) {
        if (getOrderDetailsServiceListener != null) {
            getOrderDetailsServiceListener.onGetOrderDetailsServiceFailed(code);
        }
    }

    public int getStatusCode(){
        return statusCode;
    }


}
