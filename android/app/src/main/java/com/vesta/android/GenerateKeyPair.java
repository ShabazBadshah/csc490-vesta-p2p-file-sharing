package com.vesta.android;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import 	java.security.KeyPairGenerator;


/**
 * Class used for generating public/private key pair
 * */
public class GenerateKeyPair {

    /**
     * param: keyStoreAlias constant
     * return: a keyPair
     */
    public static KeyPair generateKeyPair(final String keyStoreAlias) {

        try {
            KeyPairGenerator keyPairGen = java.security.KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            keyPairGen.initialize(
                    new KeyGenParameterSpec.Builder(keyStoreAlias, KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .build());

            return keyPairGen.generateKeyPair();

        } catch (NoSuchProviderException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
    * param: PublicKey
     * return: String representation of the key
    * */
    public static String convertPublicKeyToString(PublicKey key) {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }
}
