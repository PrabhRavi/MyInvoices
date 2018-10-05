package com.usc.myinvoices.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.usc.myinvoices.R;
import com.usc.myinvoices.controllers.InvoiceActivity;
import com.usc.myinvoices.lab.InvoiceLab;
import com.usc.myinvoices.models.MyInvoice;

import java.util.UUID;


public class MapFragment extends Fragment {

    private MyInvoice myInvoice;
    private LatLng myPosition;
    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String activityID = "";

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            activityID = bundle.getString(InvoiceActivity.EXTRA_LOG_ID, "");
        }
        myInvoice = InvoiceLab.get(getActivity()).getInvoices(UUID.fromString(activityID));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                String[] latlng = myInvoice.getLocation().split(",");

                LatLng location = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
                googleMap.addMarker(new MarkerOptions().position(location).title(myInvoice.getTitle()).snippet(myInvoice.getComment()));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
