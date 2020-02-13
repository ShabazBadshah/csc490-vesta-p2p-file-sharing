package com.vesta.android;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import 	java.security.KeyPairGenerator;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;


/**
 * Class responsible for the creation and management of Public/Private key-pairs
 *
 * We never expose public keys to anything expect for this application, ONLY the methods that require the private-key will have access to it (only when needed)
 */
public class KeyPairManager {

    public static final String KEY_STORE_PROVIDER_NAME = "AndroidKeyStore";
    public static final String LOG_TAG = KeyPair.class.getSimpleName();
    public static KeyStore keyStore;

    /**
     * Generates a Public/Private key-pair that can be used for encryption and decryption
     * @param keyPairAlias String, the name of the key-pair that will be saved in the KeyStore
     * @return KeyPair, returns the KeyPair that was created which contains the PublicKey and PrivateKey
     */
    public static PublicKey generateKeyPair(final String keyPairAlias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchProviderException, InvalidAlgorithmParameterException, UnrecoverableEntryException {

        if (keyPairAlias == null || keyPairAlias.length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "generateKeyPair()", "Illegal argument String:keyPairAlias provided"));
        }

        loadKeyStore(KEY_STORE_PROVIDER_NAME);

        if (!keyStore.containsAlias(keyPairAlias)) {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_STORE_PROVIDER_NAME);
            keyPairGen.initialize(
            new KeyGenParameterSpec.Builder(keyPairAlias, KeyProperties.PURPOSE_ENCRYPT |
                    KeyProperties.PURPOSE_DECRYPT)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .build());

            KeyPair keyPair =  keyPairGen.generateKeyPair();
        }

        Log.i(LOG_TAG, getBase64StringEncoding(getPublicKeyFromKeyStore(keyPairAlias)));

        return getPublicKeyFromKeyStore(keyPairAlias);
    }

    /**
     * Loads the KeyStore
     * @param keyStoreProviderName String, the name of the key store provider that is being utilized
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void loadKeyStore(String keyStoreProviderName) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore = KeyStore.getInstance(KEY_STORE_PROVIDER_NAME);
        keyStore.load(null);
    }


    /**
     * Returns the the Base64 encoding of the PublicKey
     * @param key PublicKey, the PublicKey which will be converted to a Base64 String
     * @return String, the Base64 encoding of the PublicKey
     */
    public static String getBase64StringEncoding(Key key) {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }


    /**
     * Returns the PublicKey contained within the KeyStore
     * @param keyPairAlias String, the alias of the KeyPair that the PublicKey is stored within
     * @return PublicKey, the PublicKey stored within the KeyStore
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws UnrecoverableEntryException
     */
    public static PublicKey getPublicKeyFromKeyStore(String keyPairAlias) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {

        if (keyPairAlias == null || keyPairAlias.length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "getKeyPairFromKeyStore()", "Illegal argument String:keyPairAlias provided"));
        }

        loadKeyStore(KEY_STORE_PROVIDER_NAME);

        return keyStore.getCertificate(keyPairAlias).getPublicKey();
    }

}
