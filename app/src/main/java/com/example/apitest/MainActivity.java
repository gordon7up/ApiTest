package com.example.apitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apitest.api.DjangoApi;
import com.example.apitest.models.Snippet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.PublicKey;
import java.util.List;

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
    }

    private void setupLayout(){
        getBtn = (Button)findViewById(R.id.btn_snippet_get);
        addBtn = (Button)findViewById(R.id.btn_snippet_add);
        addSnippet = (EditText)findViewById(R.id.et_snippet_add);
        getStuff = (EditText)findViewById(R.id.et_snippet_get);
        output = (TextView)findViewById(R.id.tv_snippet_output);
    }

    private void getSnippets(){
        String snippetText = getStuff.getText().toString();

        if(snippetText.isEmpty()){
            snippetText = "";
        }

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void createDjangoApi(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(DjangoApi.class);
    }

    Callback<List<Snippet>> snippetsCallback = new Callback<List<Snippet>>() {
        @Override
        public void onResponse(Call<List<Snippet>> call, Response<List<Snippet>> response) {
            if(response.isSuccessful()){
                Log.d(TAG, "Callback successful...");
                for(Snippet snippet : response.body()){
                    output.append(snippet.getCode() + "\n");
                }
            }
            else{
                Log.d(TAG, "Code: " + response.code() + " Message:" + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Snippet>> call, Throwable t) {
            t.printStackTrace();
        }
    };


}
