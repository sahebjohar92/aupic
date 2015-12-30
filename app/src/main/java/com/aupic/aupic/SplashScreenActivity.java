package com.aupic.aupic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.aupic.aupic.Helper.ImageCacheHelper;
import com.aupic.aupic.Storage.TransientDataRepo;

/**
 * Created by saheb on 19/10/15.
 */
public class SplashScreenActivity extends Activity {

    protected boolean active = true;
    protected int splashTime = 2000;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);

        TransientDataRepo.init();
        ImageCacheHelper.init(this);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            active = false;
        }
        return true;
    }
}
