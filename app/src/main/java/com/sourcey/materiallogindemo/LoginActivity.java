package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usuario=_emailText.getText().toString();
                final String password=_passwordText.getText().toString();
                if (usuario.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Necesitas llenar los campos de usuario y/o password para continuar",Toast.LENGTH_SHORT).show();
                    return;
                }
                //login();
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        final String resultado=enviarDatosGET(usuario,password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (resultado==""){
                                    Toast.makeText(getApplicationContext(),"No tienes conexión a internet",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                int r=obtenerDatosJSON(resultado);
                                if (r > 0){
                                    Intent intent=new Intent(getApplicationContext(),Menu_Principal.class);
                                    intent.putExtra("usuario",usuario);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(),"Usuario o password incorrectos",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                };
                thread.start();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
            }
        });
    }

    public String enviarDatosGET(String usuario,String password){
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;
        try {
            url=new URL("http://noslen.esy.es/lost-dog/pruebasPHP/login.php?usuario="+usuario+"&password="+password);
            HttpURLConnection conexion=(HttpURLConnection)url.openConnection();
            respuesta=conexion.getResponseCode();
            result = new StringBuilder();
            int resCode = conexion.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conexion.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));
                while ((linea=reader.readLine())!=null){
                    result.append(linea);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return result.toString();
    }

    public int obtenerDatosJSON(String response){
        int res=0;
        try {
            JSONArray json= new JSONArray(response);
            if (json.length()>0){
                res=1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    //implementacion del login selene
    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                              onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Inicio fallido", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    //validacion de selene
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Ingrese un correo valido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("entre 4 y 10 alfanumericos");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
