package com.vesta.android;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

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
public class SocketConnection {

    private Socket mSocket;

    {
        try {
            //TODO: will be our nodejs server url
            mSocket = IO.socket("SOMEURL");

            //to setup callbacks
            mSocket.on("open", new Emitter.Listener(){

                @Override
                public void call(Object... args) {
                    mSocket.emit("connected");
                }
            });
            mSocket.connect();
        } catch (URISyntaxException e) {
        }
    }


    /**
     * Send string msg through the socket
     * @param msg
     */
    private void sendMessage(final String msg) {
        mSocket.emit("message", msg);
    }


    /**
     * Used to recieve msgs from other users
     */
    private void newMessage() {

        mSocket.on("newMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) new MainActivity().getContext()).runOnUiThread(new Runnable() {

                    public void run() {
                        JSONObject data = (JSONObject) args[0];

                        try{
                            String username = data.getString("username");
                            String message = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }

                    }
                });
            }
        });
    }

    /**
     * Returns the socket object
     * @return
     */
    public Socket getmSocket() {
        return mSocket;
    }

    /**
     * Disconnect and close the socket
     */
    public void disconnect() {
        mSocket.disconnect();
        mSocket.close();
    }
}
