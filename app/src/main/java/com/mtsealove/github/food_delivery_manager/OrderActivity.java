package com.mtsealove.github.food_delivery_manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mtsealove.github.food_delivery_manager.Design.OrderView;
import com.mtsealove.github.food_delivery_manager.Design.SlideView;
import com.mtsealove.github.food_delivery_manager.Design.SystemUiTuner;
import com.mtsealove.github.food_delivery_manager.Entity.Account;
import com.mtsealove.github.food_delivery_manager.Entity.Order;
import com.mtsealove.github.food_delivery_manager.Restful.RestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    Account account;
    RestAPI restAPI;
    String tag = getClass().getSimpleName();
    LinearLayout contentLayout;
    SlideView slideView;
    static DrawerLayout drawerLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        contentLayout = findViewById(R.id.contentLayout);
        slideView = findViewById(R.id.slideView);
        drawerLayout = findViewById(R.id.drawerView);

        SystemUiTuner tuner = new SystemUiTuner(this);
        tuner.setStatusBarWhite();


        restAPI = new RestAPI(this);
        Login();
    }

    private void Login() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.show();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String id = pref.getString("id", "");
        String pw = pref.getString("pw", "");
        String token = FirebaseInstanceId.getInstance().getToken();
        Call<Account> call = restAPI.getRetrofitService().Login(id, pw, token);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    account = response.body();
                    slideView.setAccount(account);
                    GetOrders();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    private void GetOrders() {
        Call<List<Order>> call = restAPI.getRetrofitService().GetOrders(account.getID());
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    Order tmp = null;
                    OrderView orderView = new OrderView(OrderActivity.this);
                    orderView.setAccount(account);
                    for (Order order : response.body()) {
                        //첫번째 경우
                        if (tmp == null) {

                        } else if (!tmp.getOrderTime().equals(order.getOrderTime())) {
                            contentLayout.addView(orderView);
                            orderView = new OrderView(OrderActivity.this);
                            orderView.setAccount(account);
                        } else {//같은 주문인 경우
                            orderView.AddOrder(order);
                        }
                        tmp = order;
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public static void OpenDrawer(){
        if(!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void CloseDrawer() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("noti", false)){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
