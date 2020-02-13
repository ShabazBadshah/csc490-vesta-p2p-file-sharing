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
 * Class responsible for the creation and management of Public/Private key-pairs
 */
public class GenerateKeyPair {

    public static final String KEY_STORE_PROVIDER = "AndroidKeyStore";

    /**
     * Generates a Public/Private key-pair that can be used for encryption and decryption
     * @param keyStoreAlias String, the name of the key-pair that will be saved in the KeyStore
     * @return KeyPair, returns the KeyPair that was created which contains the PublicKey and PrivateKey
     */
    public static KeyPair generateKeyPair(final String keyStoreAlias) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
            KeyPairGenerator keyPairGen = java.security.KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_STORE_PROVIDER);
            keyPairGen.initialize(
                    new KeyGenParameterSpec.Builder(keyStoreAlias, KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .build());

            return keyPairGen.generateKeyPair();
    }

    /**
     * Returns the the Base64 encoding of the PublicKey
     * @param key PublicKey, the publicKey which will be converted to a Base64 String
     * @return String, the Base64 encoding of the PublicKey
     */
    public static String getBase64StringEncoding(PublicKey key) {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }
}
