package com.rbc.yelp.services;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    final String TAG = getClass().getSimpleName();
    private static MyApplication mInstance;
    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static Retrofit getRetrofitClient() {

        if (retrofit == null) {
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .client((new OkHttpClient.Builder()
                            .addInterceptor(new ApiKeyInterceptor())
                            .build()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.yelp.com")
                    .build();
        }
        return retrofit;
    }

    private static class ApiKeyInterceptor implements Interceptor {
        @Override
        public @NonNull
        Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " +"Th7LsHZW8RiGeR3zg3wAD2xhqSVRfh5U_0C6Gt__rJ9exrDXQSupcYXxqel-Alc1YD9coO-sGLHs8pBukBV8dZuyB0T4pJeqO1R6pfqnHnSdA8JYyx7H2vYY8PTlYXYx")
                    .build());
        }
    }

}