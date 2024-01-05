package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.baitaplon.adapter.AdapterProductList;
import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Category;
import com.example.baitaplon.entity.Location;
import com.example.baitaplon.entity.Media;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.entity.Supplier;
import com.example.baitaplon.request_response.CategoryResponse;
import com.example.baitaplon.request_response.ImageResponse;
import com.example.baitaplon.request_response.LocationResponse;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.ProductListResponse;
import com.example.baitaplon.request_response.SupplierResponse;
import com.example.baitaplon.util.RealPathUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    EditText name, id, price, remain, sold, discount, description;
    AutoCompleteTextView location, supplier, category;
    Button btnOk, btnCancel;
    ImageView imgProduct;
    RatingBar ratingBar;
    UserClient client = RetrofitService.createService(UserClient.class);
    ArrayList<Category> arrayCategory = new ArrayList<>();
    ArrayList<Supplier> arraySupplier = new ArrayList<>();
    ArrayList<Location> arrayLocation = new ArrayList<>();
    Uri imageUri;
    int locationID, supplierID, categoryID;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Product");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");

        name = findViewById(R.id.ProductNameAdd);
        id = findViewById(R.id.ProductIDAdd);
        id.setText("Auto");
        id.setEnabled(false);
        price= findViewById(R.id.ProductPriceAdd);
        remain = findViewById(R.id.ProductRemainAdd);
        sold = findViewById(R.id.ProductSoldAdd);
        discount = findViewById(R.id.ProductPriceDiscountAdd);
        description = findViewById(R.id.ProductDescriptionAdd);
        location = findViewById(R.id.ProductLocationAdd);
        supplier = findViewById(R.id.ProductSupplierAdd);
        category = findViewById(R.id.ProductCategoryAdd);
        btnCancel = findViewById(R.id.btnCancelAdd);
        btnOk = findViewById(R.id.btnOKAdd);
        imgProduct = findViewById(R.id.imgProductAdd);
        ratingBar = findViewById(R.id.ProductRatingAdd);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Call<CategoryResponse> callGetCategory = client.GetCategory();
        callGetCategory.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){

                    CategoryResponse data = response.body();
                    arrayCategory = data.getBody();
                    ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_list_item_1,arrayCategory);
                    category.setAdapter(categoryAdapter);

                } else {
                    Toast.makeText(AddProductActivity.this, "Error get category data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<SupplierResponse> callGetSupplier = client.GetSupplier();
        callGetSupplier.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if(response.isSuccessful()){

                    SupplierResponse data = response.body();
                    arraySupplier = data.getBody();
                    ArrayAdapter<Supplier> supplierAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_list_item_1,arraySupplier);
                    supplier.setAdapter(supplierAdapter);

                } else {
                    Toast.makeText(AddProductActivity.this, "Error get supplier data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<LocationResponse> callGetLocation = client.GetLocation();
        callGetLocation.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if(response.isSuccessful()){

                    LocationResponse data = response.body();
                    arrayLocation = data.getBody();
                    ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_list_item_1,arrayLocation);
                    location.setAdapter(locationAdapter);

                } else {
                    Toast.makeText(AddProductActivity.this, "Error get location data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()!=0&&id.getText().length()!=0&&price.getText().length()!=0&&remain.getText().length()!=0&&
                        sold.getText().length()!=0&&discount.getText().length()!=0&&description.getText().length()!=0&&location.getText().length()!=0&&
                        supplier.getText().length()!=0&&category.getText().length()!=0&&ratingBar.getRating()!=0&&imageUri!=null) {

                    String path = RealPathUtil.getRealPath(AddProductActivity.this,imageUri);
                    File file = new File(path);

                    RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part multipartBodyImage = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);

                    Call<ImageResponse> uploadImageCall = client.UploadImage(multipartBodyImage);
                    uploadImageCall.enqueue(new Callback<ImageResponse>() {
                        @Override
                        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                            if (response.isSuccessful()) {
                                int imageID = response.body().getBody().getId();
                                Product product = new Product();
                                product.setTitle(name.getText().toString());
                                product.setDescription(description.getText().toString());
                                product.setMediaId(imageID);
                                product.setPrice(Long.parseLong(price.getText().toString()));
                                product.setPriceAfterDiscount(Long.parseLong(discount.getText().toString()));
                                product.setCategoryId(categoryID);
                                product.setRemain(Integer.parseInt(remain.getText().toString()));
                                product.setRating((int) ratingBar.getRating());
                                product.setSupplierId(supplierID);
                                product.setLocationId(locationID);
                                product.setSold(Integer.parseInt(sold.getText().toString()));
                                Call<MessageResponse> addProductCall = client.AddProduct(token,product);
                                addProductCall.enqueue(new Callback<MessageResponse>() {
                                    @Override
                                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                        if(response.isSuccessful()){
                                            finish();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Error add product", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                                        Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(AddProductActivity.this, "Error upload image", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ImageResponse> call, Throwable t) {
                            Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(AddProductActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });


        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        supplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                supplierID = arraySupplier.get(i).getId();
            }
        });

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locationID = arrayLocation.get(i).getId();
            }
        });

        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryID = arrayCategory.get(i).getId();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK && data.getData() != null) {
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                imageUri = uri;
                Picasso.with(this).load(uri).into(imgProduct);

            } else {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

