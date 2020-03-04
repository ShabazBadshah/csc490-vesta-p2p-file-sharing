package com.vesta.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private final int MY_CAMERA_REQUEST_CODE = 1888;
    private static final String TAG = "ScannerActivity";
    private static PeerConnectionFactory peerConnectionFactory;
    private static PeerConnection localPeerConnection;
    private static PeerConnection remotePeerConnection;
    private static DataChannel localDataChannel;


    /*
    * The name of our shared preferences
    * Used to instantiate the shared preferences object with this name
    * */
    private static final String SHARED_PREFERENCES = "SharedPreferences";

    private void setScannerProperties(ZXingScannerView qrCodeScanner) {
        qrCodeScanner.setAutoFocus(true);
        qrCodeScanner.setLaserColor(R.color.colorAccent);
        qrCodeScanner.setMaskColor(R.color.colorAccent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                String [] perms = {Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(this, perms,
                        MY_CAMERA_REQUEST_CODE);
                return;
            }
        }

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /**
     * The rawResult variable contains the data which was present in the QR code
     * Used to get the contents from the QR code
     * @param rawResult, Result object retrieved from the QR code
     */
    @Override
    public void handleResult(Result rawResult) {

        //Storing the rawResult which is the public key in shared preferences
        KeyPairManager.storePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext(),
                rawResult.getText());
        Log.i("Retrieve shared pref",
                KeyPairManager.retrievePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext()));


        /**
         * WebRTC code here
         * The callee is the android side of the connection
         * Check to see if the result from the QR code is from the desktop
         */

        //if (rawResult.getText()) //parse the string and check if its from the desktop

        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, true);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        peerConnectionFactory = new PeerConnectionFactory(options);

        //PeerConnection localPeerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, pcConstraints, pcObserver);

        //The desktop is the local peer, the caller
        localPeerConnection = createPeerConnection(peerConnectionFactory, true);

        //The mobile is the callee, the one getting called
        remotePeerConnection = createPeerConnection(peerConnectionFactory, false);

        //creation/initialization of data channel so we can exchange data
        localDataChannel = localPeerConnection.createDataChannel("dataChannel", new DataChannel.Init());

        localDataChannel.registerObserver(new DataChannel.Observer() {

            @Override
            public void onBufferedAmountChange(long l) {

            }

            @Override
            public void onStateChange() {

            }

            @Override
            public void onMessage(DataChannel.Buffer buffer) {

            }

        });


        System.out.println(((TextView)findViewById(R.id.textView)));
        Log.v(TAG, rawResult.getText()); // Prints scan result
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        setContentView(R.layout.activity_main);

        TextView publicKeyTextView = (TextView)findViewById(R.id.textView);
        publicKeyTextView.setText(rawResult.getText());
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    /**
     *
     * Reference https://github.com/IhorKlimov/Android-WebRtc/blob/dec8ab27ff9525150af80ba7054aa19dfdd8c065/app/src/main/java/com/myhexaville/androidwebrtc/tutorial/DataChannelActivity.java?fbclid=IwAR3My1RT0w_LunhYg4fiCBgFY2_RssjoitGk6mA5dZHiSFHaOUX70cbQQDg
     * @param factory
     * @param isLocal
     * @return
     */
    public static PeerConnection createPeerConnection(PeerConnectionFactory factory,
                    boolean isLocal) {
        //the local peer
        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(new ArrayList<PeerConnection.IceServer>());
        MediaConstraints pcConstraints = new MediaConstraints();
        PeerConnection.Observer pcObserver = new PeerConnection.Observer() {
            @Override
            public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {

            }

            @Override
            public void onSignalingChange(PeerConnection.SignalingState signalingState) {
                Log.d(TAG, "onSignalingChange: ");
            }

            @Override
            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                Log.d(TAG, "onIceConnectionChange: ");
            }

            @Override
            public void onIceConnectionReceivingChange(boolean b) {
                //                Log.d(TAG, "onIceConnectionReceivingChange: ");
            }

            @Override
            public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
                Log.d(TAG, "onIceGatheringChange: ");
            }

            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {

                //                Log.d(TAG, "onIceCandidate: " + isLocal);
                //                if (isLocal) {
                //                    remotePeerConnection.addIceCandidate(iceCandidate);
                //                } else {
                //                    localPeerConnection.addIceCandidate(iceCandidate);
                //

            }

            @Override
            public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
                Log.d(TAG, "onIceCandidatesRemoved: ");
            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                Log.d(TAG, "onAddStream: ");
            }

            @Override
            public void onRemoveStream(MediaStream mediaStream) {
                Log.d(TAG, "onRemoveStream: ");
            }

            @Override
            public void onDataChannel(final DataChannel dataChannel) {
                Log.d(TAG, "onDataChannel: is local:  , state: " + dataChannel.state());
                dataChannel.registerObserver(new DataChannel.Observer() {
                    @Override
                    public void onBufferedAmountChange(long l) {

                    }

                    @Override
                    public void onStateChange() {
                        Log.d(TAG, "onStateChange: remote data channel state: " + dataChannel.state().toString());
                    }

                    @Override
                    public void onMessage(DataChannel.Buffer buffer) {
                        Log.d(TAG, "onMessage: got message");
                    }
                });
            }

            @Override
            public void onRenegotiationNeeded() {
                Log.d(TAG, "onRenegotiationNeeded: ");
            }
        };
        return peerConnectionFactory.createPeerConnection(rtcConfig, pcConstraints, pcObserver);

    }
}
