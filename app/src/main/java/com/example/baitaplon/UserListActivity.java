package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplon.adapter.AdapterUserList;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.User;
import com.example.baitaplon.request_response.UserListResponse;
import com.example.baitaplon.util.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<>();
    AdapterUserList adapter;
    ListView listView;
    SearchView searchView;
    String token;
    UserClient client = RetrofitService.createService(UserClient.class);

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setSupportActionBar(findViewById(R.id.pl_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List User");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");

        listView = findViewById(R.id.lv_User);
        searchView = findViewById(R.id.sv_ListUser);

        Call<UserListResponse> call = client.GetUserList(token,0,false);
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if(response.isSuccessful()){

                    UserListResponse data = response.body();
                    users = data.getBody();

                    adapter = new AdapterUserList(UserListActivity.this,users,searchView,token);
                    listView.setAdapter(adapter);

                } else {
                    Toast.makeText(UserListActivity.this, "Error get user data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Toast.makeText(UserListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        Intent productlist = new Intent(UserListActivity.this,ProductListActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("token", token);
                        productlist.putExtras(bundle1);
                        startActivity(productlist);
                        break;
                    case R.id.paymentmenu:
                        Intent payment = new Intent(UserListActivity.this, EconomicActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("token", token);
                        payment.putExtras(bundle4);
                        startActivity(payment);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(UserListActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo", Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        Intent home = new Intent(UserListActivity.this,MainActivity.class);
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
                Intent order = new Intent(UserListActivity.this,OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                order.putExtras(bundle);
                startActivity(order);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


}