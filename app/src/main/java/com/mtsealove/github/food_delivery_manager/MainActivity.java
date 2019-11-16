package com.mtsealove.github.food_delivery_manager;

import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mtsealove.github.food_delivery_manager.Design.ItemView;
import com.mtsealove.github.food_delivery_manager.Design.SlideView;
import com.mtsealove.github.food_delivery_manager.Design.SystemUiTuner;
import com.mtsealove.github.food_delivery_manager.Entity.Account;
import com.mtsealove.github.food_delivery_manager.Entity.Item;
import com.mtsealove.github.food_delivery_manager.Entity.Sales;
import com.mtsealove.github.food_delivery_manager.Restful.RestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Account account;
    BarChart chart;
    LinearLayout mainItemLayout;
    SlideView slideView;
    static DrawerLayout drawerLayout;
    String tag = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainItemLayout = findViewById(R.id.mainItemLayout);
        slideView = findViewById(R.id.slideView);
        drawerLayout = findViewById(R.id.drawerView);

        SystemUiTuner tuner = new SystemUiTuner(this);
        tuner.setStatusBarWhite();

        account = (Account) getIntent().getSerializableExtra("account");
        Toast.makeText(this, account.getBusinessName() + "님 환영합니다", Toast.LENGTH_SHORT).show();
        chart = findViewById(R.id.barChart);

        GetSales();
        GetMainItems();

        slideView.setAccount(account);
    }

    //매출 차트 설정
    private void SetChart(List<Sales> salesList) {
        ArrayList date = new ArrayList();
        ArrayList Total = new ArrayList();

        int i = 0;
        for (Sales sales : salesList) {
            date.add(sales.getOrderDate().substring(5, 10));
            Total.add(new BarEntry(sales.getTotal(), i));
            i++;
        }

        BarDataSet bardataset = new BarDataSet(Total, "매출액");
        chart.animateY(5000);
        BarData data = new BarData(date, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.setDescription("");
    }

    private void GetSales() {
        RestAPI restAPI = new RestAPI(this);
        Call<List<Sales>> call = restAPI.getRetrofitService().GetSales(account.getID());
        call.enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                if (response.isSuccessful()) {
                    List<Sales> salesList = response.body();
                    Collections.reverse(salesList);
                    SetChart(salesList);
                }
            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {

            }
        });
    }

    private void GetMainItems() {
        RestAPI restAPI = new RestAPI(this);
        Call<List<Item>> call = restAPI.getRetrofitService().GetMainItems(account.getID());
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Log.e(tag, response.toString());
                if (response.isSuccessful()) {
                    List<Item> itemList = response.body();
                    for (Item item : itemList) {
                        ItemView itemView = new ItemView(MainActivity.this);
                        itemView.setItem(item);
                        mainItemLayout.addView(itemView);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(tag, t.toString());
            }
        });
    }

    public static void OpenDrawer() {
        if(!drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public static void CloseDrawer() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}

