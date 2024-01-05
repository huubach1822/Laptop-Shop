package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.request_response.DeviceList;
import com.example.baitaplon.request_response.DeviceListResponse;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.NotificationRequest;
import com.example.baitaplon.util.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    EditText title, content;
    RadioButton one, all;
    Button btnOk;
    AutoCompleteTextView userlist;
    TextInputLayout userListLayout;
    String token, firebaseToken;
    Integer userID;
    UserClient client = RetrofitService.createService(UserClient.class);
    ArrayList<DeviceList> deviceList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Send Notification");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");

        title = findViewById(R.id.notiTitle);
        content = findViewById(R.id.notiContent);
        one = findViewById(R.id.radio_one);
        all = findViewById(R.id.radio_all);
        userlist = findViewById(R.id.UserList);
        userlist.setEnabled(false);
        userListLayout = findViewById(R.id.UserListLayout);
        userListLayout.setEnabled(false);
        btnOk = findViewById(R.id.btnOK);

        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userlist.setEnabled(b);
                userListLayout.setEnabled(b);
            }
        });

        Call<DeviceListResponse> call = client.GetDeviceList();
        call.enqueue(new Callback<DeviceListResponse>() {
            @Override
            public void onResponse(Call<DeviceListResponse> call, Response<DeviceListResponse> response) {
                if(response.isSuccessful()){

                    DeviceListResponse data = response.body();
                    deviceList = data.getBody();

                    ArrayAdapter<DeviceList> locationAdapter = new ArrayAdapter<>(NotificationActivity.this, android.R.layout.simple_list_item_1,deviceList);
                    userlist.setAdapter(locationAdapter);

                } else {
                    Toast.makeText(NotificationActivity.this, "Error get user data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DeviceListResponse> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.length()!=0&&content.length()!=0){
                    if(all.isChecked()) {
                        Call<MessageResponse> call = client.PushNotification(
                                new NotificationRequest(title.getText().toString(),content.getText().toString(),"",1)
                        );
                        call.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(NotificationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NotificationActivity.this, "Error push notification", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<MessageResponse> call = client.PushNotification(
                                new NotificationRequest(title.getText().toString(),content.getText().toString(),firebaseToken,0)
                        );
                        call.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(NotificationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NotificationActivity.this, "Error push notification", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(NotificationActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userID = deviceList.get(i).getUser().getId();
                firebaseToken = deviceList.get(i).getFirebaseToken();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        Intent productlist = new Intent(NotificationActivity.this,ProductListActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("token", token);
                        productlist.putExtras(bundle1);
                        startActivity(productlist);
                        break;
                    case R.id.paymentmenu:
                        Intent payment = new Intent(NotificationActivity.this, EconomicActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("token", token);
                        payment.putExtras(bundle4);
                        startActivity(payment);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(NotificationActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo", Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        Intent home = new Intent(NotificationActivity.this,MainActivity.class);
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
                Intent order = new Intent(NotificationActivity.this,OrderActivity.class);
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