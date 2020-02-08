package com.vesta.android;

import android.util.Base64;
import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PGP {

    private PublicKey masterPublicKey = null;
    private KeyPair masterKeyPair = null;

    private PublicKey encryptionPublicKey = null;
    private KeyPair encryptionKeyPair = null;


    public PGP() {

        try {
            KeyPairGenerator rsaKeyPairGen = KeyPairGenerator.getInstance("RSA");
            masterKeyPair = rsaKeyPairGen.generateKeyPair();
            masterPublicKey = masterKeyPair.getPublic();

            encryptionKeyPair = rsaKeyPairGen.generateKeyPair();
            encryptionPublicKey = encryptionKeyPair.getPublic();
        } catch (NoSuchAlgorithmException nsa) {
            Log.e("nsa-PGP", nsa.toString());
        }

    }

    public PublicKey getPublicKey() {
        return masterPublicKey;
    }

    public String getBase64MasterPublicKey() {
        return Base64.encodeToString(encryptionPublicKey.getEncoded(), Base64.DEFAULT);
    }

    public String getBase64EncryptionPublicKey() {
        return Base64.encodeToString(masterPublicKey.getEncoded(), Base64.DEFAULT);
    }

}