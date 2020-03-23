package com.vesta.android;

import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;


/**
 * Our custom SdpObserver class, will implement the methods accordingly when being called
 */
public class CustomSdpObserver implements SdpObserver {

    public void onCreateSuccess(SessionDescription sessionDescription) {

    }

    public void onSetSuccess() {

    }

    public void onCreateFailure(String s) {

    }

    public void onSetFailure(String s) {

    }
}
