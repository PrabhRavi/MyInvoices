package com.usc.myinvoices.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.usc.myinvoices.R;

import java.util.UUID;

public class HelpActivity extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        web = findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://en.wikipedia.org/wiki/Invoice");

    }

    public static Intent newIntent(Context packageContent){

        Intent intent = new Intent(packageContent, HelpActivity.class);
        return intent;

    }

}
