package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.User;
import com.example.baitaplon.request_response.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    ProgressDialog progressDialog;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogOut);
        etUsername = findViewById(R.id.LoginUsername);
        etPassword = findViewById(R.id.LoginPassword);

        etUsername.setText("1234");
        etPassword.setText("123456");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.getText().length()==0) {
                    Toast.makeText(LoginActivity.this, "Username can't be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etPassword.getText().length()==0) {
                    Toast.makeText(LoginActivity.this, "Password can't be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging in please wait");
                progressDialog.show();
                UserClient client = RetrofitService.createService(UserClient.class);
                Call<User> call = client.Login(new LoginRequest(etUsername.getText().toString(),etPassword.getText().toString()));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()&&response.body()!=null){
                            token = response.body().getAccessToken();
                            token = "Bearer " + token;
                            Intent main = new Intent(LoginActivity.this,MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("token", token);
                            main.putExtras(bundle);
                            main.putExtra("userInfo",response.body());
                            startActivity(main);

                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

    }
}