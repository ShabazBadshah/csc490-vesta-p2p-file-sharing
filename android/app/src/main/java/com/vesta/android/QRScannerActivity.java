package com.vesta.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.security.Key;
import android.provider.Settings.Secure;


import com.google.zxing.Result;
import com.google.zxing.MultiFormatReader;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private final int MY_CAMERA_REQUEST_CODE = 1888;
    private static final String TAG = "ScannerActivity";

    /*
    * The name of our shared preferences
    * Used to instantiate the shared preferences object with this name
    * */
    private static final String SHARED_PREFERENCES = "SharedPreferences";

    private void setScannerProperties(ZXingScannerView qrCodeScanner) {
        qrCodeScanner.setAutoFocus(true);
        qrCodeScanner.setLaserColor(R.color.colorAccent);
        qrCodeScanner.setMaskColor(R.color.colorAccent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                String [] perms = {Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(this, perms,
                        MY_CAMERA_REQUEST_CODE);
                return;
            }
        }

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /**
     * The rawResult variable contains the data which was present in the QR code
     * Used to get the contents from the QR code
     * @param rawResult, Result object retrieved from the QR code
     */
    @Override
    public void handleResult(Result rawResult) {

        //Storing the rawResult which is the public key in shared preferences with the
        // unique identifier of the device
        String androidID = Secure.getString(this.getBaseContext().getContentResolver(),
                Secure.ANDROID_ID);

        KeyPairManager.storePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext(),
                rawResult.getText(), androidID);
        Log.i("Retrieve shared pref",
                KeyPairManager.retrievePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext(),
                        androidID));

        System.out.println(((TextView)findViewById(R.id.textView)));
        Log.v(TAG, rawResult.getText()); // Prints scan result
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        setContentView(R.layout.activity_main);

        TextView publicKeyTextView = (TextView)findViewById(R.id.textView);
        publicKeyTextView.setText(rawResult.getText());
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

}
