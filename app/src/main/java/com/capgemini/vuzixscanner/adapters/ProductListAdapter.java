package com.capgemini.vuzixscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.capgemini.vuzixscanner.R;
import com.capgemini.vuzixscanner.entity.OrderDetails;

import java.util.ArrayList;

/**
 * Changes by sphansek on 6/15/2017.
 */
public class ProductListAdapter extends BaseAdapter {

    private ArrayList<OrderDetails> orderList ;
    private Context mContext ;
    public ProductListAdapter(Context context , ArrayList<OrderDetails> orderList){
        mContext =context ;
        this.orderList = orderList ;
    }

    @Override
    public int getCount() {
        if(orderList != null)
            return orderList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder ;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.row_item_product,null,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_product_name = (TextView)convertView.findViewById(R.id.product_name);
            viewHolder.ck_isPicked = (CheckBox)convertView.findViewById(R.id.ck_isPicked);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tv_product_name.setText(orderList.get(position).getCatalogName());
        viewHolder.ck_isPicked.setChecked(orderList.get(position).isPicked());
        return convertView ;
    }


    public static class ViewHolder{
        public TextView tv_product_name ;
        public CheckBox ck_isPicked ;
    }
}
