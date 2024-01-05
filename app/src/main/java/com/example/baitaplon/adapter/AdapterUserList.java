package com.example.baitaplon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.baitaplon.R;
import com.example.baitaplon.UserInfoActivity;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.entity.Role;
import com.example.baitaplon.entity.User;
import com.example.baitaplon.request_response.MessageResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterUserList extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<User> data;
    private LayoutInflater inflater;
    private ArrayList<User> databackup;
    private SearchView searchView;
    String token;

    public AdapterUserList(Activity activity, ArrayList<User> data, SearchView searchView, String token) {
        this.activity = activity;
        this.data = data;
        databackup = data;
        inflater = (LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.searchView = searchView;
        this.token = token;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
        {
            v = inflater.inflate(R.layout.user_layout, null);
        }

        ImageView image = v.findViewById(R.id.imgUser);
        Picasso.with(activity).load(data.get(position).getImageUrl()).into(image);

        TextView name = v.findViewById(R.id.nameUser);
        name.setText(data.get(position).getName());

        TextView email = v.findViewById(R.id.emailUser);
        email.setText(data.get(position).getEmail());

        TextView role = v.findViewById(R.id.roleUser);
        for(Role r: data.get(position).getRoles()){
            if(r.getTitle().equals("Admin")) {
                data.get(position).setRoleUser("Admin");
                break;
            }
        }
        role.setText("Role: " + data.get(position).getRoleUser());

        TextView phone = v.findViewById(R.id.phoneUser);
        phone.setText("Phone: " + data.get(position).getPhone());

        Button btnChiTiet = v.findViewById(R.id.btnCapNhap);
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("",true);

                Intent userInfo = new Intent(activity, UserInfoActivity.class);
                userInfo.putExtra("userInfo",data.get(position));
                Bundle bundle = new Bundle();
                bundle.putString("token",token);
                bundle.putBoolean("checkLogOut",false);
                userInfo.putExtras(bundle);
                activity.startActivity(userInfo);

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
                    ArrayList<User> newdata = new ArrayList<>();
                    for(User u:databackup)
                        if(u.getName().toLowerCase().contains(
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
                data = new ArrayList<User>();
                ArrayList<User> tmp =(ArrayList<User>)filterResults.values;
                for(User u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}

