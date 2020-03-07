package com.vesta.android.implementation;

import android.app.Activity;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.vesta.android.interfaces.BaseActivityInt;

public class BaseActivity extends Activity implements BaseActivityInt {
    @Override
    public void preventScreenCapture() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }
}
