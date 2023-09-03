package com.example.lab1_20171198;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LetrasAdapter extends BaseAdapter {

    private String[] letters;
    private LayoutInflater letInfla;


    public LetrasAdapter(Context context){

        letters = new String[26];
        for (int i = 0; i < letters.length ; i++) {

            letters[i] = "" + (i + "A");
        }

        letInfla = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Button botonLetra;

        if (view==null){

            botonLetra = (Button) letInfla.inflate(R.layout.letra, viewGroup, false);
        } else {

            botonLetra = (Button) view;
        }

        botonLetra.setText(letters[i]);

        return botonLetra;
    }
}
