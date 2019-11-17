package com.mtsealove.github.food_delivery_manager.Design;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.core.view.WindowInsetsCompat;
import com.mtsealove.github.food_delivery_manager.*;
import com.mtsealove.github.food_delivery_manager.Entity.Account;

import java.util.ArrayList;

public class SlideView extends LinearLayout {
    Context context;
    TextView nameTv, BnameTv, logoutTv;
    ListView menuLv;
    Account account;

    public SlideView(Context context) {
        super(context);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_slide, this, false);

        nameTv = view.findViewById(R.id.nameTv);
        BnameTv = view.findViewById(R.id.bnameTv);
        logoutTv = view.findViewById(R.id.logoutTv);
        menuLv = view.findViewById(R.id.menuLv);

        logoutTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        SetListView();
        addView(view);
    }

    //메뉴 리스트 추가
    private void SetListView() {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("주문 확인");
        menus.add("리뷰");

        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, menus);
        menuLv.setAdapter(adapter);
        menuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        moveOrder();
                        break;
                    case 1:
                        moveReview();
                        break;
                }
            }
        });
    }

    //주문 확인으로 이동
    private void moveOrder() {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("account", account);
        context.startActivity(intent);
        closeDrawer();
    }

    //리뷰 확인으로 이동
    private void moveReview() {
        Intent intent=new Intent(context, ReviewActivity.class);
        intent.putExtra("account", account);
        context.startActivity(intent);
        closeDrawer();
    }

    //계정 설정
    public void setAccount(Account account) {
        nameTv.setText(account.getUserName() + "님");
        BnameTv.setText(account.getBusinessName());
        this.account = account;
    }

    //로그아웃
    private void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("id");
                editor.remove("pw");
                editor.commit();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).finishAffinity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void closeDrawer() {
        switch (context.getClass().getSimpleName()) {
            case "MainActivity":
                MainActivity.CloseDrawer();
                break;
            case "OrderActivity":
                OrderActivity.CloseDrawer();
                break;
            case "ReviewActivity":
                ReviewActivity.CloseDrawer();
                break;
        }
    }
}
