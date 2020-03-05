package com.vesta.android;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import androidx.appcompat.app.AppCompatActivity;


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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        keyPairManager = (KeyPairManager) getIntent().getSerializableExtra("KeyPairManager");

        setContentView(R.layout.activity_qr_main);

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
}