package com.example.tp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String HTTP_BASE_URL="https://belatar.name";
    private static final String HTTP_WS = "/rest/profile.php?login=test&passwd=test&id=9998";
    private static final String HTTP_IMAGES="/images/";
    Etudiant etu ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHandler(View view) {
        Toast.makeText(this,R.string.ok ,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, HTTP_BASE_URL+HTTP_WS, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(MainActivity.class.getSimpleName(),response.toString());

                        try {
                            if(response.has("error")){
                                Toast.makeText(MainActivity.this,response.getString("error"),Toast.LENGTH_LONG);
                            }
                            else {
                                etu = new Etudiant(response.getInt("id"), response.getString("nom"),
                                        response.getString("prenom"),
                                        response.getString("classe"),
                                        response.getString("phone"), null);

                                EditText txtNom = findViewById(R.id.txtNom);
                                EditText textPrenom = findViewById(R.id.txtPrenom);
                                EditText txtClasse = findViewById(R.id.txtClasse);

                                txtNom.setText(etu.getNom());
                                textPrenom.setText(etu.getPrenom());
                                txtClasse.setText(etu.getClasse());


                                VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(
                                        HTTP_BASE_URL+HTTP_IMAGES+response.getString("photo"),
                                        new ImageLoader.ImageListener(){
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }

                                            @Override
                                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                                             }

                                        }
                                );
                            new ImageLoader.ImageListener(){

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(MainActivity.class.getSimpleName(),error.getMessage());
                                }

                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate ) {
                                    etu.setPhoto(response.getBitmap());
                                    ImageView img = findViewById(R.id.ImageProfile);
                                    img.setImageBitmap(etu.getPhoto());
                                }
                            };

                            }

                        } catch (JSONException e ){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}