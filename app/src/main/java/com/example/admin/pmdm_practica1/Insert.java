package com.example.admin.pmdm_practica1;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.admin.pmdm_practica1.datos.persona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Admin on 18/10/2015.
 */
public class Insert extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
    }

    public void agregar (View v){

        EditText nombre= (EditText) findViewById(R.id.et_nombre);
        EditText telefono= (EditText) findViewById(R.id.et_telefono);

        //Creamos una lista auxiliar donde guardar los telefonos
        List<String> telef=new ArrayList<String>();
        telef.add(telefono.getText().toString());

        //gauardamos el Id del ultimo contacto de la lista
        long lastId = Principal.lista.get(Principal.lista.size()-1).getId();

        //Creamos una persona nueva, asignandole una Id mayor a la del ultimo
        persona nueva=new persona( lastId+3 ,nombre.getText().toString(),telef);

        //Añadimos al usiario a la lista
        Principal.lista.add(nueva);
        volver();

    }

    public void cancelar (View v){
        volver();
    }

    private void volver(){
        //Actualizamos la lista y finalizamos la actividad
        Collections.sort(Principal.lista);
        Principal.adt.notifyDataSetChanged();
        this.finish();
    }
}
