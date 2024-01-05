package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.request_response.ChartDataResponse;
import com.example.baitaplon.request_response.DataBarChart;
import com.example.baitaplon.request_response.MoneyOrderResponse;
import com.example.baitaplon.util.Const;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EconomicActivity extends AppCompatActivity {

    String token;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    BarChart barChart;
    TextView order, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic);

        setSupportActionBar(findViewById(R.id.pl_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Economic");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error token");

        order = findViewById(R.id.textOrder);
        money = findViewById(R.id.textMoney);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        Intent productlist = new Intent(EconomicActivity.this,ProductListActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("token", token);
                        productlist.putExtras(bundle1);
                        startActivity(productlist);
                        break;
                    case R.id.paymentmenu:
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(EconomicActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo", Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        Intent home = new Intent(EconomicActivity.this,MainActivity.class);
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
                Intent order = new Intent(EconomicActivity.this,OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                order.putExtras(bundle);
                startActivity(order);
            }
        });

        barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> arrayData = new ArrayList<>();

        UserClient client = RetrofitService.createService(UserClient.class);
        Call<ChartDataResponse> call = client.GetChartData(token);
        call.enqueue(new Callback<ChartDataResponse>() {
            @Override
            public void onResponse(Call<ChartDataResponse> call, Response<ChartDataResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    for(DataBarChart dataBarChart: response.body().getBody()) {
                        arrayData.add(new BarEntry(dataBarChart.getKey(),dataBarChart.getValue()));
                    }
                    BarDataSet barDataSet = new BarDataSet(arrayData,"Money");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(10f);
                    barDataSet.setDrawValues(false);
                    BarData barData = new BarData(barDataSet);
                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Bar chart");
                    barChart.animateY(2000);
                } else {
                    Toast.makeText(EconomicActivity.this, "Error get data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ChartDataResponse> call, Throwable t) {
                Toast.makeText(EconomicActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<MoneyOrderResponse> call2 = client.GetTotalOder(token,Const.getUser().getId());
        call2.enqueue(new Callback<MoneyOrderResponse>() {
            @Override
            public void onResponse(Call<MoneyOrderResponse> call, Response<MoneyOrderResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    order.setText("Total Orders\n" + response.body().getBody());
                } else {
                    Toast.makeText(EconomicActivity.this, "Error get data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MoneyOrderResponse> call, Throwable t) {
                Toast.makeText(EconomicActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<MoneyOrderResponse> call3 = client.GetTotalMoney(token,Const.getUser().getId());
        call3.enqueue(new Callback<MoneyOrderResponse>() {
            @Override
            public void onResponse(Call<MoneyOrderResponse> call, Response<MoneyOrderResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    money.setText("Total Money\n" + response.body().getBody());
                } else {
                    Toast.makeText(EconomicActivity.this, "Error get data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MoneyOrderResponse> call, Throwable t) {
                Toast.makeText(EconomicActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}