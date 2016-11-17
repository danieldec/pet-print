package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Lista_Mascotas extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    ArrayList idPet=new ArrayList();
    ArrayList nombrePet=new ArrayList();
    ArrayList razaPet=new ArrayList();
    ArrayList imagenPet=new ArrayList();
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__mascotas);
        usuario=getIntent().getExtras().getString("usuario");
        listView=(ListView)findViewById(R.id.listaMascotas);
        descargarImagen();
    }
    private void descargarImagen() {
        idPet.clear();
        nombrePet.clear();
        razaPet.clear();
        imagenPet.clear();
        final ProgressDialog progressDialog = new ProgressDialog(Lista_Mascotas.this);
        progressDialog.setMessage("Cargando Datos...");
        progressDialog.show();
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://noslen.esy.es/lost-dog/pruebasPHP/listaMascotas.php?usuario=" + usuario, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i =0;i<jsonArray.length();i++){
                            idPet.add(jsonArray.getJSONObject(i).getString("idpet"));
                            nombrePet.add(jsonArray.getJSONObject(i).getString("name"));
                            razaPet.add(jsonArray.getJSONObject(i).getString("raza"));
                            imagenPet.add(jsonArray.getJSONObject(i).getString("img"));
                        }
                        listView.setAdapter(new ImagenAdapter(getApplicationContext()));
                        listView.setOnItemClickListener(Lista_Mascotas.this);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Fallo Conexión con el internet",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String item=((TextView)view).getText().toString();
        Toast.makeText(Lista_Mascotas.this,item,Toast.LENGTH_LONG).show();
        Toast.makeText(Lista_Mascotas.this,"HOLAMUNDO",Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),i+" "+l,Toast.LENGTH_SHORT).show();
    }

    private class ImagenAdapter extends BaseAdapter{
        Context contexto;
        LayoutInflater inflater;
        SmartImageView smartImageView;
        TextView txtVwidPet,txtVwnombre,txtVwraza;
        Button btnPerdido;
        String urlImagen="";
        public ImagenAdapter(Context applicationContext) {
            this.contexto=applicationContext;
            inflater=(LayoutInflater)contexto.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return imagenPet.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup parent) {
            ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.activity_lista_pet,null);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagenPet);
            txtVwidPet=(TextView)viewGroup.findViewById(R.id.txtViewIdPet);
            txtVwnombre=(TextView)viewGroup.findViewById(R.id.txtViewNombrePet);
            txtVwraza=(TextView)viewGroup.findViewById(R.id.txtViewRazaPet);
            btnPerdido=(Button)viewGroup.findViewById(R.id.btnPerdido);
            urlImagen="http://noslen.esy.es/lost-dog/imgPets/"+imagenPet.get(i).toString();
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlImagen, rect);
            txtVwidPet.setText("ID: " + idPet.get(i).toString());
            txtVwnombre.setText("Nombre: " + nombrePet.get(i).toString());
            txtVwraza.setText("Raza: " + razaPet.get(i).toString());
            btnPerdido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogo= new AlertDialog.Builder(Lista_Mascotas.this);
                    dialogo.setTitle("IMPORTANTE").setMessage("¿Estás seguro que deseas continuar?").setCancelable(false);
                    dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aceptar();
                        }
                    });
                    dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelar(dialogInterface);
                        }
                    });
                    dialogo.show();
                }
            });
            return viewGroup;
        }
        public void aceptar(){
            //Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
        }
        public void cancelar(DialogInterface d){
            d.dismiss();
        }
    }
}
