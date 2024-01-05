package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.baitaplon.client_service.RetrofitService;
import com.example.baitaplon.client_service.UserClient;
import com.example.baitaplon.entity.Category;
import com.example.baitaplon.entity.Location;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.entity.Supplier;
import com.example.baitaplon.request_response.CategoryResponse;
import com.example.baitaplon.request_response.ImageResponse;
import com.example.baitaplon.request_response.LocationResponse;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.SupplierResponse;
import com.example.baitaplon.util.RealPathUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    EditText name, id, price, remain, sold, discount, description;
    AutoCompleteTextView location, supplier, category;
    TextInputLayout locationLayout, supplierLayout, categoryLayout;
    Button btnOk, btnCancel;
    ImageView btnEdit, imgProduct;
    RatingBar ratingBar;
    UserClient client = RetrofitService.createService(UserClient.class);
    ArrayList<Category> arrayCategory = new ArrayList<>();
    ArrayList<Supplier> arraySupplier = new ArrayList<>();
    ArrayList<Location> arrayLocation = new ArrayList<>();
    Uri imageUri = null;
    String token;
    int locationID, supplierID, categoryID;

    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token", "Error");

        name = findViewById(R.id.ProductName);
        id = findViewById(R.id.ProductID);
        id.setEnabled(false);
        price= findViewById(R.id.ProductPrice);
        remain = findViewById(R.id.ProductRemain);
        sold = findViewById(R.id.ProductSold);
        discount = findViewById(R.id.ProductPriceDiscount);
        description = findViewById(R.id.ProductDescription);
        supplier = findViewById(R.id.ProductSupplier);
        supplierLayout = findViewById(R.id.SupplierLayout);
        location = findViewById(R.id.ProductLocation);
        locationLayout = findViewById(R.id.LocationLayout);
        category = findViewById(R.id.ProductCategory);
        categoryLayout = findViewById(R.id.CategoryLayout);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOK);
        imgProduct = findViewById(R.id.imgProduct);
        btnEdit = findViewById(R.id.btnEdit);
        ratingBar = findViewById(R.id.ProductRating);

        Intent i = getIntent();
        product = (Product)i.getSerializableExtra("product");

        locationID = product.getLocationId();
        supplierID = product.getSupplierId();
        categoryID = product.getCategoryId();

        Call<CategoryResponse> callGetCategory = client.GetCategory();
        callGetCategory.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){

                    CategoryResponse data = response.body();
                    arrayCategory = data.getBody();
                    ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arrayCategory);
                    category.setAdapter(categoryAdapter);

                } else {
                    Toast.makeText(DetailProductActivity.this, "Error get category data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<SupplierResponse> callGetSupplier = client.GetSupplier();
        callGetSupplier.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if(response.isSuccessful()){

                    SupplierResponse data = response.body();
                    arraySupplier = data.getBody();
                    ArrayAdapter<Supplier> supplierAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arraySupplier);
                    supplier.setAdapter(supplierAdapter);

                } else {
                    Toast.makeText(DetailProductActivity.this, "Error get supplier data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<LocationResponse> callGetLocation = client.GetLocation();
        callGetLocation.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if(response.isSuccessful()){

                    LocationResponse data = response.body();
                    arrayLocation = data.getBody();
                    ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arrayLocation);
                    location.setAdapter(locationAdapter);

                } else {
                    Toast.makeText(DetailProductActivity.this, "Error get location data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        setProductInfo(product);
        setEnableDisable(false);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEnableDisable(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableDisable(false);
                setProductInfo(product);
            }
        });

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()!=0&&id.getText().length()!=0&&price.getText().length()!=0&&remain.getText().length()!=0&&
                        sold.getText().length()!=0&&discount.getText().length()!=0&&description.getText().length()!=0&&location.getText().length()!=0&&
                        supplier.getText().length()!=0&&category.getText().length()!=0&&ratingBar.getRating()!=0) {

                    Product product1 =new Product();
                    product1.setId(Integer.parseInt(id.getText().toString()));
                    product1.setTitle(name.getText().toString());
                    product1.setDescription(description.getText().toString());
                    product1.setPrice(Long.parseLong(price.getText().toString()));
                    product1.setPriceAfterDiscount(Long.parseLong(discount.getText().toString()));
                    product1.setCategoryId(categoryID);
                    product1.setRemain(Integer.parseInt(remain.getText().toString()));
                    product1.setRating((int) ratingBar.getRating());
                    product1.setSupplierId(supplierID);
                    product1.setLocationId(locationID);
                    product1.setSold(Integer.parseInt(sold.getText().toString()));
                    if(imageUri!=null) {
                        String path = RealPathUtil.getRealPath(DetailProductActivity.this, imageUri);
                        File file = new File(path);

                        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part multipartBodyImage = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);

                        Call<ImageResponse> uploadImageCall = client.UploadImage(multipartBodyImage);
                        uploadImageCall.enqueue(new Callback<ImageResponse>() {
                            @Override
                            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                                if (response.isSuccessful()) {
                                    int imageID = response.body().getBody().getId();
                                    product1.setMediaId(imageID);
                                    Call<MessageResponse> updateProductCall = client.UpdateProduct(token, product1);
                                    updateProductCall.enqueue(new Callback<MessageResponse>() {
                                        @Override
                                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                            if (response.isSuccessful()) {
                                                finish();
                                            } else {
                                                Toast.makeText(DetailProductActivity.this, "Error update product", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                                            Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(DetailProductActivity.this, "Error upload image", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ImageResponse> call, Throwable t) {
                                Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        product1.setMediaId(product.getMediaId());
                        Call<MessageResponse> updateProductCall = client.UpdateProduct(token, product1);
                        updateProductCall.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                if (response.isSuccessful()) {
                                    finish();
                                } else {
                                    Toast.makeText(DetailProductActivity.this, "Error update product", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    setEnableDisable(false);
                } else {
                    Toast.makeText(DetailProductActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void setProductInfo(Product product){
        name.setText(product.getTitle());
        id.setText(product.getId().toString());
        price.setText(product.getPrice().toString());
        remain.setText(product.getRemain().toString());
        sold.setText(product.getSold().toString());
        discount.setText(product.getPriceAfterDiscount().toString());
        description.setText(product.getDescription());
        location.setText(product.getLocation().getAddress());
        supplier.setText(product.getSupplier().getTitle());
        category.setText(product.getCategory().getTitle());
        ratingBar.setRating(product.getRating());
        Picasso.with(this).load(product.getImageUrl()).into(imgProduct);
    }

    public void setEnableDisable(boolean check){
        name.setEnabled(check);
        price.setEnabled(check);
        remain.setEnabled(check);
        sold.setEnabled(check);
        discount.setEnabled(check);
        description.setEnabled(check);
        supplier.setEnabled(check);
        supplierLayout.setEnabled(check);
        location.setEnabled(check);
        locationLayout.setEnabled(check);
        category.setEnabled(check);
        categoryLayout.setEnabled(check);
        ratingBar.setEnabled(check);
        imgProduct.setEnabled(check);
        if(check == true) {
            btnCancel.setVisibility(View.VISIBLE);
            btnOk.setVisibility(View.VISIBLE);
            ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arrayCategory);
            category.setAdapter(categoryAdapter);
            ArrayAdapter<Supplier> supplierAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arraySupplier);
            supplier.setAdapter(supplierAdapter);
            ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_list_item_1,arrayLocation);
            location.setAdapter(locationAdapter);
        } else {
            btnCancel.setVisibility(View.GONE);
            btnOk.setVisibility(View.GONE);
        }

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