package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.baitaplon.adapter.AdapterOrderList;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Order;
import com.example.baitaplon.request_response.OrderListResponse;
import com.example.baitaplon.util.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    UserClient client = RetrofitService.createService(UserClient.class);
    String token;
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<Order> ordersbackup = new ArrayList<>();
    ListView listView;
    SearchView searchView;
    AdapterOrderList adapter;
    RadioGroup radioGroup;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    RadioButton a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");
        listView = findViewById(R.id.lv_Order);
        searchView = findViewById(R.id.sv_Order);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setEnabled(false);
        a = findViewById(R.id.radio_a);
        b = findViewById(R.id.radio_b);
        a.setEnabled(false);
        b.setEnabled(false);

        setSupportActionBar(findViewById(R.id.pl_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        Intent productlist = new Intent(OrderActivity.this,ProductListActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("token", token);
                        productlist.putExtras(bundle1);
                        startActivity(productlist);
                        break;
                    case R.id.paymentmenu:
                        Intent payment = new Intent(OrderActivity.this, EconomicActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("token", token);
                        payment.putExtras(bundle4);
                        startActivity(payment);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(OrderActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo", Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        Intent home = new Intent(OrderActivity.this,MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        home.putExtra("userInfo", Const.getUser());
                        bundle2.putString("token", token);
                        home.putExtras(bundle2);
                        startActivity(home);
                        break;
                }
                return false;
            }
        });
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        Call<OrderListResponse> call = client.GetOrderList(token);
        call.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                if(response.isSuccessful()){

                    ordersbackup = response.body().getBody();
                    for(Order o: ordersbackup) {
                        if(o.getStatus().equals("WAITING")) {
                            orders.add(o);
                        }
                    }
                    adapter = new AdapterOrderList(OrderActivity.this,orders,searchView,token);
                    listView.setAdapter(adapter);
                    a.setEnabled(true);
                    b.setEnabled(true);

                } else {
                    Toast.makeText(OrderActivity.this, "Error get product data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null)
                    adapter.getFilter().filter(newText.toString());
                return false;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_a) {
                    orders = new ArrayList<>();
                    for(Order o: ordersbackup) {
                        if(o.getStatus().equals("WAITING")) {
                            orders.add(o);
                        }
                    }
                    adapter = new AdapterOrderList(OrderActivity.this,orders,searchView,token);
                    listView.setAdapter(adapter);
                }
                if(checkedId == R.id.radio_b) {
                    orders = new ArrayList<>();
                    for(Order o: ordersbackup) {
                        if(o.getStatus().equals("COMPLETED")) {
                            orders.add(o);
                        }
                    }
                    adapter = new AdapterOrderList(OrderActivity.this,orders,searchView,token);
                    listView.setAdapter(adapter);
                }
            }
        });

    }



    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}