package com.vesta.android.implementation.view_impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.vesta.android.MainActivity;
import com.vesta.android.R;
import com.vesta.android.interfaces.view_int.SplashScreenViewInt;
import com.vesta.android.model.KeyPairManager;
import com.vesta.android.util.AppConstants;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenViewInt {

    public static final int SPLASH_SCREEN_DELAY_MILLIS = 3000;
    public static final int CLOSE_APP_DELAY_MILLIS = 5000;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        // if (isBiometricAuthAvailable()) {
        //     generateKeyPair("userKeys");

        //     executor = ContextCompat.getMainExecutor(this);
        //     biometricPrompt = new BiometricPrompt(SplashScreenActivity.this,
        //             executor, new BiometricPrompt.AuthenticationCallback() {
        //         @Override
        //         public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
        //             super.onAuthenticationError(errorCode, errString);
        //             Toast.makeText(getApplicationContext(),
        //                     "Authentication error: " + errString, Toast.LENGTH_SHORT)
        //                     .show();
        //         }

        //         @Override
        //         public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
        //             super.onAuthenticationSucceeded(result);
        //             Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
        //             Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        //             startActivity(intent);
        //             finish();
        //         }

        //         @Override
        //         public void onAuthenticationFailed() {
        //             super.onAuthenticationFailed();
        //             Toast.makeText(getApplicationContext(), "Biometric authentication is not available for your device. The app will now exit.", Toast.LENGTH_SHORT).show();

        //             new Handler().postDelayed(new Runnable() {
        //                 @Override
        //                 public void run() {
        //                     SplashScreenActivity.this.finish();
        //                 }
        //             }, 7000);
        //         }
        //     });

        //     promptInfo = new BiometricPrompt.PromptInfo.Builder()
        //             .setTitle("Biometric login for my app")
        //             .setSubtitle("Log in using your biometric credential")
        //             .setNegativeButtonText("Use account password")
        //             .build();

        //     biometricPrompt.authenticate(promptInfo);

        // } else {
        //     Toast.makeText(this, "Biometric authentication is not available for your device. The app will now exit.", Toast.LENGTH_LONG).show();

        //     new Handler().postDelayed(new Runnable() {
        //         @Override
        //         public void run() {
        //             SplashScreenActivity.this.finish();
        //         }
        //     }, CLOSE_APP_DELAY_MILLIS);
        // }

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


    /**
     * Determines if biometric authentication capabilities are available on the device (i.e. can we use the device's fingerprint scanner if it exists)
     * @return boolean, True if the device has biometric capabilities. False otherwise (or if any error occurs)
     *
     * Resources:
     *
     * https://developer.android.com/training/sign-in/biometric-auth#available
     */
    @Override
    public boolean isBiometricAuthAvailable() {
        BiometricManager biometricManager = BiometricManager.from(this);
        boolean isBiometricAuthAvailable = false;
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(AppConstants.APP_LOG_TAG, "App can authenticate using biometrics.");
                isBiometricAuthAvailable = true;
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(AppConstants.APP_LOG_TAG, "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(AppConstants.APP_LOG_TAG, "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e(AppConstants.APP_LOG_TAG, "The user hasn't associated any biometric credentials with their account.");
                break;
        }
        return isBiometricAuthAvailable;
    }
}
