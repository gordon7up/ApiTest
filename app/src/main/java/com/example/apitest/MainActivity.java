/**
 * Simple activity to work with the Django snippets tutorial using an Android client.
 */
package com.example.apitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitest.api.DjangoApi;
import com.example.apitest.models.Snippet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    DjangoApi api;

    Button getBtn;
    Button addBtn;
    EditText addSnippet;
    EditText getStuff;
    TextView output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayout();
        createDjangoApi();
        api.getSnippets().enqueue(snippetsCallback);
        postSnippetSetup();
    }

    /**
     * Initilise the various widgets and setup the app UI.
     */
    private void setupLayout(){
        getBtn = (Button)findViewById(R.id.btn_snippet_get);
        addBtn = (Button)findViewById(R.id.btn_snippet_add);
        addSnippet = (EditText)findViewById(R.id.et_snippet_add);
        getStuff = (EditText)findViewById(R.id.et_snippet_get);
        output = (TextView)findViewById(R.id.tv_snippet_output);
    }

    /**
     * Add a simple listener to the add snippet button. Method
     * will take the user input and post as code snippet.
     */
    private void postSnippetSetup(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String snippet = addSnippet.getText().toString();
                if(!snippet.isEmpty()){
                    api.addCodeSnippet(snippet).enqueue(addSnippetCallback);
                }
            }
        });
    }


    /**
     * Create the retrofit2 api.
     */
    private void createDjangoApi(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.BASE_URL)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(DjangoApi.class);
    }

    /**
     * Using OkHTTP's Auth interceptor to inject simple authentication
     * the the api requests.
     * Authentication is setup within django app, here we simply use the superuser
     * name and pass to view or make changes to api.
     * @return
     */
    private OkHttpClient createClient(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic("elaine", "pass12345"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        return okHttpClient;
    }

    /**
     * Retrofit 2 callback handling the get snippets request.
     * If successful, all snippets and corresponding owners will be sent to
     * output textview. If unsuccessful an code and message will publish to logcat.
     */
    Callback<List<Snippet>> snippetsCallback = new Callback<List<Snippet>>() {
        @Override
        public void onResponse(Call<List<Snippet>> call, Response<List<Snippet>> response) {
            if(response.isSuccessful()){
                for(Snippet snippet : response.body()){
                    output.append(snippet.getCode() + " by: " + snippet.getOwner() + "\n");
                }
            }
            else{
                Log.d(TAG, "snippetCallback: " + response.code() + " Message:" + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Snippet>> call, Throwable t) {
            t.printStackTrace();
        }
    };


    /**
     * Retrofit2 callback handling the response from an add snippet attempt.
     * if succesfull the new snippet will be added to db, if unsuccessful code
     * and message will be posted to logcat.
     *
     */
    Callback<ResponseBody> addSnippetCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                Toast.makeText(MainActivity.this, "add succesful", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Add snippetCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            t.printStackTrace();
        }
    };


}
