package com.example.lab1_20171198;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class EstaditicasActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaditicas3);

        Button buttonActivity3 = findViewById(R.id.buttonNewGame);


        Long tiempo_inicial3 = System.currentTimeMillis()/1000;

        ArrayList<String> listaEstadisticas = getIntent().getStringArrayListExtra("estadisticas");

        int i=1;
        for (String elemento : listaEstadisticas) {
            TextView despliegueEstad =  findViewById(R.id.textView5);
            despliegueEstad.setText("Juego "+ i + ":" + elemento);
            i++;
        }

        buttonActivity3.setOnClickListener( view -> {
            Intent intent = new Intent();
            intent.putExtra("tiempo_inicial_3" ,tiempo_inicial3.toString());
            setResult(RESULT_OK, intent);
            finish();
        } );
        /*for (int i = 0; i < listaEstadisticas.size(); i++) {
            TextView despliegueEstad =  findViewById(R.id.textView5);

        }*/
    }
}