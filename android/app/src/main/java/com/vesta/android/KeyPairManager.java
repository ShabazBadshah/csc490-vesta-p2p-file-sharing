package com.vesta.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Class responsible for the creation and management of Public/Private key-pairs
 *
 *
 * [IMPORTANT]
 * We never expose public keys to anything expect for this application, ONLY the methods that require the private-key will have access to it (only when needed)
 * [END IMPORTANT]
 *
 * References:
 * https://github.com/googlearchive/android-BasicAndroidKeyStore/blob/master/Application/src/main/java/com/example/android/basicandroidkeystore/BasicAndroidKeyStoreFragment.java
 */
public class KeyPairManager {

    public static final String KEY_STORE_PROVIDER_NAME = "AndroidKeyStore";
    public static final String LOG_TAG = KeyPair.class.getSimpleName();
    private static PublicKey publicKeyObject;

    public static KeyStore keyStore;
    public Context context;

    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;

    public static final String KEY_DOES_NOT_EXIST = "KeyDoesNotExist";


    public KeyPairManager(Context context) {
        this.context = context;
    }

    /**
     * Generates a Public/Private key-pair that can be used for encryption and decryption, or returns the KeyPair if it already exists in the KeyStore
     * @param keyPairAlias String, the name of the key-pair that will be saved in the KeyStore
     * @return KeyPair, returns the KeyPair that was created which contains the PublicKey and PrivateKey
     */
    public KeyPair generateRsaEncryptionKeyPair(final String keyPairAlias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchProviderException, UnrecoverableEntryException, InvalidAlgorithmParameterException {
        if (keyPairAlias == null || keyPairAlias.length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "generateRsaEncryptionKeyPair()", "Illegal argument String:keyPairAlias"));
        }

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_PROVIDER_NAME);
        keyStore.load(null);

        KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);

        kpGenerator.initialize(new KeyGenParameterSpec.Builder(
                keyPairAlias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setKeySize(512)
                .build());

        return kpGenerator.generateKeyPair();
    }


    /**
     * Returns the the Base64 encoding of the RSA Key
     * @param rsaKey Key, the Key which will be converted to a Base64 String
     * @return String, the Base64 encoding of the Key
     */
    public static String convertRsaKeyToBase64String(Key rsaKey) {
        return Base64.encodeToString(rsaKey.getEncoded(), Base64.NO_PADDING);
    }


    /**
     * Stores the public key object in the shared preferences
     * @param sharedPrefName
     * @param context, which is a subclass of context
     * @param publicKey
     * @param androidId, unique device identifier
     */
    public static void storePublicKeySharedPref(String sharedPrefName, Context context,
                                                String publicKey, final String androidId) {

        //Convert the string public key to public key object
        try {
             publicKeyObject = KeyPairManager.convertBase64StringToPublicKey(publicKey);
            System.out.println("PUBLIC KEY OBJECTTTT " + publicKeyObject);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        mPreferences =  context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        Log.i("PublicKeyObjectString", publicKeyObject.toString());

        //Storing the object representation, used toString() to bypass error
        mEditor.putString(androidId, publicKeyObject.toString());

        mEditor.commit();
        mEditor.apply();


    }


    /**
     * Retrieves the public key from the shared preferences
     * @param sharedPrefName
     * @param context
     * @param androidID, unique device identifier
     * @return String representation of pub key that was stored
     */
    public static String retrievePublicKeySharedPref(String sharedPrefName, Context context,
                                                     final String androidID) {

        mPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        //returns default value if key does not exist
        return mPreferences.getString(androidID, KEY_DOES_NOT_EXIST);
    }


    /**
     * Removes the public key stored in shared preferences
     */
    public static void removePublicKeySharedPref(final String androidID) throws SharedPrefKeyNotFoundException {

        mEditor = mPreferences.edit();

        if (!mPreferences.getString(androidID, KEY_DOES_NOT_EXIST).equals(KEY_DOES_NOT_EXIST)) {
            mEditor.remove(androidID);
            mEditor.apply();
        }
        else {
            throw new SharedPrefKeyNotFoundException("The key you are trying to remove has not been stored");
        }
    }


    /**
     * Returns the KeyPair contained within the KeyStore for the given alias
     * @param keyPairAlias String, the alias of the KeyPair t hat the PublicKey is stored within
     * @return KeyPair, the KeyPair associated with the alias
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws UnrecoverableEntryException
     *
     * References:
     * https://stackoverflow.com/questions/42110123/save-and-retrieve-keypair-in-androidkeystore?rq=1
     *
     * Known Bugs:
     *
     * Retrieving KeyPair throws a android.os.ServiceSpecificException (code 7) Exception: https://stackoverflow.com/questions/52024752/android-9-keystore-exception-android-os-servicespecificexception
     */
    public static KeyPair getKeyPairFromKeystore(String keyPairAlias) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {

        if (keyPairAlias == null || keyPairAlias.length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "getKeyPairFromKeyStore()", "Illegal argument String:keyPairAlias"));
        }

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_PROVIDER_NAME);
        keyStore.load(null);

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyPairAlias, null);
        PublicKey publicKey = keyStore.getCertificate(keyPairAlias).getPublicKey();

        return new KeyPair(publicKey, privateKey);
    }


    /**
     * Converts a Base64 encoding of a PublicKey to a PublicKey object that can be used to encrypt data
     * @param base64EncodedPublicKey String, the Base64 intepretation of a PublicKey that will be converted to a PublicKey
     * @return PublicKey, the PublicKey object representing the base64EncodedPublicKey string argument passed in
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     *
     * References:
     * https://stackoverflow.com/questions/45754277/how-to-generate-publickey-from-string-java
     */
    public static PublicKey convertBase64StringToPublicKey(String base64EncodedPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = Base64.decode(base64EncodedPublicKey, Base64.DEFAULT);
        return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_RSA).generatePublic(new X509EncodedKeySpec(encodedPublicKey));
    }


    /**
     * Deletes the keys associated with the keyPairAlias from the KeyStore
     * @param keyPairAlias String, the alia of the KeyPair that is going to be deleted from the KeyStore
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     */
    public static void deleteKeysFromKeystore(String keyPairAlias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        if (keyPairAlias == null || keyPairAlias.length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "getKeyPairFromKeyStore()", "Illegal argument String:keyPairAlias"));
        }

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_PROVIDER_NAME);
        keyStore.load(null);

        keyStore.deleteEntry(keyPairAlias);
    }


    /**
     * Encrypts data using the PublicKey associated with the keyPairAlias given
     * @param keyPairAlias String, the alias of the KeyPair that will be used to encrypt the data given
     * @param stringToEncrypt String, the data that needs to be encrypted
     * @return String, dataToEncrypt encrypted using the keyPairAlias' PrivateKey
     */
    public static String encrypt(String keyPairAlias, String stringToEncrypt) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException, NoSuchProviderException {

        if (stringToEncrypt == null || stringToEncrypt.trim().length() == 0) {
            throw new IllegalArgumentException(String.format("%s[%s]: %s", KeyPair.class.getSimpleName(), "encrypt()", "Illegal argument String:dataToEncrypt"));
        }

        keyStore = KeyStore.getInstance(KEY_STORE_PROVIDER_NAME);
        keyStore.load(null);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround"); //or try with "RSA"
        cipher.init(Cipher.ENCRYPT_MODE, getKeyPairFromKeystore(keyPairAlias).getPublic());
        byte[] encrypted = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }


    /**
     * Decrypts data using the PrivateKey associated with the keyPairAlias given
     * @param keyPairAlias String, the alias of the KeyPair that will be used to encrypt the data given
     * @param stringToDecrypt String, the encrypted data that needs to be decrypted
     * @return String, dataToEncrypt encrypted using the keyPairAlias' PrivateKey
     */
    public static String decrypt(String keyPairAlias, String stringToDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, CertificateException, UnrecoverableEntryException, KeyStoreException, IOException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
        cipher.init(Cipher.DECRYPT_MODE, getKeyPairFromKeystore(keyPairAlias).getPrivate());
        byte[] cipherText = cipher.doFinal(Base64.decode(stringToDecrypt, Base64.DEFAULT));
        return new String(cipherText);
    }

    public static class SharedPrefKeyNotFoundException extends Exception {
        public SharedPrefKeyNotFoundException(String message) {
            super(message);
        }
    }

}