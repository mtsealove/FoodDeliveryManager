package com.mtsealove.github.food_delivery_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mtsealove.github.food_delivery_manager.Design.SystemUiTuner;
import com.mtsealove.github.food_delivery_manager.Entity.Account;
import com.mtsealove.github.food_delivery_manager.Restful.RestAPI;
import com.mtsealove.github.food_delivery_manager.Utils.IpActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTv, titleTv;
    String tag = getClass().getSimpleName();
    EditText idEt, pwEt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUpTv = findViewById(R.id.signUpTv);
        titleTv = findViewById(R.id.titleTv);
        idEt = findViewById(R.id.idEt);
        pwEt = findViewById(R.id.pwEt);
        loginBtn = findViewById(R.id.loginBtn);

        SystemUiTuner tuner = new SystemUiTuner(this);
        tuner.setStatusBarWhite();

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        titleTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(LoginActivity.this, IpActivity.class);
                startActivity(intent);
                return false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInput();
            }
        });

        AutoLogin();
    }

    private void SignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    private void CheckInput() {
        if (idEt.getText().toString().length() == 0) {
            Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
        } else if (pwEt.getText().toString().length() == 0) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            Login();
        }
    }

    private void Login() {
        String id = idEt.getText().toString();
        String pw = pwEt.getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();

        RestAPI restAPI = new RestAPI(this);
        Call<Account> call = restAPI.getRetrofitService().Login(id, pw, token);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    if (account.getID() != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Log.e(tag, t.toString());
            }
        });

        KeepLogin();
    }

    //로그인 유지
    private void KeepLogin() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String pw = pwEt.getText().toString();
        String ID = idEt.getText().toString();
        editor.putString("id", ID);
        editor.putString("pw", pw);

        editor.commit();
    }

    //자동 로그인
    private void AutoLogin() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String id = pref.getString("id", "");
        String pw = pref.getString("pw", "");
        if (id.length() != 0 && pw.length() != 0) {
            idEt.setText(id);
            pwEt.setText(pw);
            Login();
        }
    }
}
