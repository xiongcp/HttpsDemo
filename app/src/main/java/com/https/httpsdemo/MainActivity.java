package com.https.httpsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.https.httpsdemo.https.OkHttpUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://192.168.144.169:8443")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(OkHttpUtils.getOkHttpClient().getBuild())
                .build().create(APIService.class).getUsers().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_test.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                tv_test.setText(t.getLocalizedMessage());
            }
        });
    }

    public interface APIService {
        @GET("/")
        Call<String> getUsers();
    }
}
