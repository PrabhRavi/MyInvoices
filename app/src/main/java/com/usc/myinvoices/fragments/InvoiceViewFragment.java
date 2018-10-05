package com.usc.myinvoices.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.usc.myinvoices.MainActivity;
import com.usc.myinvoices.R;
import com.usc.myinvoices.controllers.InvoiceActivity;
import com.usc.myinvoices.lab.InvoiceLab;
import com.usc.myinvoices.models.MyInvoice;

import java.util.Map;
import java.util.UUID;


public class InvoiceViewFragment extends Fragment {

    private MyInvoice myInvoices;

    private TextView mTitleField,mDateField,mDestinationField,mCommentField,mLocationField,mTypeField;
    private ImageView mImageView;
    private Button mCancelBtn,mMapBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String activityID = "";

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            activityID = bundle.getString(InvoiceActivity.EXTRA_LOG_ID, "");
        }
        myInvoices = InvoiceLab.get(getActivity()).getInvoices(UUID.fromString(activityID));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_invoice_view,container,false);

        mDateField = (TextView) view.findViewById(R.id.date);
        mTypeField = (TextView) view.findViewById(R.id.type);
        mTitleField = (TextView) view.findViewById(R.id.title);
        mCommentField = (TextView) view.findViewById(R.id.comment);
        mLocationField = (TextView) view.findViewById(R.id.location);
        mDestinationField = (TextView) view.findViewById(R.id.destination);

        mDateField.setText(myInvoices.getDate());
        mTypeField.setText(myInvoices.getType());
        mTitleField.setText(myInvoices.getTitle());
        mCommentField.setText(myInvoices.getComment());
        mLocationField.setText(myInvoices.getLocation());
        mDestinationField.setText(myInvoices.getShopName());

        mMapBtn = (Button) view.findViewById(R.id.btn_map);
        mCancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        mImageView = (ImageView) view.findViewById(R.id.imageView);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvoiceLab.get(getActivity()).deleteLog(myInvoices);
                startActivity(MainActivity.newIntent(getActivity()));
            }
        });

        mMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getContext(), "Show map", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString(InvoiceActivity.EXTRA_LOG_ID, myInvoices.getId().toString());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MapFragment myFragment = new MapFragment();

                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null).commit();

            }
        });

        return view;

    }

}
