package com.sourcey.materiallogindemo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
public class MainActivity extends ActionBarActivity {
    ImageButton imageButton2;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity();
            }
        });

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogsActivity();
            }
        });
        //Toast.makeText(this, Tel(),Toast.LENGTH_LONG).show();
    }

  /*  private String Tel(){
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
   return telephonyManager.getLine1Number();
    }
*/
    private void launchActivity() {
        Intent intent = new Intent(this, RActivity.class);
        startActivity(intent);
    }

    private void dogsActivity() {

        Intent intent = new Intent(this, mios.class);
        startActivity(intent);
        //Intent intent = new Intent();
        //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
    }
    }


