package com.vesta.android;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Important links https://k6.io/docs/javascript-api/k6-ws/socket/socket-on-event-callback
 * https://socket.io/blog/native-socket-io-and-android/
 */
public class SocketConnection extends MainActivity {

    private static Socket mSocket;
    private static String CHAT_SERVER_URL = "http://81888896.ngrok.io";
    private static EditText mInputMessageView;
    private static List<Message> mMessages = new ArrayList<Message>();
    private static RecyclerView.Adapter mAdapter;


    public static void initSocket() {
        try {
            mSocket = IO.socket(CHAT_SERVER_URL);
            JSONObject obj = new JSONObject();

            try {
                obj.put("textVal", "coronaWIRUS");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.connect();
            mSocket.emit("peer-msg", obj);
        }
        catch (URISyntaxException e) {}
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        Log.i("IN ON CREATEE", "HERE WE ARE");
        super.onCreate(savedInstanceState);
        mSocket.connect();
        mSocket.on("peer-msg", handleIncomingMessages);
    }

    /**
     * Send string msg through the socket
     * @param msg
     */
    public static void sendMessage(final String msg) {
        //String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        //mInputMessageView.setText("");
        addMessage(msg);
        JSONObject send = new JSONObject();
        try {
            send.put("textVal", send);
            //going to send JSON object
            mSocket.emit("peer-msg", send);
            Log.i("JSONOBJ", send.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adding the msg to the list
     * @param message
     */
    private static void addMessage(String message) {

        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .message(message).build());
        mAdapter = new MessageAdapter(mMessages);
        mAdapter = new MessageAdapter( mMessages);
        mAdapter.notifyItemInserted(0);
         //scrollToBottom();
    }

    /**
     * Used to recieve msgs from other users
     * Listening on events
     */
    public Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            System.out.println("WWE ARE IN THERE PLAXXXX");
            ((Activity) new MainActivity().getBaseContext()).runOnUiThread(new Runnable() {

                public void run() {
                    Log.i("THE ARGS", args.toString());
                    JSONObject data = (JSONObject) args[0];

                    try{
                        String message = data.getString("textVal");
                        addMessage(message);
                        Log.i("THIS IS THE MSG", message);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

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
