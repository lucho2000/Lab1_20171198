package com.example.lab1_20171198;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Integer> colorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //para la barra de la parte de arriba y que se centre el titulo
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);

        TextView textView = findViewById(R.id.textView2); //
        registerForContextMenu(textView);


        //boton para empezar el juego
        Button buttonPlay = findViewById(R.id.buttonPlay);



    }


    public void inicioJuego(View view){

        Intent intent = new Intent(MainActivity.this , MainActivity2.class);

        intent.putExtra("tiempo inicial", System.currentTimeMillis()/1000);
        startActivity(intent);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);

        //otra forma de agrupar los colores
        colorMap = new HashMap<>();
        colorMap.put(R.id.menu_item_red, Color.RED);
        colorMap.put(R.id.menu_item_green, Color.GREEN);
        colorMap.put(R.id.menu_item_blue, Color.BLUE);

    }


    //para el cambio de color en el texto de TELEAHORCADO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        TextView textView = findViewById(R.id.textView2);

        Integer color = colorMap.get(id);
        if (color != null) {
            textView.setTextColor(color);
            return true;
        }

        return super.onContextItemSelected(item);
    }
}