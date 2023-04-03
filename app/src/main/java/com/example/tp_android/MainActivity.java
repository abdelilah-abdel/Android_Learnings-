package com.example.tp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String HTTP_URL="https://belatar.name/rest/profile.php?login=test&passwd=test&id=9998";

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
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, HTTP_URL, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(MainActivity.class.getSimpleName(),response.toString());
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