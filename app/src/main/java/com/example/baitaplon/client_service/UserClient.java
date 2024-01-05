package com.example.baitaplon.client_service;

import com.example.baitaplon.entity.Order;
import com.example.baitaplon.entity.Product;
import com.example.baitaplon.entity.User;
import com.example.baitaplon.request_response.AddDeviceRequest;
import com.example.baitaplon.request_response.CategoryResponse;
import com.example.baitaplon.request_response.ChartDataResponse;
import com.example.baitaplon.request_response.DeviceListResponse;
import com.example.baitaplon.request_response.ImageResponse;
import com.example.baitaplon.request_response.LocationResponse;
import com.example.baitaplon.request_response.LoginRequest;
import com.example.baitaplon.request_response.MessageResponse;
import com.example.baitaplon.request_response.MoneyOrderResponse;
import com.example.baitaplon.request_response.NotificationRequest;
import com.example.baitaplon.request_response.OrderListResponse;
import com.example.baitaplon.request_response.ProductListResponse;
import com.example.baitaplon.request_response.SupplierResponse;
import com.example.baitaplon.request_response.UserListResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserClient {

    @GET("product_category/get")
    Call<CategoryResponse> GetCategory();

    @GET("v1/product/delete")
    Call<MessageResponse> DeleteProduct(@Query("id") int id);

    @POST("v1/auth/signin")
    Call<User> Login(@Body LoginRequest loginInfo);

    @GET("v1/product/get")
    Call<ProductListResponse> GetProductList(@Header("Authorization") String token, @Query("page") int p);

    @GET("supplier/get")
    Call<SupplierResponse> GetSupplier();

    @GET("location/get")
    Call<LocationResponse> GetLocation();

    @Multipart
    @POST("v1/file/upload-image")
    Call<ImageResponse> UploadImage(@Part MultipartBody.Part img);

    @POST("admin/v1/product/add")
    Call<MessageResponse> AddProduct(@Header("Authorization") String token, @Body Product ProductInfo);

    @GET("admin/v1/user/get-users")
    Call<UserListResponse> GetUserList(@Header("Authorization") String token, @Query("page") int p, @Query("deleted") boolean d);

    @POST("admin/v1/product/update")
    Call<MessageResponse> UpdateProduct(@Header("Authorization") String token, @Body Product ProductInfo);

    @POST("v1/notification/fcm-push")
    Call<MessageResponse> PushNotification(@Body NotificationRequest Noti);

    @POST("v1/device/add")
    Call<MessageResponse> AddDevice(@Header("Authorization") String token, @Body AddDeviceRequest Noti);

    @GET("admin/v1/order/get")
    Call<OrderListResponse> GetOrderList(@Header("Authorization") String token);

    @POST("admin/v1/order/update")
    Call<MessageResponse> UpdateOrder(@Header("Authorization") String token, @Body Order order);

    @GET("admin/v1/order/revenueMonth?date=2023-03-01")
    Call<ChartDataResponse> GetChartData(@Header("Authorization") String token);

    @GET("v1/device/all")
    Call<DeviceListResponse> GetDeviceList();

    @GET("admin/v1/static/countOrder")
    Call<MoneyOrderResponse> GetTotalOder(@Header("Authorization") String token, @Query("userId") int id);

    @GET("admin/v1/static/countMoney")
    Call<MoneyOrderResponse> GetTotalMoney(@Header("Authorization") String token, @Query("userId") int id);

}

