package com.mtsealove.github.food_delivery_manager.Design;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.mtsealove.github.food_delivery_manager.Entity.Account;
import com.mtsealove.github.food_delivery_manager.Entity.Order;
import com.mtsealove.github.food_delivery_manager.Entity.Result;
import com.mtsealove.github.food_delivery_manager.OrderActivity;
import com.mtsealove.github.food_delivery_manager.R;
import com.mtsealove.github.food_delivery_manager.Restful.RestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class OrderView extends LinearLayout {
    Context context;
    TextView namesTv, pricesTv, addressTv, totalPriceTv, timeTv;
    Button statusBtn;
    ArrayList<Order> orderArrayList;
    Account account;
    String tag = getClass().getSimpleName();

    public OrderView(Context context) {
        super(context);
        init(context);
    }

    public OrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public OrderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_order, this, false);

        namesTv = view.findViewById(R.id.namesTv);
        pricesTv = view.findViewById(R.id.pricesTv);
        addressTv = view.findViewById(R.id.addressTv);
        totalPriceTv = view.findViewById(R.id.totalPriceTv);
        statusBtn = view.findViewById(R.id.statusBtn);
        timeTv = view.findViewById(R.id.timeTv);

        orderArrayList = new ArrayList<>();
        addView(view);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //주문 하나 추가
    public void AddOrder(Order order) {
        orderArrayList.add(order);
        refresh();
    }

    //데이터 표시
    private void refresh() {
        String names = "";
        String prices = "";
        int total_price = 0;
        for (Order order : orderArrayList) {
            names += order.getItemName() + "\n";
            prices += order.getPrice() + "원\n";
            total_price += order.getPrice();
        }
        names = names.substring(0, names.length() - 1);
        prices = prices.substring(0, prices.length() - 1);
        namesTv.setText(names);
        pricesTv.setText(prices);
        addressTv.setText("배송주소: " + orderArrayList.get(0).getLocation());
        totalPriceTv.setText("주문금액: " + total_price + "원");
        statusBtn.setText(orderArrayList.get(0).getStatusName());
        timeTv.setText("시간: "+orderArrayList.get(0).getOrderTime());

        Drawable btnBackground = null;
        switch (orderArrayList.get(0).getStatus()) {
            case 1:
                btnBackground = context.getDrawable(R.drawable.btn_red);
                break;
            case 2:
                btnBackground = context.getDrawable(R.drawable.btn_blue);
                break;
            default:
                btnBackground = context.getDrawable(R.drawable.btn_green);
        }
        statusBtn.setBackground(btnBackground);
        statusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStatus(account.getID(), orderArrayList.get(0).getOrderTime());
            }
        });
    }

    private void UpdateStatus(final String managerID, final String orderTime) {
        ArrayList<String> list = new ArrayList<>();
        list.add("대기중");
        list.add("배송 준비중");
        list.add("배송중");

        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);

        ListView listView = new ListView(context);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("상태 변경")
                .setView(listView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestAPI restAPI = new RestAPI(context);
                Call<Result> call = restAPI.getRetrofitService().UpdateStatus(managerID, orderTime, position + 1);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equals("Ok")) {
                                Toast.makeText(context, "상태가 변경되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, OrderActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        }
                        Log.e(tag, response.toString());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(tag, t.toString());
                    }
                });
                dialog.dismiss();
            }
        });
    }
}
