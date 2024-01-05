package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.baitaplon.entity.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class UserInfoActivity extends AppCompatActivity {

    Button LogOut;
    EditText name, phone, email, password, role, gender, birthday;
    User user;
    String token;
    ImageView imgAvatar;
    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");
        check = bundle.getBoolean("checkLogOut");
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("userInfo");

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Info");

        LogOut = findViewById(R.id.btnLogOut);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
            }
        });

        if(!check) {
            LogOut.setVisibility(View.GONE);
        }

        name = findViewById(R.id.UserInfoName);
        phone = findViewById(R.id.UserInfoPhone);
        email = findViewById(R.id.UserInfoEmail);
        password = findViewById(R.id.UserInfoPassword);
        role = findViewById(R.id.UserInfoRole);
        gender = findViewById(R.id.UserInfoGender);
        birthday = findViewById(R.id.UserInfoBirthday);
        imgAvatar = findViewById(R.id.imageView7);

        name.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);
        role.setEnabled(false);
        gender.setEnabled(false);
        birthday.setEnabled(false);

        name.setText(user.getName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        role.setText(user.getRoleUser());
        gender.setText(Objects.toString(user.getGender(), ""));
        birthday.setText(Objects.toString(user.getBirthday(), ""));
        Picasso.with(this).load(user.getImageUrl()).into(imgAvatar);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}