package com.mtsealove.github.food_delivery_manager.Design;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mtsealove.github.food_delivery_manager.Entity.Item;
import com.mtsealove.github.food_delivery_manager.R;

public class ItemView extends LinearLayout {
    Context context;
    ImageView img;
    TextView nameTv, priceTv, totalTv;
    Item item;

    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_item, this, false);
        img = view.findViewById(R.id.imgIv);
        nameTv = view.findViewById(R.id.nameTv);
        priceTv = view.findViewById(R.id.priceTv);
        totalTv = view.findViewById(R.id.totalTv);

        addView(view);
    }

    public void setItem(Item item) {
        this.item = item;

        nameTv.setText(item.getItemName());
        priceTv.setText("금액: " + item.getPrice() + "원");
        totalTv.setText("매출액: " + item.getTotal() + "원");
        if(item.getImagePath()!=null) {
            Glide.with(context)
                    .load(GetImgUrl()+item.getImagePath())
                    .into(img);
        }
    }

    private String GetImgUrl() {
        SharedPreferences pref=context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String data="http://"+pref.getString("ip", "")+"/DeliveryService/Images/";
        return data;
    }
}
