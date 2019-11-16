package com.mtsealove.github.food_delivery_manager.Restful;

import androidx.cardview.widget.CardView;
import com.mtsealove.github.food_delivery_manager.Entity.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("/DeliveryService/Android/Manager/Post/Login.php")
    Call<Account> Login(@Field("ID") String ID, @Field("Password") String password, @Field("Token") String token);

    @GET("/DeliveryService/Android/Manager/Get/Sales.php")
    Call<List<Sales>> GetSales(@Query("ID") String id);

    @GET("/DeliveryService/Android/Manager/Get/MainItems.php")
    Call<List<Item>> GetMainItems(@Query("ID") String id);

    @GET("/DeliveryService/Android/Manager/Get/OrderList.php")
    Call<List<Order>> GetOrders(@Query("ID") String id);

    @FormUrlEncoded
    @POST("/DeliveryService/Android/Manager/Post/UpdateStatus.php")
    Call<Result> UpdateStatus(@Field("ManagerID") String ManagerID, @Field("OrderTime") String orderTime, @Field("Status") int status);
}
