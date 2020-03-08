package com.vesta.android.implementation.view_impl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.vesta.android.MainActivity;
import com.vesta.android.R;
import com.vesta.android.implementation.BaseActivity;
import com.vesta.android.interfaces.view_int.SplashScreenViewInt;
import com.vesta.android.model.KeyPairManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenViewInt {

    public static final int SPLASH_SCREEN_DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        generateKeyPair("userKeys");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DELAY_MILLIS);

    }

    @Override
    public boolean generateKeyPair(String keyAlias) {
        try {
            KeyPairManager keyPairManager = new KeyPairManager(getApplicationContext());
            keyPairManager.generateRsaEncryptionKeyPair(keyAlias);
            return true;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return false;
    }
}
