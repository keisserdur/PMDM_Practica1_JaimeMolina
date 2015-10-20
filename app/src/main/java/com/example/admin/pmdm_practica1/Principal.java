package com.example.admin.pmdm_practica1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.admin.pmdm_practica1.datos.OrdenInverso;
import com.example.admin.pmdm_practica1.datos.OrigenDatos;
import com.example.admin.pmdm_practica1.datos.persona;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


public class Principal extends Activity {

    private ListView lv;
    static protected List<persona> lista;
    static protected AdapterClass adt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ini();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menu
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Comprobamos cual se a pulsado y se ejecuta su codigo
        if (id == R.id.ordenarAZ) {
            //ordenamos la lista de forma ascendente
            Collections.sort(lista);
            adt.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.ordenarZA) {
            //ordenamos la lista de forma descendente
            Collections.sort(lista, new OrdenInverso());
            adt.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.menu_insertar) {
            //iniciamos una actividad nueva para la insercion de un nuevo contacto a la lista
            Intent i = new Intent(this, Insert.class);
            startActivity(i);
            adt.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();

        //DEVUELVE LA VISTA DONDE HE ECHO CLICK (ITEM)
        AdapterView.AdapterContextMenuInfo VistaInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = VistaInfo.position;

        if (id == R.id.menu_borrar) {
            //Borramos el contacto de la posicion pos
            lista.remove(pos);
            adt.notifyDataSetChanged();

            return true;
        } else if (id == R.id.menu_editar) {
            //Lanzamos una nueva actividad y le pasamos la posicion del elemento a editar
            Intent i = new Intent(this, Editar.class);
            Bundle b=new Bundle();
            b.putInt("pos",pos);
            i.putExtras(b);
            startActivity(i);
            adt.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    private void ini() {

        lv = (ListView) findViewById(R.id.lv);
        lista = new ArrayList<persona>();

        //Recorremos la agenda del dispositivo y la guardamos en una List para trabajar con ella
        for (persona p : OrigenDatos.getListaContactos(this)) {
            //Creamos personas que seran agregadas a la lista
            persona per = new persona(p.getId(), p.getNombre(), OrigenDatos.getListaTelefonos(this, p.getId()));
            lista.add(per);
        }

        //Creamos un adaptador y se lo asignamos al ListView
        adt = new AdapterClass(this, R.layout.elemento_lista, lista);
        lv.setAdapter(adt);

        //Registramos el menucontextual al ListView
        registerForContextMenu(lv);

    }
}
