package com.example.ddopik.phlogbusiness.network;

import android.content.Context;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * interceptor is a class whose methods are invoked when business methods on a target class are invoke
 * **/
public class BasicAuthInterceptor implements Interceptor {

    private String credentials;
    private Context context;
    private final String USER_NAME = "user_name";
    private final String LOGIN_PASSWORD = "PassWord";

    public BasicAuthInterceptor(Context context) {
        this.context = context;
        this.credentials = Credentials.basic(USER_NAME, LOGIN_PASSWORD);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials) // add oAuth (userName&&PassWord)
                .addHeader("Authorization", "bearer " + "--->User Token here<----") //add token to  Header
                .build();
        return chain.proceed(authenticatedRequest);
    }


}