package com.vesta.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Point;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Headers;

import android.graphics.Bitmap;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private ImageView qrImage;
    private String inputValue;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmapResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.button);
        final Button qr_button = (Button)findViewById(R.id.qrbutton);
        qrImage = findViewById(R.id.qr_image);
        final TextView responseField = (TextView)findViewById(R.id.textView);

        final OkHttpClient client = new OkHttpClient();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            final Request request = new Request.Builder()
                                    .url("http://10.0.2.2:5000/")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Log.v("VESTA-LOG", response.toString());
                            responseField.setText(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calculating bitmap dimension
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                qrgEncoder = new QRGEncoder("AVI IS THE FIRST", null, QRGContents.Type.TEXT, smallerDimension);
                try {
                    bitmapResult = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmapResult);
                } catch (Exception e) {
                    Log.v("Log error", e.toString());
                }

            }

        });
    }
}
