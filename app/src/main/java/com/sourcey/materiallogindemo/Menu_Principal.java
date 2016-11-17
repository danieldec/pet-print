package com.sourcey.materiallogindemo;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Menu_Principal extends AppCompatActivity {
    ImageButton imageButton5,imageButton7;
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
        imageButton5=(ImageButton)findViewById(R.id.imageButton5);
        imageButton7=(ImageButton)findViewById(R.id.imageButton7);
        usuario=getIntent().getExtras().getString("usuario");
        Toast.makeText(getApplicationContext(),"Bienvenido "+usuario,Toast.LENGTH_SHORT).show();
    }
    public void evBtnListaMascota(View v){
        Intent intent=new Intent(getApplicationContext(),Lista_Mascotas.class);
        intent.putExtra("usuario",usuario);
        startActivity(intent);
    }
    public void evBtnScanQR(View v){
        Intent intent = new Intent(this, RActivity.class);
        startActivity(intent);
    }
}
