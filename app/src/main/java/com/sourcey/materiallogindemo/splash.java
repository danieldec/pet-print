package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by laughtly on 14/11/2016.
 */
public class splash extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(splash.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}

