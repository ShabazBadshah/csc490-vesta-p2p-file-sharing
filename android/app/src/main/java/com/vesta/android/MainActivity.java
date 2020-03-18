package com.vesta.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vesta.android.model.KeyPairManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    private ImageView qrImage;
    private String inputValue;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmapResult;
    private FrameLayout regenerateKeyBtn;
    private FrameLayout scanQrBtn;
    private KeyPairManager keyPairManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        qrImage = findViewById(R.id.publicKeyQrImgView);
        regenerateKeyBtn = findViewById(R.id.regenerateKeyBtn);

        scanQrBtn = findViewById(R.id.scanQrBtn);

        generateQr();
    }

    public void generateQr() {
        Point point = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;

        try {
            KeyPairManager keyPairManager = new KeyPairManager(getApplicationContext());
            keyPairManager.generateRsaEncryptionKeyPair("userKeys");
            qrgEncoder = new QRGEncoder(KeyPairManager.convertRsaKeyToBase64String(
                    KeyPairManager.getKeyPairFromKeystore("userKeys").getPublic()),
                    QRGContents.Type.TEXT, smallerDimension);

            qrgEncoder.setColorWhite(getColor(R.color.primaryBrandColour));
            qrgEncoder.setColorBlack(getColor(android.R.color.white));
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
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            bitmap = qrgEncoder.getBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v("Log error", e.toString());
        }
    }

    public void onRegenerateKeyBtnClick(View view) {
        generateQr();
    }

    public void onLaunchQrActivity(View view) {
        Intent intent = new Intent(this.getApplicationContext(), QRScannerActivity.class);
        startActivity(intent);
    }
}