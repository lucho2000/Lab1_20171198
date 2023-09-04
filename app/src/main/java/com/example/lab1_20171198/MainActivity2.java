package com.example.lab1_20171198;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {


    private String[] palabras; //arreglo de strings para las palabras , se debe crear un .xml con el contenido
    private Random random;
    private String palabraActual;
    private TextView[] caracteresViews;

    private LinearLayout palabraLay;

    private LetrasAdapter letrasAdapter;

    private GridLayout gridLayout; //componente gridView

    private int numCorrectos;

    private int charCorrectos;

    private ImageView[] partesImagenes;

    private int numPartes = 6;
    private int indiceParteActual;

    private Handler handler;
    private Runnable runnable;
    private int segundosTranscurridos;

    private Long tiempo_inicio;

    private ArrayList<String> arrayEstadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tiempo_inicio = getIntent().getLongExtra("tiempo inicial", 0 ); //recibo el tiempo del principal

        //obteniendo el arreglo de palabras del directorio values y almacenarlo en el arreglo creado
        palabras=getResources().getStringArray(R.array.words);
        Log.d("palabras para adivinar", Arrays.toString(palabras));
        palabraLay = findViewById(R.id.contenedorLineas);
        gridLayout = findViewById(R.id.gridLayout); //para las letras
        random = new Random();
        arrayEstadisticas = new ArrayList<>();


        partesImagenes = new ImageView[6];

        //agregando las partes del cuerpo del muñeco al arreglo
        partesImagenes[0] = findViewById(R.id.cabeza);
        partesImagenes[1] = findViewById(R.id.torso);
        partesImagenes[2] = findViewById(R.id.brazo_derecho);
        partesImagenes[3] = findViewById(R.id.brazo_izq);
        partesImagenes[4] = findViewById(R.id.pierna_dere);
        partesImagenes[5] = findViewById(R.id.pierna_izq);



        logicaJuego();

        Button button = findViewById(R.id.button28);
        button.setOnClickListener(view -> {
            //Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            //startActivity(intent);
            //para regresar al MainActivity ya creado
            finish();
        });




    }

    private void logicaJuego(){
        String nuevaPalabra = palabras[random.nextInt(palabras.length)];

        while (nuevaPalabra.equals(palabraActual) )nuevaPalabra = palabras[random.nextInt(palabras.length)];
        palabraActual=nuevaPalabra;

        caracteresViews = new TextView[palabraActual.length()];

        palabraLay.removeAllViews();
        for (int i = 0; i < palabraActual.length() ; i++) {
            caracteresViews[i] = new TextView(this); //creando el primer textView para la prim. letra
            caracteresViews[i].setText(""+palabraActual.charAt(i));  //obteniendo la primera letra de la palabra
            caracteresViews[i].setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT  )  );
            caracteresViews[i].setGravity(Gravity.CENTER);
            caracteresViews[i].setTextColor(Color.WHITE);
            //caracteresViews[i].setBackgroundResource(R.color.black);
            palabraLay.addView(caracteresViews[i]);

        }

            //letrasAdapter = new LetrasAdapter(MainActivity2.this);
            //gridView.setAdapter(letrasAdapter);
        numCorrectos=0;
        charCorrectos=palabraActual.length();
        indiceParteActual=0;
        for (int i = 0; i < numPartes; i++) {
            partesImagenes[i].setVisibility(View.INVISIBLE);
        }


    }


    public void letraPresionada(View view){

        //para obtener el texto del button
        String letter = ( (TextView)view).getText().toString(); //primero se convierte a text view
        char letterChar = letter.charAt(0); //primer caracter del texto

        view.setEnabled(false);//deshabilitar el boton, se pone en color plomo al presionar una letra
        boolean correct = false;

        for (int i = 0; i < palabraActual.length(); i++) {
            if (palabraActual.charAt(i) == letterChar){
                correct = true;
                numCorrectos++; //contador para rptas correctas
                caracteresViews[i].setTextColor(Color.BLACK);
            }
        }

        //si la letra es correcta
        if (correct){

            if (numCorrectos == charCorrectos){ //si el numero de rptas correctas es igual al tamaño de la palabra
                deshabilitarBotones();
                Long tiempo_final_gana = System.currentTimeMillis()/1000;
                /*AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("ganaste");
                builder.setMessage("felicidades\n\nla respuesta era " +palabraActual);
                builder.setPositiveButton("nuevo juego", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity2.this.logicaJuego();
                    }
                });
                builder.setNegativeButton("salir", (dialogInterface, i) -> MainActivity2.this.finish());
                builder.show();*/

                TextView resultado =  findViewById(R.id.textViewResultado);
                resultado.setVisibility(View.VISIBLE);

                resultado.setText("Ganó / Terminó en " + (String.valueOf(tiempo_final_gana - tiempo_inicio )) + "segundos");
                arrayEstadisticas.add("Terminó en " + (String.valueOf(tiempo_final_gana - tiempo_inicio )) + "segundos");

            }
        }else if (indiceParteActual < numPartes) {
            partesImagenes[indiceParteActual].setVisibility(View.VISIBLE);
            indiceParteActual++;
        }else {
            deshabilitarBotones();
            Long tiempo_final_pierde = System.currentTimeMillis()/1000;
            /*AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("perdiste");
            builder.setMessage("felicidades\n\nla respuesta era" +palabraActual);

            builder.setPositiveButton("nuevo juego", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity2.this.logicaJuego();
                }
            });
            builder.setNegativeButton("salir", (dialogInterface, i) -> MainActivity2.this.finish());
            builder.show();*/
            TextView resultado =  findViewById(R.id.textViewResultado);
            resultado.setVisibility(View.VISIBLE);
            resultado.setText("perdió / Termino en "+ (String.valueOf(tiempo_final_pierde - tiempo_inicio )) + "segundos");
            arrayEstadisticas.add("Termino en "+ (String.valueOf(tiempo_final_pierde - tiempo_inicio )) + "segundos");
        }


    }

    //para deshabilitar todos los botones al terminar el juego
    public void deshabilitarBotones(){

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_activity2 , menu );
        return true;

    }


    //el boton de estadisticas lleva a otra vista
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idEstad = item.getItemId();

        if (idEstad == R.id.estadistica ){
            Intent intent = new Intent(MainActivity2.this, EstaditicasActivity3.class);
            intent.putExtra("estadisticas", arrayEstadisticas);
            launcher.launch(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == RESULT_OK) { //validacion
                    Intent data = result.getData(); //obtener la data del activity 3 con el result.getData()
                    String tiempo_3 = data.getStringExtra("tiempo_inicial_3"); //llega como tipo string

                    tiempo_inicio= Long.valueOf(tiempo_3);

                }
            }
    );
}