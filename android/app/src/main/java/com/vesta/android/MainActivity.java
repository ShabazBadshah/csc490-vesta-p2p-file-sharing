package com.vesta.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.telecom.Call;

import android.util.Base64;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import 	java.security.KeyPairGenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            //Use to encode public key to Base64
            //Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT));

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            keyPairGen.initialize(
                    new KeyGenParameterSpec.Builder("Key", KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .build());

            //Generates the keyPair
            //Use keyPair.getPrivate() and keyPair.getPublic() to get pub and private keys
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // The key can also be obtained from the Android Keystore any time as follows:
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            System.out.println(keyStore.getKey("Key", null));


        } catch (NoSuchProviderException |  NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | KeyStoreException | CertificateException |
                IOException | UnrecoverableKeyException e ) {
            e.printStackTrace();
        }
        final Button button = (Button)findViewById(R.id.button);
        final TextView responseField = (TextView)findViewById(R.id.textView);

        final OkHttpClient client = new OkHttpClient();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            final Request request = new Request.Builder()
                                    .url("http://10.0.2.2:5000/")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Log.v("VESTA-LOG", response.toString());
                            responseField.setText(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }
}

