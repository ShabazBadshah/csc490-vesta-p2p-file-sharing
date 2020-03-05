package com.vesta.android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
//import com.myhexaville.androidwebrtc.databinding.ActivitySampleDataChannelBinding;

import com.google.zxing.Result;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import kotlin.text.Charsets;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private final int MY_CAMERA_REQUEST_CODE = 1888;
    private static final String TAG = "ScannerActivity";
    private static PeerConnectionFactory peerConnectionFactory;
    private static PeerConnection localPeerConnection;
    private static PeerConnection remotePeerConnection;
    private static DataChannel localDataChannel;
    //private ActivitySampleDataChannelBinding binding;



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

        System.out.println("HERE IS THE RAW RESULT " + rawResult.getText());
        //if (rawResult.getText()) //parse the string and check if its from the desktop

        //TODO: Commented out for now for testing purposes, to test if web rtc connection
        // is established
        //Storing the rawResult which is the public key in shared preferences
       // KeyPairManager.storePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext(),
         //       rawResult.getText());
       // Log.i("Retrieve shared pref",
     //           KeyPairManager.retrievePublicKeySharedPref(SHARED_PREFERENCES, this.getBaseContext()));


        /**
         * WebRTC code here
         * The callee is the android side of the connection
         * Check to see if the result from the QR code is from the desktop
         */

        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, true);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        peerConnectionFactory = new PeerConnectionFactory(options);
       // binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //PeerConnection localPeerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, pcConstraints, pcObserver);

        //The desktop is the local peer, the caller
        localPeerConnection = createPeerConnection(peerConnectionFactory, true);

        //The mobile is the callee, the one getting called
        remotePeerConnection = createPeerConnection(peerConnectionFactory, false);

        //creation/initialization of data channel so we can exchange data
        DataChannel.Init initDC = new DataChannel.Init();

        //set the attributes for our data channel
        initDC.id = 1;
        initDC.protocol = "udp";

        //TODO: work on creating offer, setting remote and local desc etc, understand process
        // Establishes the connection between the two peers
        localPeerConnection.createOffer(new CustomSdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {

                Log.i("SessionDescription", sessionDescription.toString());

                //setting of remote and local description happens here
                //send a description to the other client for establishing connection
                localPeerConnection.setLocalDescription(new CustomSdpObserver(), sessionDescription);
                remotePeerConnection.setRemoteDescription(new CustomSdpObserver(), sessionDescription);

                //remote peer will answer back to the caller
                remotePeerConnection.createAnswer(new CustomSdpObserver() {

                    //reply back here
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {

                        localPeerConnection.setRemoteDescription(new CustomSdpObserver(), sessionDescription);
                        remotePeerConnection.setLocalDescription(new CustomSdpObserver(), sessionDescription);
                    }

                }, new MediaConstraints());
            }
        }, new MediaConstraints());


        //init of localDataChannel
        localDataChannel = localPeerConnection.createDataChannel("dataChannel", initDC);

        localDataChannel.registerObserver(new DataChannel.Observer() {

            @Override
            public void onBufferedAmountChange(long l) {

            }

            @Override
            public void onStateChange() {
                Log.d(TAG, "onStateChange: " + localDataChannel.state().toString());


               // runOnUiThread(() -> {

                    //if (localDataChannel.state() == DataChannel.State.OPEN) {
                      //  binding.sendButton.setEnabled(true);
                    //} else {
                      //  binding.sendButton.setEnabled(false);
                    //}
                //});

            }

            /**
             * Where we are notified that the messaged is recieved
             * Turn the buffer into a string to get where it is sent from
             * @param buffer
             */
            @Override
            public void onMessage(final DataChannel.Buffer buffer) {
                byte[] bytes  = buffer.data.array();
                String stringMsg = new String( bytes, StandardCharsets.UTF_8);
                Log.i("stringMsg", stringMsg);
            }

        });


        /**
         * WebRTC code here
         * The callee is the android side of the connection
         * Check to see if the result from the QR code is from the desktop
         */

        //if (rawResult.getText()) //parse the string and check if its from the desktop

        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, true);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        peerConnectionFactory = new PeerConnectionFactory(options);
       // binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //PeerConnection localPeerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, pcConstraints, pcObserver);

        //The desktop is the local peer, the caller
        localPeerConnection = createPeerConnection(peerConnectionFactory, true);

        //The mobile is the callee, the one getting called
        remotePeerConnection = createPeerConnection(peerConnectionFactory, false);

        //creation/initialization of data channel so we can exchange data
        DataChannel.Init initDC = new DataChannel.Init();

        //set the attributes for our data channel
        initDC.id = 1;
        initDC.protocol = "udp";

        localDataChannel = localPeerConnection.createDataChannel("dataChannel", initDC);

        localDataChannel.registerObserver(new DataChannel.Observer() {

            @Override
            public void onBufferedAmountChange(long l) {

            }

            @Override
            public void onStateChange() {
                Log.d(TAG, "onStateChange: " + localDataChannel.state().toString());


               // runOnUiThread(() -> {

                    //if (localDataChannel.state() == DataChannel.State.OPEN) {
                      //  binding.sendButton.setEnabled(true);
                    //} else {
                      //  binding.sendButton.setEnabled(false);
                    //}
                //});

            }

            /**
             * Where we are notified that the messaged is recieved
             * Turn the buffer into a string to get where it is sent from
             * @param buffer
             */
            @Override
            public void onMessage(final DataChannel.Buffer buffer) {
                byte[] bytes  = buffer.data.array();
                String stringMsg = new String( bytes, StandardCharsets.UTF_8 );
            }

        });


        System.out.println(((TextView)findViewById(R.id.textView)));
        Log.v(TAG, rawResult.getText()); // Prints scan result
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
//        setContentView(R.layout.activity_main);

//        TextView publicKeyTextView = (TextView)findViewById(R.id.textView);
//        publicKeyTextView.setText(rawResult.getText());
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

    /**
     * Responsible for sending the msg through the data channel
     * @param msg
     */
    public void sendMessage (final String msg) {

        //convert msg to byte buffer
        byte[] bytes = msg.getBytes(Charsets.UTF_8);

        //send the buffer from the local data channel
        localDataChannel.send(new DataChannel.Buffer(ByteBuffer.wrap(bytes), false));

    }    /*public void sendMessage(View view) {
        String message = binding.textInput.getText().toString();
        if (message.isEmpty()) {
            return;
        }

        binding.textInput.setText("");

        ByteBuffer data = stringToByteBuffer("-s" + message, Charset.defaultCharset());
        localDataChannel.send(new DataChannel.Buffer(data, false));
    }*/

    private static ByteBuffer stringToByteBuffer(String msg, Charset charset) {
        return ByteBuffer.wrap(msg.getBytes(charset));
    }
}
