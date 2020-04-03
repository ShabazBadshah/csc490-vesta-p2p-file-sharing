package com.vesta.android;
import android.util.Log;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.vesta.android.implementation.view_impl.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


/**
 * Important links https://k6.io/docs/javascript-api/k6-ws/socket/socket-on-event-callback
 * https://socket.io/blog/native-socket-io-and-android/
 */
public class SocketConnection extends MainActivity {

    private static Socket mSocket;
    private static EditText mInputMessageView;
    private JSONObject messageJson;

    static {
        try {
            mSocket = IO.socket("http://62ba76cc.ngrok.io");
//            Log.i("SERVER_URL", SplashScreenActivity.P2P_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //private static List<Message> mMessages = new ArrayList<Message>();
    private static RecyclerView.Adapter mAdapter;


    /**
     * Using this method as test purposes for sending messages
     * @param encPubKeyWithSymKey
     * @param symKeyBase64
     * @param fileTransferFlowState
     */
    public void sendMessage(String encPubKeyWithSymKey, String symKeyBase64, String fileTransferFlowState) {

        JSONObject obj = new JSONObject();

        try {
            obj.put("textVal", encPubKeyWithSymKey);
            obj.put("symKeyBase64", symKeyBase64);
            obj.put("fileTransferFlowState", fileTransferFlowState);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getmSocket().connect();

        //sends the msg on the peer-msg channel, works
        //sends coronaWIRUS to server
        //symetric encrypted key to the server
        getmSocket().emit("peer-msg", obj);

        Log.i("OUTSIDE OF ON", "OUTIEWEBFIUWEBFOUBE");

    }

    /**
     * Recieve messages from the server
     */
    public void recieveMessages() {

        //this should listen on peer-msg channels in order to recieve msgs from server
        getmSocket().on("peer-msg", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                try {
                    messageJson = new JSONObject(args[0].toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("Rec Message", messageJson.getString("textVal"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Returns the socket object
     * @return
     */
    public static Socket getmSocket() {
        return mSocket;
    }

    /**
     * Disconnect and close the socket when app is closed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.close();
    }
}
