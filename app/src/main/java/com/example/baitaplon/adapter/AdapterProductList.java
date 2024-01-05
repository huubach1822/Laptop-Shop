package com.example.baitaplon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import com.example.baitaplon.DetailProductActivity;
import com.example.baitaplon.MainActivity;
import com.example.baitaplon.ProductListActivity;
import com.example.baitaplon.R;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.ProductListResponse;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProductList extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<Product> data;
    private LayoutInflater inflater;
    private ArrayList<Product> databackup;
    private SearchView searchView;
    TextView remainProduct, soldProduct;
    String token;
    int totalremain, totalsold;
    public AdapterProductList(Activity activity, ArrayList<Product> data, SearchView searchView, String token,
                              TextView remain, TextView sold) {
        this.activity = activity;
        this.data = data;
        databackup = data;
        inflater = (LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.searchView = searchView;
        this.token = token;
        this.remainProduct = remain;
        this.soldProduct = sold;
    }

    public ArrayList<Product> getData(){
        return data;
    }

    public void restAllitem() {
        data = new ArrayList<Product>(databackup);
        this.notifyDataSetChanged();
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
            v = inflater.inflate(R.layout.product_layout, null);
        }

        ImageView image = v.findViewById(R.id.imgProduct);
        Picasso.with(activity).load(data.get(position).getImageUrl()).into(image);

        TextView title = v.findViewById(R.id.titleProduct);
        title.setText(data.get(position).getTitle());

        TextView num = v.findViewById(R.id.numProduct);
        num.setText("Slg: " + String.valueOf(data.get(position).getRemain()));

        TextView supp = v.findViewById(R.id.supplierProduct);
        supp.setText(data.get(position).getSupplier().getTitle());

        TextView price = v.findViewById(R.id.priceProduct);
        price.setText("Giá: " + String.valueOf(data.get(position).getPrice()));

        TextView des = v.findViewById(R.id.desProduct);
        des.setText(data.get(position).getDescription());

        Button btnXoa = v.findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Dialog box");
                alertDialogBuilder.setMessage("Do you want to delete this product");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        searchView.setQuery("",true);
                        UserClient client = RetrofitService.createService(UserClient.class);
                        Call<MessageResponse> call = client.DeleteProduct(data.get(position).getId());
                        call.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    databackup.remove(data.get(position));
                                    data = databackup;
                                    notifyDataSetChanged();
                                    totalremain = 0;
                                    totalsold = 0;
                                    for(Product p: databackup) {
                                        totalremain += p.getRemain();
                                        totalsold += p.getSold();
                                    }
                                    remainProduct.setText(String.valueOf(totalremain));
                                    soldProduct.setText(String.valueOf(totalsold));
                                } else {
                                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        Button btnChiTiet = v.findViewById(R.id.btnCapNhap);
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("",true);

                Intent detailProduct = new Intent(activity,DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                detailProduct.putExtras(bundle);
                detailProduct.putExtra("product",data.get(position));
                activity.startActivityForResult(detailProduct,201);

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
                    ArrayList<Product> newdata = new ArrayList<>();
                    for(Product u:databackup)
                        if(u.getTitle().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<Product>();
                ArrayList<Product> tmp =(ArrayList<Product>)filterResults.values;
                for(Product u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
