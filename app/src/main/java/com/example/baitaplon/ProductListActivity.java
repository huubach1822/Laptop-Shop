package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.adapter.AdapterProductList;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.request_response.ProductListResponse;
import com.example.baitaplon.util.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    AdapterProductList adapter;
    ListView listView;
    SearchView searchView;
    Button btnThem;
    String token;
    TextView remainProduct,soldProduct;
    UserClient client = RetrofitService.createService(UserClient.class);
    int totalremain, totalsold;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        setSupportActionBar(findViewById(R.id.pl_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product List");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error token");

        listView = findViewById(R.id.lv_ProductList);
        searchView = findViewById(R.id.sv_Product);
        btnThem = findViewById(R.id.btnLogOut);
        remainProduct = findViewById(R.id.textView);
        soldProduct = findViewById(R.id.textView2);

        Call<ProductListResponse> call = client.GetProductList(token,0);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if(response.isSuccessful()){
                    ProductListResponse data = response.body();
                    products = data.getBody();
                    adapter = new AdapterProductList(ProductListActivity.this,products,searchView,token,remainProduct, soldProduct);
                    listView.setAdapter(adapter);
                    totalremain = 0;
                    totalsold = 0;
                    for(Product p: products) {
                        totalremain += p.getRemain();
                        totalsold += p.getSold();
                    }
                    remainProduct.setText(String.valueOf(totalremain));
                    soldProduct.setText(String.valueOf(totalsold));
                } else {
                    Toast.makeText(ProductListActivity.this, "Error get product data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("",true);
                Intent addproduct = new Intent(ProductListActivity.this,AddProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                addproduct.putExtras(bundle);
                startActivityForResult(addproduct,200);

            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.paymentmenu:
                        Intent payment = new Intent(ProductListActivity.this, EconomicActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("token", token);
                        payment.putExtras(bundle4);
                        startActivity(payment);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(ProductListActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo", Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        Intent home = new Intent(ProductListActivity.this,MainActivity.class);
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
                Intent order = new Intent(ProductListActivity.this,OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                order.putExtras(bundle);
                startActivity(order);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 || requestCode == 201){
            Call<ProductListResponse> call = client.GetProductList(token,0);
            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                    if(response.isSuccessful()){

                        ProductListResponse data = response.body();
                        products = data.getBody();

                        adapter = new AdapterProductList(ProductListActivity.this,products,searchView,token,remainProduct,soldProduct);
                        listView.setAdapter(adapter);

                        totalremain = 0;
                        totalsold = 0;
                        for(Product p: products) {
                            totalremain += p.getRemain();
                            totalsold += p.getSold();
                        }
                        remainProduct.setText(String.valueOf(totalremain));
                        soldProduct.setText(String.valueOf(totalsold));

                    } else {
                        Toast.makeText(ProductListActivity.this, "Error get product data", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    Toast.makeText(ProductListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}