package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Role;
import com.example.baitaplon.entity.User;
import com.example.baitaplon.request_response.AddDeviceRequest;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.util.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnDonhang, btnTaiChinh, btnSanPham, btnNoti, layoutAdmin, btnListUser;
    ImageView btnUserInfo, imgAvatar;
    String token;
    User user;
    TextView name, role;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChanel();
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/rb_all_dev")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("userInfo");
        Const.setUser(user);

        btnDonhang = findViewById(R.id.btnDonHang);
        btnTaiChinh = findViewById(R.id.btnTaiChinh);
        btnSanPham = findViewById(R.id.btnSanPham);
        btnUserInfo = findViewById(R.id.btnUserInfo);
        imgAvatar = findViewById(R.id.imgAvatar);
        name = findViewById(R.id.tvName);
        role = findViewById(R.id.tvRole);
        btnNoti = findViewById(R.id.btnSendNoti);
        layoutAdmin = findViewById(R.id.LayoutGroup2);
        btnListUser = findViewById(R.id.btnListUser);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.productmenu:
                        Intent productlist = new Intent(MainActivity.this,ProductListActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("token", token);
                        productlist.putExtras(bundle1);
                        startActivity(productlist);
                        break;
                    case R.id.paymentmenu:
                        Intent payment = new Intent(MainActivity.this, EconomicActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("token", token);
                        payment.putExtras(bundle4);
                        startActivity(payment);
                        break;
                    case R.id.accoutnmenu:
                        Intent userInfo = new Intent(MainActivity.this,UserInfoActivity.class);
                        userInfo.putExtra("userInfo",Const.getUser());
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("token",token);
                        bundle3.putBoolean("checkLogOut",true);
                        userInfo.putExtras(bundle3);
                        startActivity(userInfo);
                        break;
                    case R.id.homemenu:
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order = new Intent(MainActivity.this,OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                order.putExtras(bundle);
                startActivity(order);
            }
        });

        name.setText(user.getName());
        for(Role r: user.getRoles()){
            if(r.getTitle().equals("Admin")) {
                user.setRoleUser("Admin");
                break;
            }
        }
        role.setText(user.getRoleUser());
        Picasso.with(this).load(user.getImageUrl()).into(imgAvatar);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("firebase", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        Log.d("token",task.getResult());
                        UserClient client = RetrofitService.createService(UserClient.class);
                        AddDeviceRequest addDeviceRequest = new AddDeviceRequest(user.getId().toString(), task.getResult(),
                                "1.0", String.valueOf(android.os.Build.VERSION.SDK_INT));
                        Call<MessageResponse> call1 = client.AddDevice(token,addDeviceRequest);
                        call1.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                if(response.isSuccessful()){

                                } else {
                                    Toast.makeText(MainActivity.this, "Error add device", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

        btnDonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDonhang.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_item));

                Intent order = new Intent(MainActivity.this,OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                order.putExtras(bundle);
                startActivity(order);
            }
        });
        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSanPham.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_item));

                Intent productlist = new Intent(MainActivity.this,ProductListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                productlist.putExtras(bundle);
                startActivity(productlist);

            }
        });
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userInfo = new Intent(MainActivity.this,UserInfoActivity.class);
                userInfo.putExtra("userInfo",user);
                Bundle bundle = new Bundle();
                bundle.putString("token",token);
                bundle.putBoolean("checkLogOut",true);
                userInfo.putExtras(bundle);
                startActivity(userInfo);
            }
        });
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNoti.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_item));
                if(user.getRoleUser()=="Admin") {
                    Intent noti = new Intent(MainActivity.this, NotificationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("token", token);
                    noti.putExtras(bundle);
                    startActivity(noti);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Message");
                    alertDialogBuilder.setMessage("You don't have permission");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        btnListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnListUser.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_item));
                if(user.getRoleUser()=="Admin") {
                    Intent userlist = new Intent(MainActivity.this, UserListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("token", token);
                    userlist.putExtras(bundle);
                    startActivity(userlist);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Message");
                    alertDialogBuilder.setMessage("You don't have permission");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        btnTaiChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTaiChinh.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_item));

                Intent payment = new Intent(MainActivity.this, EconomicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                payment.putExtras(bundle);
                startActivity(payment);
            }
        });
    }
    private void createNotificationChanel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("my_chanel","Chanel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is my chanel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}