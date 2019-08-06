package com.capgemini.vuzixscanner.entity;

import com.capgemini.vuzixscanner.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sphansek on 6/14/2017.
 */

public class OrderDetails implements Serializable {

    private String orderId;
    private String catalogName;
    private String imagePath;
    private String description;
    private String modelId;
    private String statusInStock;
    private String stockAvailable;
    private String price;
    private String color;
    private String rating;
    private String brand;
    private String discount;
    private String barcode;
    private boolean isPicked ;
    private String rfid;

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getStatusInStock() {
        return statusInStock;
    }

    public void setStatusInStock(String statusInStock) {
        this.statusInStock = statusInStock;
    }

    public String getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(String stockAvailable) {
        this.stockAvailable = stockAvailable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public static ArrayList<OrderDetails> getOrderDetails(final String response) throws Exception, JSONException {
        //final JSONObject orderDetailsJSONObject = new JSONObject(response);

//        ServiceUtils.checkForResponseError(orderDetailsJSONObject);

        final JSONArray orderDetailsJsonArray = new JSONArray(response);
        final int totalOrders = orderDetailsJsonArray.length();
        final ArrayList<OrderDetails> ordersDetailsArrayList = new ArrayList<OrderDetails>();

        for (int i = 0; i < totalOrders; i++) {
            final OrderDetails orderDetail = new OrderDetails();

            final JSONObject orderJSONObject = orderDetailsJsonArray.getJSONObject(i);

            if (orderJSONObject.has(Constants.OrderDetails.ID)) {
                orderDetail.setOrderId(orderJSONObject.getString(Constants.OrderDetails.ID));
            }
            if (orderJSONObject.has(Constants.OrderDetails.CATALOG_NAME)) {
                orderDetail.setCatalogName(orderJSONObject.getString(Constants.OrderDetails.CATALOG_NAME));
            }
            if (orderJSONObject.has(Constants.OrderDetails.IMAGE_PATH)) {
                orderDetail.setImagePath(orderJSONObject.getString(Constants.OrderDetails.IMAGE_PATH));
            }
            if (orderJSONObject.has(Constants.OrderDetails.DESCRIPTION)) {
                orderDetail.setDescription(orderJSONObject.getString(Constants.OrderDetails.DESCRIPTION));
            }
            if (orderJSONObject.has(Constants.OrderDetails.MODEL_id)) {
                orderDetail.setModelId(orderJSONObject.getString(Constants.OrderDetails.MODEL_id));
            }
            if (orderJSONObject.has(Constants.OrderDetails.STATUS_IN_STOCK)) {
                orderDetail.setStatusInStock(orderJSONObject.getString(Constants.OrderDetails.STATUS_IN_STOCK));
            }
            if (orderJSONObject.has(Constants.OrderDetails.STOCK_AVAILABLE)) {
                orderDetail.setStockAvailable(orderJSONObject.getString(Constants.OrderDetails.STOCK_AVAILABLE));
            }
            if (orderJSONObject.has(Constants.OrderDetails.PRICE)) {
                orderDetail.setPrice(orderJSONObject.getString(Constants.OrderDetails.PRICE));
            }
            if (orderJSONObject.has(Constants.OrderDetails.COLOR)) {
                orderDetail.setColor(orderJSONObject.getString(Constants.OrderDetails.COLOR));
            }
            if (orderJSONObject.has(Constants.OrderDetails.RATING)) {
                orderDetail.setRating(orderJSONObject.getString(Constants.OrderDetails.RATING));
            }
            if (orderJSONObject.has(Constants.OrderDetails.BRAND)) {
                orderDetail.setBrand(orderJSONObject.getString(Constants.OrderDetails.BRAND));
            }
            if (orderJSONObject.has(Constants.OrderDetails.DISCOUNT)) {
                orderDetail.setDiscount(orderJSONObject.getString(Constants.OrderDetails.DISCOUNT));
            }
            if (orderJSONObject.has(Constants.OrderDetails.BARCODE)) {
                orderDetail.setBarcode(orderJSONObject.getString(Constants.OrderDetails.BARCODE));
            }

            if (orderJSONObject.has(Constants.OrderDetails.RFID)) {
                orderDetail.setRfid(orderJSONObject.getString(Constants.OrderDetails.RFID));
            }


            if (orderJSONObject.has(Constants.OrderDetails.PRODUCT_ID)) {
                orderDetail.setOrderId(orderJSONObject.getString(Constants.OrderDetails.PRODUCT_ID));
            }
            if (orderJSONObject.has(Constants.OrderDetails.PRODUCT_NAME)) {
                orderDetail.setCatalogName(orderJSONObject.getString(Constants.OrderDetails.PRODUCT_NAME));
            }

            ordersDetailsArrayList.add(orderDetail);
        }
        return ordersDetailsArrayList;
    }
}
