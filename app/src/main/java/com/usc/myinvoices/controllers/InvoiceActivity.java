package com.usc.myinvoices.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.usc.myinvoices.R;
import com.usc.myinvoices.fragments.InvoiceFragment;

import java.util.UUID;

public class InvoiceActivity extends AppCompatActivity {

    public static final String EXTRA_LOG_ID = "TripActivity.log id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_invoice_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = new InvoiceFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }

    }

    public static Intent newIntent(Context packageContent, UUID logID){

        Intent intent = new Intent(packageContent, InvoiceActivity.class);
        intent.putExtra(EXTRA_LOG_ID, logID);
        return intent;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
