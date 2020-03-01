package com.vesta.android;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageView qrImage;
    private String inputValue;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmapResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_qr_main);

        qrImage = findViewById(R.id.qr_image);

//        calculating bitmap dimension
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        try {
            qrgEncoder = new QRGEncoder(KeyPairManager.convertRsaKeyToBase64String(
                    KeyPairManager.getKeyPairFromKeystore("userKeys").getPublic()),
                    null, QRGContents.Type.TEXT, smallerDimension);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }

        try {
            bitmap = qrgEncoder.getBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v("Log error", e.toString());
        }
    }
}