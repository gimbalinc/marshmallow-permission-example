/**
 * Copyright (C) 2015 Gimbal, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of Gimbal, Inc.
 *
 * The following sample code illustrates various aspects of the Gimbal SDK.
 *
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided AS IS and your use of this sample code, whether as provided or
 * with any modification, is at your own risk. Neither Gimbal, Inc.
 * nor any affiliate takes any liability nor responsibility with respect
 * to the sample code, and disclaims all warranties, express and
 * implied, including without limitation warranties on merchantability,
 * fitness for a specified purpose, and against infringement.
 */
package sample.android.gimbal.com.pleasepermit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;
import android.util.Log;

public class LocationPermissions implements DialogInterface.OnClickListener {
    private static final String TAG = "LocationPermissions";
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    static private Activity activity;
    public LocationPermissions(Activity activity) {
        this.activity = activity;
    }
    private PlaceManager placeManager;
    private PlaceEventListener placeEventListener;
    private ArrayAdapter<String> listAdapter;

    public void checkAndRequestPermission(ArrayAdapter<String> listAdapter) {
        this.listAdapter = listAdapter;
        if (isLocationPermissionEnabled()) {
            enablePlaceMonitoring();
        }
        else {
            requestLocationPermission();
        }
    }

    public boolean isLocationPermissionEnabled() {
        return ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showMessageOKCancel("Permitting us to access your location will entitle you to receive exclusive offers when you visit our stores. Please allow us to access your location so that we can better help you navigate our event.", this.activity, this, this);
            return;
        }
        activityRequestPermission();
    }

    private static void showMessageOKCancel(String message, Activity activity, DialogInterface.OnClickListener okListener,
            DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(activity).setMessage(message).setPositiveButton("OK", okListener).create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            ActivityCompat.requestPermissions(this.activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION }, LOCATION_PERMISSION_REQUEST_CODE);
        }
        else if (which == DialogInterface.BUTTON_NEGATIVE) {
            Log.e(TAG, "Application was denied permission!");

        }

    }

    private void activityRequestPermission() {
        ActivityCompat.requestPermissions(this.activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enablePlaceMonitoring();
            }else{
                Log.e(TAG, "Application was denied permission!");
            }

        }
    }

    private void enablePlaceMonitoring() {
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                listAdapter.add(String.format("Start Visit for %s", visit.getPlace().getName()));
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onVisitEnd(Visit visit) {
                listAdapter.add(String.format("End Visit for %s", visit.getPlace().getName()));
                listAdapter.notifyDataSetChanged();
            }
        };

        placeManager = PlaceManager.getInstance();
        placeManager.addListener(placeEventListener);
        placeManager.startMonitoring();

        CommunicationManager.getInstance().startReceivingCommunications();

    }


    }
