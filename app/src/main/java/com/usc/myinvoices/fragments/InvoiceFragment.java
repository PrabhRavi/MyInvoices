package com.usc.myinvoices.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.usc.myinvoices.R;
import com.usc.myinvoices.MainActivity;
import com.usc.myinvoices.controllers.InvoiceActivity;
import com.usc.myinvoices.lab.InvoiceLab;
import com.usc.myinvoices.models.MyInvoice;

import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class InvoiceFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

    private MyInvoice myInvoice;

    private Spinner mType;
    private ImageView mImageView;
    private GoogleApiClient mClient;
    private TextView mLocationField;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button mDateBtn;
    private int mYear, mMonth, mDay;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID invoiceID = (UUID) getActivity().getIntent().getSerializableExtra(InvoiceActivity.EXTRA_LOG_ID);

        myInvoice = InvoiceLab.get(getActivity()).getInvoices(invoiceID);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_invoice,container,false);

        mDateBtn = (Button) view.findViewById(R.id.date);

        EditText mTitleField = (EditText) view.findViewById(R.id.title);
        EditText mCommentField = (EditText) view.findViewById(R.id.comment);
        mLocationField = (TextView) view.findViewById(R.id.location);
        EditText mShopNameField = (EditText) view.findViewById(R.id.shopname);

        mType = (Spinner) view.findViewById(R.id.type);
        mImageView = (ImageView) view.findViewById(R.id.imageView);

        mLocationField.setText("Location Location...");

        Button mSaveBtn = (Button) view.findViewById(R.id.btn_save);
        Button mCancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        Button mCameraBtn = (Button) view.findViewById(R.id.btn_camera);

        mCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                myInvoice.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mShopNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                myInvoice.setShopName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                myInvoice.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mDateBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                myInvoice.setDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMyInvoice();
                startActivity(MainActivity.newIntent(getActivity()));
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvoiceLab.get(getActivity()).deleteLog(myInvoice);
                startActivity(MainActivity.newIntent(getActivity()));
            }
        });

        return view;

    }

    public void updateMyInvoice() {

        if(myInvoice.getTitle() == null || myInvoice.getComment() == null || myInvoice.getShopName() == null
            || myInvoice.getDate() == null){
            InvoiceLab.get(getActivity()).deleteLog(myInvoice);
            return;
        }

        myInvoice.setType(mType.getSelectedItem().toString());
        InvoiceLab.get(getActivity()).updateLog(myInvoice);
    }

    @Override
    public void onStart(){
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onPause(){
        super.onPause();
        updateMyInvoice();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            mLocationField.setText("No Permission");
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocationField.setText(location.getLatitude()+","+location.getLongitude());
                myInvoice.setLocation(location.getLatitude()+","+location.getLongitude());
            }
        });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
