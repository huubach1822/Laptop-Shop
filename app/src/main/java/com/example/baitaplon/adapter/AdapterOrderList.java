package com.example.baitaplon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import com.example.baitaplon.DetailProductActivity;
import com.example.baitaplon.MainActivity;
import com.example.baitaplon.OrderActivity;
import com.example.baitaplon.ProductListActivity;
import com.example.baitaplon.R;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Order;
import com.example.baitaplon.entity.OrderCart;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.OrderListResponse;
import com.example.baitaplon.request_response.ProductListResponse;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOrderList extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<Order> data;
    private LayoutInflater inflater;
    private ArrayList<Order> databackup;
    private SearchView searchView;
    String token;

    public AdapterOrderList(Activity activity, ArrayList<Order> data, SearchView searchView, String token) {
        this.activity = activity;
        this.data = data;
        databackup = data;
        inflater = (LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.searchView = searchView;
        this.token = token;
        searchView.setQuery("",true);
    }


    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
        {
            v = inflater.inflate(R.layout.order_layout, null);
        }

        TextView id = v.findViewById(R.id.orderID);
        id.setText("ID: " + data.get(position).getId().toString());

        TextView name = v.findViewById(R.id.orderName);
        name.setText(Objects.toString(data.get(position).getUsername(), ""));

        TextView status = v.findViewById(R.id.orderStatus);
        status.setText("Status: " + data.get(position).getStatus());

        TextView total = v.findViewById(R.id.orderTotal);
        total.setText("Total: " + data.get(position).getTotalPrice());

        TextView pay = v.findViewById(R.id.orderPayMethod);
        pay.setText("Payment Method: " + data.get(position).getPaymentMethod());

        String s = "";
        HashMap<Integer, Pair<Integer,String>> hashMap = new HashMap<>();
        for(OrderCart orderCart: data.get(position).getOrderCarts()) {
            Integer total1;
            String name1;
            Pair<Integer,String> pair;
            if(hashMap.containsKey(orderCart.getProductId())) {

                total1 = hashMap.get(orderCart.getProductId()).first+1;
                name1 = hashMap.get(orderCart.getProductId()).second;
                pair = new Pair<>(total1,name1);
                hashMap.put(orderCart.getProductId(), pair);

            } else {
                total1 = 1;
                name1 = orderCart.getProduct().getTitle();
                pair = new Pair<>(total1,name1);
                hashMap.put(orderCart.getProductId(),pair);
            }
        }

        for (Map.Entry<Integer, Pair<Integer,String>> set :
                hashMap.entrySet()) {
           s += set.getValue().second + " x" + set.getValue().first.toString() + "\n";
        }


        TextView listProduct = v.findViewById(R.id.listProduct);
        listProduct.setText(s);

        Button btnConfirm = v.findViewById(R.id.btnConfirm);
        if(data.get(position).getStatus().equals("COMPLETED")) {
            btnConfirm.setVisibility(View.GONE);
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setStatus("COMPLETED");
                UserClient client = RetrofitService.createService(UserClient.class);
                Call<MessageResponse> call = client.UpdateOrder(token,data.get(position));
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){

                            databackup.remove(data.get(position));
                            data = databackup;
                            searchView.setQuery("",true);
                            notifyDataSetChanged();
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error confirm order", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return v;
    }
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();

                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                else
                {
                    ArrayList<Order> newdata = new ArrayList<>();
                    for(Order u:databackup)
                    {
                        if(u!=null&&u.getUsername()!=null&&u.getUsername().length()!=0){
                            if(u.getUsername().toLowerCase().contains(
                                    charSequence.toString().toLowerCase()))
                                newdata.add(u);
                        }
                    }
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<Order>();
                ArrayList<Order> tmp =(ArrayList<Order>)filterResults.values;
                for(Order u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }

}
