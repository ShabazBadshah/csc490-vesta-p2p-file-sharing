package com.vesta.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_main);

        final Button qr_button = (Button)findViewById(R.id.qrbutton);
        qrImage = findViewById(R.id.qr_image);
        final Button scanQRButton = (Button)findViewById(R.id.scanQR);
        qrImage = findViewById(R.id.qr_image);

        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QRScannerActivity.class);
                startActivity(intent);
            }
        });

        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calculating bitmap dimension
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                qrgEncoder = new QRGEncoder("AVI IS THE FIRST", null, QRGContents.Type.TEXT, smallerDimension);
                try {
                    bitmapResult = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmapResult);
                } catch (Exception e) {
                    Log.v("Log error", e.toString());
                }

            }
        });

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                  WindowManager.LayoutParams.FLAG_SECURE);

          try {
              KeyPairManager.generateKeyPair("userKeys");
              KeyPairManager.generateKeyPair("encryptionKeys");
          } catch (CertificateException e) {
              e.printStackTrace();
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          } catch (KeyStoreException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          } catch (NoSuchProviderException e) {
              e.printStackTrace();
          } catch (InvalidAlgorithmParameterException e) {
              e.printStackTrace();
          } catch (UnrecoverableEntryException e) {
              e.printStackTrace();
          } catch (InvalidKeySpecException e) {
              e.printStackTrace();
          }
    }
}