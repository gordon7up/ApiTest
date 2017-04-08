package com.example.apitest.api;

/**
 * Created by gordonwallace on 07/04/2017.
 */

import com.example.apitest.models.Snippet;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;

public interface DjangoApi {
    String BASE_URL = "ip/";

    @GET("snippets/")
    Call<List<Snippet>> groupList(@Path("id") int groupId);

}
