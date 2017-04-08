package com.example.apitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apitest.api.DjangoApi;

public class MainActivity extends AppCompatActivity {

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


    }

    private void setupLayout(){
        getBtn = (Button)findViewById(R.id.btn_snippet_get);
        addBtn = (Button)findViewById(R.id.btn_snippet_add);
        addSnippet = (EditText)findViewById(R.id.et_snippet_add);
        getStuff = (EditText)findViewById(R.id.et_snippet_get);
        output = (TextView)findViewById(R.id.tv_snippet_output);
    }


}
