package com.amst.mascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue ListaRequest = null;
    private LinearLayout contenedorMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListaRequest = Volley.newRequestQueue(this);
        requestMascotas();
    }

    protected void requestMascotas() {
        String url_registros = "https://mascotas-fdad.restdb.io/rest/mascotas";
        JsonArrayRequest requestRegistros = new JsonArrayRequest(
                Request.Method.GET, url_registros, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Responded");
                        mostrarMascotas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("x-apikey", "cf3f78703d25a93e03fb2d5d0e507d341fe18");
                return params;
            }
        };
        ListaRequest.add(requestRegistros);
    }

    protected void mostrarMascotas(JSONArray mascotas) {
        String registroId;
        JSONObject mascota;
        LinearLayout nuevoRegistro;
        TextView nombreMascota;
        TextView animalMascota;
        TextView edadMascota;
        TextView razaMascota;

        contenedorMascotas = findViewById(R.id.cont_mascotas);
        LinearLayout.LayoutParams parametrosLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        try
        {
            for (int i = 0; i < mascotas.length(); i++) {
                mascota = (JSONObject) mascotas.get(i);
                nuevoRegistro = new LinearLayout(this);
                nuevoRegistro.setOrientation(LinearLayout.HORIZONTAL);
                nombreMascota = new TextView(this);
                nombreMascota.setLayoutParams(parametrosLayout);
                nombreMascota.setText(mascota.getString("nombre"));
                nuevoRegistro.addView(nombreMascota);
                animalMascota = new TextView(this);
                animalMascota.setLayoutParams(parametrosLayout);
                animalMascota.setText(mascota.getString("animal"));
                nuevoRegistro.addView(animalMascota);
                edadMascota = new TextView(this);
                edadMascota.setLayoutParams(parametrosLayout);
                edadMascota.setText(mascota.getString("edad"));
                nuevoRegistro.addView(edadMascota);
                razaMascota = new TextView(this);
                razaMascota.setLayoutParams(parametrosLayout);
                razaMascota.setText(mascota.getString("raza"));
                nuevoRegistro.addView(razaMascota);
                contenedorMascotas.addView(nuevoRegistro);
            }
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
