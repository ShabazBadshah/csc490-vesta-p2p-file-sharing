package com.vesta.android;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


/**
 * Important links https://k6.io/docs/javascript-api/k6-ws/socket/socket-on-event-callback
 * https://socket.io/blog/native-socket-io-and-android/
 */
public class SocketConnection extends MainActivity {

    private static Socket mSocket;
    private static String CHAT_SERVER_URL = "http://9d019160.ngrok.io";
    private static EditText mInputMessageView;
    private JSONObject messageJson;

    static {
        try {
            mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //private static List<Message> mMessages = new ArrayList<Message>();
    private static RecyclerView.Adapter mAdapter;


    /**
     * Using this method as test purposes for sending and recieving messages
     */
    public void sendMessage(String msg) {

        JSONObject obj = new JSONObject();

        try {
            obj.put("textVal", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getmSocket().connect();

        //sends the msg on the peer-msg channel, works
        //sends coronaWIRUS to server
        //symetric encrypted key to the server
        getmSocket().emit("peer-msg", obj);
        
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
