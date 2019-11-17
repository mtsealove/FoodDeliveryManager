package com.mtsealove.github.food_delivery_manager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mtsealove.github.food_delivery_manager.Design.ReviewRecyclerAdapter;
import com.mtsealove.github.food_delivery_manager.Design.SystemUiTuner;
import com.mtsealove.github.food_delivery_manager.Entity.Account;
import com.mtsealove.github.food_delivery_manager.Entity.Review;
import com.mtsealove.github.food_delivery_manager.Restful.RestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView reviewRv;
    Account account;
    static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewRv = findViewById(R.id.reviewRv);
        drawerLayout = findViewById(R.id.drawerLayout);
        account = (Account) getIntent().getSerializableExtra("account");

        SystemUiTuner tuner = new SystemUiTuner(this);
        tuner.setStatusBarWhite();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        reviewRv.setLayoutManager(lm);

        GetReview();
    }

    private void GetReview() {
        RestAPI restAPI = new RestAPI(this);
        Call<List<Review>> call = restAPI.getRetrofitService().GetReview(account.getID());
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    ReviewRecyclerAdapter adapter = new ReviewRecyclerAdapter(ReviewActivity.this);
                    for (Review review : reviews) {
                        adapter.addItem(review);
                    }
                    reviewRv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

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
}
